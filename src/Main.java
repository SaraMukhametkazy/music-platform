import model.Listener;
import model.Playlist;
import model.Song;
import model.User;
import service.AuthService;
import service.MusicService;
import service.PlaylistService;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        AuthService authService = new AuthService();
        MusicService musicService = new MusicService();
        PlaylistService playlistService = new PlaylistService();

        // 🔐 Регистрация
        User user = new Listener(0, "testuser", "123", "test@mail.com", "Pop");
        authService.register(user);

        // 🔑 Логин
        User loggedUser = authService.login("testuser", "123");
        System.out.println("Logged in: " + loggedUser);

        // 🎵 Добавление песни
        Song song = new Song(0, "Test Song", 180, "Test Artist", "Pop", 0);
        musicService.addSong(song);

        // 📃 Получение всех песен
        List<Song> songs = musicService.getAllSongs();
        for (Song s : songs) {
            System.out.println(s);
        }

        // ▶️ Проигрывание
        if (!songs.isEmpty()) {
            musicService.playSong(songs.get(0));
        }

        // 📂 Создание плейлиста
        Playlist playlist = new Playlist(0, "My Playlist", 1);
        playlistService.createPlaylist(playlist);

        // ➕ Добавление песни в плейлист
        playlistService.addSongToPlaylist(1, 1);

        // 📜 Получение плейлиста
        Playlist p = playlistService.getPlaylistById(1);
        System.out.println("Playlist: " + p);

        for (Song s : p.getSongs()) {
            System.out.println(" - " + s);
        }
    }
}