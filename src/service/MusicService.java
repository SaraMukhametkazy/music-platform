package service;

import database.SongDAO;
import model.Song;

import java.util.List;

public class MusicService {

    private SongDAO songDAO;

    public MusicService() {
        this.songDAO = new SongDAO();
    }

    public boolean addSong(Song song) {
        if (song.getTitle() == null || song.getArtistName() == null) {
            System.out.println("Invalid song data");
            return false;
        }

        return songDAO.addSong(song);
    }

    public List<Song> getAllSongs() {
        return songDAO.getAllSongs();
    }

    public Song getSongById(int id) {
        return songDAO.getSongById(id);
    }

    public List<Song> searchSongs(String keyword) {
        return songDAO.searchSongs(keyword);
    }

    public void playSong(Song song) {
        if (song != null) {
            song.play();
            songDAO.updatePlayCount(song.getId(), song.getPlayCount());
        }
    }

    public boolean deleteSong(int id) {
        return songDAO.deleteSong(id);
    }
}