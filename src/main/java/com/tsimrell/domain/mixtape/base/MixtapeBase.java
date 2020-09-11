package com.tsimrell.domain.mixtape.base;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

public class MixtapeBase {
    @JsonProperty("users")
    private List<User> users;
    @JsonProperty("playlists")
    private List<Playlist> playlists;
    @JsonProperty("songs")
    private List<Song> songs;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<Playlist> playlists) {
        this.playlists = playlists;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MixtapeBase that = (MixtapeBase) o;
        return Objects.equals(users, that.users) &&
                Objects.equals(playlists, that.playlists) &&
                Objects.equals(songs, that.songs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(users, playlists, songs);
    }

    @Override
    public String toString() {
        return "MixtapeBase{" +
                "users=" + users +
                ", playlists=" + playlists +
                ", songs=" + songs +
                '}';
    }
}
