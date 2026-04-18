package database;

import model.Message;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {

    public boolean saveMessage(Message message) {
        String sql = "INSERT INTO messages (sender_id, sender_name, content, timestamp) VALUES (?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, message.getSenderId());
            statement.setString(2, message.getSenderName());
            statement.setString(3, message.getContent());
            statement.setTimestamp(4, Timestamp.valueOf(message.getTimestamp()));

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Message> getAllMessages() {
        List<Message> messages = new ArrayList<>();
        String sql = "SELECT * FROM messages ORDER BY timestamp ASC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                messages.add(extractMessageFromResultSet(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return messages;
    }

    public List<Message> getMessagesBySenderName(String senderName) {
        List<Message> messages = new ArrayList<>();
        String sql = "SELECT * FROM messages WHERE sender_name = ? ORDER BY timestamp ASC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, senderName);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                messages.add(extractMessageFromResultSet(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return messages;
    }

    public boolean deleteMessage(int id) {
        String sql = "DELETE FROM messages WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Message extractMessageFromResultSet(ResultSet rs) throws SQLException {
        Message message = new Message();
        message.setId(rs.getInt("id"));
        message.setSenderId(rs.getInt("sender_id"));
        message.setSenderName(rs.getString("sender_name"));
        message.setContent(rs.getString("content"));

        Timestamp timestamp = rs.getTimestamp("timestamp");
        if (timestamp != null) {
            message.setTimestamp(timestamp.toLocalDateTime());
        } else {
            message.setTimestamp(LocalDateTime.now());
        }

        return message;
    }
}
