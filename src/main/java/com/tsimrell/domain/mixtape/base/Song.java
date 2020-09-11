package com.tsimrell.domain.mixtape.base;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

//    {
//      "id" : "1",
//      "artist": "Camila Cabello",
//      "title": "Never Be the Same"
//    }
public class Song {
    @JsonProperty("id")
    private String id;
    @JsonProperty("artist")
    private String artist;
    @JsonProperty("title")
    private String title;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Song song = (Song) o;
        return Objects.equals(id, song.id) &&
                Objects.equals(artist, song.artist) &&
                Objects.equals(title, song.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, artist, title);
    }

    @Override
    public String toString() {
        return "Song{" +
                "id='" + id + '\'' +
                ", artist='" + artist + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
