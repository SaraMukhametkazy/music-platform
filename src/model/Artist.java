package model;

public class Artist extends User {
    private String stageName;
    private String bio;

    public Artist() {
    }

    public Artist(int id, String username, String password, String email, String stageName, String bio) {
        super(id, username, password, email);
        this.stageName = stageName;
        this.bio = bio;
    }

    @Override
    public String getRole() {
        return "Artist";
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    @Override
    public String getUserInfo() {
        return super.getUserInfo() +
                ", Stage Name: " + stageName +
                ", Bio: " + bio;
    }
}