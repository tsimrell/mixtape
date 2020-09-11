package com.tsimrell.service.impl;

import com.tsimrell.domain.mixtape.base.MixtapeBase;
import com.tsimrell.domain.mixtape.base.Playlist;
import com.tsimrell.domain.mixtape.base.Song;
import com.tsimrell.domain.mixtape.base.User;
import com.tsimrell.domain.mixtape.change.MixtapeChange;
import com.tsimrell.domain.mixtape.change.NewPlaylistItem;
import com.tsimrell.domain.mixtape.change.UpdatePlaylistItem;
import com.tsimrell.service.MixtapeChangeService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MixtapeChangeServiceImpl implements MixtapeChangeService {
    public MixtapeChangeServiceImpl() {}

    public MixtapeBase updateMixtapeBase(MixtapeBase mixtapeBase, MixtapeChange mixtapeChange) {
        if(mixtapeBase != null && mixtapeChange != null) {
            Map<String, User> userMap = mixtapeBase.getUsers().stream().collect(Collectors.toMap(User::getId,
                    Function.identity()));
            Map<String, Song> songMap = mixtapeBase.getSongs().stream().collect(Collectors.toMap(Song::getId,
                    Function.identity()));
            // Playlist is special, we need the max ID for making new playlists with unique IDs
            Map<String, Playlist> playlistMap = new HashMap<>();
            int currentMaxPlaylistId = buildPlaylistMap(mixtapeBase.getPlaylists(), playlistMap);

            boolean updated = false;
            // -- Add New Playlists --
            if(mixtapeChange.getNewPlaylist() != null && !mixtapeChange.getNewPlaylist().isEmpty()) {
                addNewPlaylists(userMap, playlistMap, songMap, mixtapeChange.getNewPlaylist(), currentMaxPlaylistId);
                updated = true;
            }
            // -- Remove Playlists --
            if(mixtapeChange.getRemovePlaylist() != null &&!mixtapeChange.getRemovePlaylist().isEmpty()) {
                removePlaylists(playlistMap, mixtapeChange.getRemovePlaylist());
                updated = true;
            }
            // -- Add Existing Song to Existing Playlist (Update Playlist) --
            if(mixtapeChange.getUpdatePlaylist() != null &&!mixtapeChange.getUpdatePlaylist().isEmpty()) {
                updatePlaylist(playlistMap, songMap, mixtapeChange.getUpdatePlaylist());
                updated = true;
            }

            if(updated) {
                mixtapeBase = makeNewMixtapeBase(userMap, playlistMap, songMap);
            }

        } else {
            // This should be checked before coming into this method.
            throw new RuntimeException("Mixtape Base and Mixtape Change objs cannot be null!");
        }
        return mixtapeBase;
    }

    void addNewPlaylists(Map<String, User> userMap, Map<String, Playlist> playlistMap, Map<String, Song> songMap,
                           List<NewPlaylistItem> newPlaylistItems, int currentMaxPlaylistId) {
        if(userMap != null && playlistMap != null && songMap != null && newPlaylistItems != null) {
            for(NewPlaylistItem currentPlaylist : newPlaylistItems) {
                boolean isValidAddition = userMap.containsKey(currentPlaylist.getUserId());
                isValidAddition &= areSongIdsValid(songMap, currentPlaylist.getSongIds());

                if(isValidAddition) {
                    currentMaxPlaylistId++;
                    Playlist newPlaylist = new Playlist();
                    newPlaylist.setId(""+currentMaxPlaylistId);
                    newPlaylist.setSongIds(currentPlaylist.getSongIds());
                    newPlaylist.setUserId(currentPlaylist.getUserId());
                    playlistMap.put(newPlaylist.getId(), newPlaylist);
                } else {
                    throw new IllegalArgumentException("new_playlist: one of the new playlists is invalid. " +
                            "There must be at least " +
                            "one valid song in each playlist and one valid user id");
                }
            }
        } else {
            throw new IllegalArgumentException("new_playlist: internal error trying to add a new playlist");
            // here is where the log statement would go.
        }
    }

    void removePlaylists(Map<String, Playlist> playlistMap, List<String> playlistsToBeRemoved) {
        if(playlistMap != null && !playlistsToBeRemoved.isEmpty()) {
            for(String playlistId : playlistsToBeRemoved) {
                if(playlistMap.containsKey(playlistId)) {
                    playlistMap.remove(playlistId);
                } else {
                    throw new IllegalArgumentException("remove_playlist: Playlist id " + playlistId + " is invalid. The given ID of " +
                            "the playlist to remove must be a valid playlist ID.");
                }
            }
        } else {
            throw new IllegalArgumentException("remove_playlist: internal error trying to remove a playlist");
            // here is where the log statement would go.
        }
    }

    void updatePlaylist(Map<String, Playlist> playlistMap, Map<String, Song> songMap,
                        List<UpdatePlaylistItem> updatePlaylistItems) {
        if(playlistMap != null && songMap != null && updatePlaylistItems != null) {
            for(UpdatePlaylistItem updatePlaylistItem : updatePlaylistItems) {
                // Valid playlist check
                boolean isValidToUpdate = playlistMap.containsKey(updatePlaylistItem.getPlaylistId());
                // Valid songs check
                isValidToUpdate &= areSongIdsValid(songMap, updatePlaylistItem.getSongIds());

                if(isValidToUpdate) {
                    Playlist playlistToBeUpdate = playlistMap.get(updatePlaylistItem.getPlaylistId());
                    playlistToBeUpdate.getSongIds().addAll(updatePlaylistItem.getSongIds());
                } else {
                    throw new IllegalArgumentException("update_playlist: Invalid update playlist items given. The playlist ID must be " +
                            "a valid playlist ID and all of the song ID must be a valid song ID");
                }
            }
        } else {
            throw new IllegalArgumentException("update_playlist: internal error trying to update a playlist");
            // here is where the log statement would go.
        }
    }

    MixtapeBase makeNewMixtapeBase(Map<String, User> userMap, Map<String, Playlist> playlistMap,
                                   Map<String, Song> songMap) {
        MixtapeBase mixtapeBase = new MixtapeBase();
        if(userMap != null && playlistMap != null && songMap != null) {
            mixtapeBase.setUsers(new ArrayList(userMap.values()));
            mixtapeBase.setPlaylists(new ArrayList(playlistMap.values()));
            mixtapeBase.setSongs(new ArrayList(songMap.values()));
        }


        return mixtapeBase;
    }

    boolean areSongIdsValid(Map<String, Song> songMap, List<String> songIds) {
        boolean isValid = true;
        if(songMap != null && songIds != null && !songIds.isEmpty()) {
            for(String songId : songIds) {
                isValid &= songMap.containsKey(songId);
            }
        }

        return isValid;
    }

    /**
     * Using the given playlist array, this method populates the given playlistMap. Along with that, it will return
     * the current highest playlist ID. This is needed to make sure the new playlists have unique IDs and are
     * sequentially higher than the older playlists.
     * @param playlists The List of playlists from the Base Mixtape JSON file.
     * @param playlistMap An empty map to have the playlists added to, key == id, value == the playlist object
     * @return An int representing the current max ID
     */
    int buildPlaylistMap(List<Playlist> playlists, Map<String, Playlist> playlistMap) {
        int currentMax = -1;
        if(playlists != null && playlistMap != null) {
            for(Playlist playlist : playlists) {
                int currentPlaylistId = Integer.parseInt(playlist.getId());
                if(currentPlaylistId > currentMax) {
                    currentMax = currentPlaylistId;
                }
                playlistMap.put(playlist.getId(), playlist);
            }
        }

        return currentMax;
    }
}
