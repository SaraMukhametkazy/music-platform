import database.PlaylistDAO;
import model.Playlist;
import model.Song;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        PlaylistDAO playlistDAO = new PlaylistDAO();

        Playlist playlist = new Playlist(0, "My Favorites", 1);
        boolean created = playlistDAO.createPlaylist(playlist);
        System.out.println("Playlist created: " + created);

        boolean added1 = playlistDAO.addSongToPlaylist(1, 1);
        boolean added2 = playlistDAO.addSongToPlaylist(1, 2);

        System.out.println("Song 1 added: " + added1);
        System.out.println("Song 2 added: " + added2);

        Playlist foundPlaylist = playlistDAO.getPlaylistById(1);

        if (foundPlaylist != null) {
            System.out.println("\nPlaylist info:");
            System.out.println(foundPlaylist);

            System.out.println("\nSongs in playlist:");
            List<Song> songs = foundPlaylist.getSongs();
            for (Song song : songs) {
                System.out.println(song);
            }
        } else {
            System.out.println("Playlist not found.");
        }
    }
}