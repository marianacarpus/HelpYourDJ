package com.music.helpyourdj;

import android.support.annotation.NonNull;

public class MusicVoted implements Comparable<MusicVoted>{
  String artist;
  String genre;
  String image;
  String melodyName;
  String url;
  int votes;
  String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public MusicVoted() {
    }

    public MusicVoted(String artist, String genre, String image, String melodyName, String url, int votes) {
        this.artist = artist;
        this.genre = genre;
        this.image = image;
        this.melodyName = melodyName;
        this.url = url;
        this.votes = votes;
    }

    @Override
    public String toString() {
        return "MusicVoted{" +
                "artist='" + artist + '\'' +
                ", genre='" + genre + '\'' +
                ", image='" + image + '\'' +
                ", melodyName='" + melodyName + '\'' +
                ", url='" + url + '\'' +
                ", votes=" + votes +
                '}';
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMelodyName() {
        return melodyName;
    }

    public void setMelodyName(String melodyName) {
        this.melodyName = melodyName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    @Override
    public int compareTo(@NonNull MusicVoted musicVoted) {

        return Integer.compare( musicVoted.votes,this.votes);
    }
}
