package network;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private static final String HOST = "localhost";
    private static final int PORT = 12345;

    public void start() {
        try (Socket socket = new Socket(HOST, PORT)) {

            System.out.println("Connected to server!");

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            Scanner scanner = new Scanner(System.in);

            // Сначала читаем запрос имени
            String serverPrompt = reader.readLine();
            System.out.println(serverPrompt);

            String username = scanner.nextLine();
            writer.println(username);

            // Поток для получения сообщений
            new Thread(() -> {
                String response;
                try {
                    while ((response = reader.readLine()) != null) {
                        System.out.println(response);
                    }
                } catch (IOException e) {
                    System.out.println("Disconnected from server.");
                }
            }).start();

            // Отправка сообщений
            while (true) {
                String message = scanner.nextLine();
                writer.println(message);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}