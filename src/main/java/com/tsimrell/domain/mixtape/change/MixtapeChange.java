package com.tsimrell.domain.mixtape.change;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

public class MixtapeChange {
    @JsonProperty("new_playlist")
    private List<NewPlaylistItem> newPlaylist;
    @JsonProperty("remove_playlist")
    private List<String> removePlaylist;
    @JsonProperty("update_playlist")
    private List<UpdatePlaylistItem> updatePlaylist;

    public List<NewPlaylistItem> getNewPlaylist() {
        return newPlaylist;
    }

    public void setNewPlaylist(List<NewPlaylistItem> newPlaylist) {
        this.newPlaylist = newPlaylist;
    }

    public List<String> getRemovePlaylist() {
        return removePlaylist;
    }

    public void setRemovePlaylist(List<String> removePlaylist) {
        this.removePlaylist = removePlaylist;
    }

    public List<UpdatePlaylistItem> getUpdatePlaylist() {
        return updatePlaylist;
    }

    public void setUpdatePlaylist(List<UpdatePlaylistItem> updatePlaylist) {
        this.updatePlaylist = updatePlaylist;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MixtapeChange that = (MixtapeChange) o;
        return Objects.equals(newPlaylist, that.newPlaylist) &&
                Objects.equals(removePlaylist, that.removePlaylist) &&
                Objects.equals(updatePlaylist, that.updatePlaylist);
    }

    @Override
    public int hashCode() {
        return Objects.hash(newPlaylist, removePlaylist, updatePlaylist);
    }

    @Override
    public String toString() {
        return "MixtapeChange{" +
                "newPlaylist=" + newPlaylist +
                ", removePlaylist=" + removePlaylist +
                ", updatePlaylist=" + updatePlaylist +
                '}';
    }
}
