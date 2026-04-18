package model;

import interfaces.Playable;
import interfaces.Searchable;

public class Song extends MediaItem implements Playable, Searchable {
    private String artistName;
    private String genre;
    private int playCount;

    public Song() {
    }

    public Song(int id, String title, int duration, String artistName, String genre, int playCount) {
        super(id, title, duration);
        this.artistName = artistName;
        this.genre = genre;
        this.playCount = playCount;
    }

    @Override
    public void play() {
        System.out.println("Now playing: " + getTitle() + " by " + artistName);
        playCount++;
    }

    @Override
    public boolean matches(String keyword) {
        if (keyword == null) {
            return false;
        }

        String lowerKeyword = keyword.toLowerCase();

        return getTitle().toLowerCase().contains(lowerKeyword)
                || artistName.toLowerCase().contains(lowerKeyword)
                || genre.toLowerCase().contains(lowerKeyword);
    }

    @Override
    public String getDetails() {
        return "Song ID: " + getId() +
                ", Title: " + getTitle() +
                ", Duration: " + getDuration() + " sec" +
                ", Artist: " + artistName +
                ", Genre: " + genre +
                ", Plays: " + playCount;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getPlayCount() {
        return playCount;
    }

    public void setPlayCount(int playCount) {
        this.playCount = playCount;
    }
}