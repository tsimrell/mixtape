package com.tsimrell.domain.mixtape.change;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

public class NewPlaylistItem {
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("song_ids")
    private List<String> songIds;

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
        NewPlaylistItem that = (NewPlaylistItem) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(songIds, that.songIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, songIds);
    }

    @Override
    public String toString() {
        return "NewPlaylistItem{" +
                "userId='" + userId + '\'' +
                ", songIds=" + songIds +
                '}';
    }
}
