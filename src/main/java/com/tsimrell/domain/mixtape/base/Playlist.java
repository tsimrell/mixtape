package com.tsimrell.domain.mixtape.base;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

//{
//      "id" : "1",
//      "user_id" : "2",
//      "song_ids" : [
//        "8",
//        "32"
//      ]
//    }
public class Playlist {
    @JsonProperty("id")
    private String id;
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("song_ids")
    private List<String> songIds;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
        Playlist playlist = (Playlist) o;
        return Objects.equals(id, playlist.id) &&
                Objects.equals(userId, playlist.userId) &&
                Objects.equals(songIds, playlist.songIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, songIds);
    }

    @Override
    public String toString() {
        return "Playlist{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", songIds=" + songIds +
                '}';
    }
}
