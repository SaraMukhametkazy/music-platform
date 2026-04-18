package model;

public class Listener extends User {
    private String favoriteGenre;

    public Listener() {
    }

    public Listener(int id, String username, String password, String email, String favoriteGenre) {
        super(id, username, password, email);
        this.favoriteGenre = favoriteGenre;
    }

    @Override
    public String getRole() {
        return "Listener";
    }

    public String getFavoriteGenre() {
        return favoriteGenre;
    }

    public void setFavoriteGenre(String favoriteGenre) {
        this.favoriteGenre = favoriteGenre;
    }

    @Override
    public String getUserInfo() {
        return super.getUserInfo() + ", Favorite Genre: " + favoriteGenre;
    }
}