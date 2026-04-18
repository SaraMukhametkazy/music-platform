package network;

import model.Message;
import service.ChatService;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler extends Thread {

    private static final List<ClientHandler> clients = new ArrayList<>();
    private static final ChatService chatService = new ChatService();

    private final Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private String username;

    public ClientHandler(Socket socket) {
        this.socket = socket;
        synchronized (clients) {
            clients.add(this);
        }
    }

    @Override
    public void run() {
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);

            writer.println("Enter your username:");
            username = reader.readLine();

            if (username == null || username.isBlank()) {
                username = "Anonymous";
            }

            sendChatHistory();

            broadcast("System: " + username + " joined the chat.");

            String message;
            while ((message = reader.readLine()) != null) {
                String fullMessage = username + ": " + message;

                System.out.println("Received: " + fullMessage);

                chatService.sendMessage(0, username, message);
                broadcast(fullMessage);
            }

        } catch (IOException e) {
            System.out.println(username + " disconnected.");
        } finally {
            synchronized (clients) {
                clients.remove(this);
            }

            if (username != null) {
                broadcast("System: " + username + " left the chat.");
            }

            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendChatHistory() {
        List<Message> history = chatService.getAllMessages();

        writer.println("----- Chat History -----");
        for (Message msg : history) {
            writer.println(msg.toString());
        }
        writer.println("------------------------");
    }

    private void broadcast(String message) {
        synchronized (clients) {
            for (ClientHandler client : clients) {
                if (client.writer != null) {
                    client.writer.println(message);
                }
            }
        }
    }
}