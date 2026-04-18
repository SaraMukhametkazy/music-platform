package service;

import database.PlaylistDAO;
import model.Playlist;
import model.Song;

import java.util.List;

public class PlaylistService {

    private PlaylistDAO playlistDAO;

    public PlaylistService() {
        this.playlistDAO = new PlaylistDAO();
    }

    public boolean createPlaylist(Playlist playlist) {
        if (playlist.getName() == null) {
            System.out.println("Playlist name cannot be empty");
            return false;
        }

        return playlistDAO.createPlaylist(playlist);
    }

    public Playlist getPlaylistById(int id) {
        return playlistDAO.getPlaylistById(id);
    }

    public List<Playlist> getAllPlaylists() {
        return playlistDAO.getAllPlaylists();
    }

    public boolean addSongToPlaylist(int playlistId, int songId) {
        return playlistDAO.addSongToPlaylist(playlistId, songId);
    }

    public List<Song> getSongsFromPlaylist(int playlistId) {
        return playlistDAO.getSongsByPlaylistId(playlistId);
    }

    public boolean deletePlaylist(int id) {
        return playlistDAO.deletePlaylist(id);
    }
}