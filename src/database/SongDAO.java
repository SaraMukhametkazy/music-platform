package database;

import model.Song;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SongDAO {

    public boolean addSong(Song song) {
        String sql = "INSERT INTO songs (title, duration, artist_name, genre, play_count) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, song.getTitle());
            statement.setInt(2, song.getDuration());
            statement.setString(3, song.getArtistName());
            statement.setString(4, song.getGenre());
            statement.setInt(5, song.getPlayCount());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Song getSongById(int id) {
        String sql = "SELECT * FROM songs WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return extractSongFromResultSet(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Song> getAllSongs() {
        List<Song> songs = new ArrayList<>();
        String sql = "SELECT * FROM songs";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                songs.add(extractSongFromResultSet(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return songs;
    }

    public List<Song> searchSongs(String keyword) {
        List<Song> songs = new ArrayList<>();
        String sql = "SELECT * FROM songs WHERE title LIKE ? OR artist_name LIKE ? OR genre LIKE ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            String pattern = "%" + keyword + "%";

            statement.setString(1, pattern);
            statement.setString(2, pattern);
            statement.setString(3, pattern);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                songs.add(extractSongFromResultSet(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return songs;
    }

    public boolean deleteSong(int id) {
        String sql = "DELETE FROM songs WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updatePlayCount(int songId, int newPlayCount) {
        String sql = "UPDATE songs SET play_count = ? WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, newPlayCount);
            statement.setInt(2, songId);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Song extractSongFromResultSet(ResultSet rs) throws SQLException {
        return new Song(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getInt("duration"),
                rs.getString("artist_name"),
                rs.getString("genre"),
                rs.getInt("play_count")
        );
    }
}