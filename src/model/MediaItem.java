package model;

import java.io.Serializable;

public abstract class MediaItem implements Serializable {
    private int id;
    private String title;
    private int duration; // in seconds

    public MediaItem() {
    }

    public MediaItem(int id, String title, int duration) {
        this.id = id;
        this.title = title;
        this.duration = duration;
    }

    public abstract String getDetails();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return getDetails();
    }
}