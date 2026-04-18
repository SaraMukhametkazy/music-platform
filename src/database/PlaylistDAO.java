package database;

import model.Playlist;
import model.Song;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDAO {

    public boolean createPlaylist(Playlist playlist) {
        String sql = "INSERT INTO playlists (name, owner_id) VALUES (?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, playlist.getName());
            statement.setInt(2, playlist.getOwnerId());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Playlist getPlaylistById(int id) {
        String sql = "SELECT * FROM playlists WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                Playlist playlist = extractPlaylistFromResultSet(rs);
                playlist.setSongs(getSongsByPlaylistId(playlist.getId()));
                return playlist;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Playlist> getAllPlaylists() {
        List<Playlist> playlists = new ArrayList<>();
        String sql = "SELECT * FROM playlists";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                Playlist playlist = extractPlaylistFromResultSet(rs);
                playlist.setSongs(getSongsByPlaylistId(playlist.getId()));
                playlists.add(playlist);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return playlists;
    }

    public boolean deletePlaylist(int id) {
        String sql = "DELETE FROM playlists WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addSongToPlaylist(int playlistId, int songId) {
        String sql = "INSERT INTO playlist_songs (playlist_id, song_id) VALUES (?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, playlistId);
            statement.setInt(2, songId);

            return statement.executeUpdate() > 0;

        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("This song is already in the playlist or playlist/song does not exist.");
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeSongFromPlaylist(int playlistId, int songId) {
        String sql = "DELETE FROM playlist_songs WHERE playlist_id = ? AND song_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, playlistId);
            statement.setInt(2, songId);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Song> getSongsByPlaylistId(int playlistId) {
        List<Song> songs = new ArrayList<>();

        String sql = """
                SELECT s.id, s.title, s.duration, s.artist_name, s.genre, s.play_count
                FROM songs s
                JOIN playlist_songs ps ON s.id = ps.song_id
                WHERE ps.playlist_id = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, playlistId);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                songs.add(new Song(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getInt("duration"),
                        rs.getString("artist_name"),
                        rs.getString("genre"),
                        rs.getInt("play_count")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return songs;
    }

    private Playlist extractPlaylistFromResultSet(ResultSet rs) throws SQLException {
        return new Playlist(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getInt("owner_id")
        );
    }
}