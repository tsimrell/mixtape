package com.tsimrell.domain.mixtape.change;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

public class UpdatePlaylistItem {
    @JsonProperty("playlist_id")
    private String playlistId;
    @JsonProperty("song_ids")
    private List<String> songIds;

    public String getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(String playlistId) {
        this.playlistId = playlistId;
    }

    public List<String> getSongIds() {
        return songIds;
    }

    public void setSongIds(List<String> songIds) {
        this.songIds = songIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdatePlaylistItem that = (UpdatePlaylistItem) o;
        return Objects.equals(playlistId, that.playlistId) &&
                Objects.equals(songIds, that.songIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playlistId, songIds);
    }

    @Override
    public String toString() {
        return "UpdatePlaylistItem{" +
                "playlistId='" + playlistId + '\'' +
                ", songIds=" + songIds +
                '}';
    }
}
