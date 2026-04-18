package service;

import database.MessageDAO;
import model.Message;

import java.time.LocalDateTime;
import java.util.List;

public class ChatService {

    private final MessageDAO messageDAO;

    public ChatService() {
        this.messageDAO = new MessageDAO();
    }

    public boolean sendMessage(int senderId, String senderName, String content) {
        if (senderName == null || senderName.isBlank() || content == null || content.isBlank()) {
            System.out.println("Sender name and message content cannot be empty.");
            return false;
        }

        Message message = new Message();
        message.setSenderId(senderId);
        message.setSenderName(senderName);
        message.setContent(content);
        message.setTimestamp(LocalDateTime.now());

        return messageDAO.saveMessage(message);
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public List<Message> getMessagesBySenderName(String senderName) {
        return messageDAO.getMessagesBySenderName(senderName);
    }

    public boolean deleteMessage(int id) {
        return messageDAO.deleteMessage(id);
    }
}