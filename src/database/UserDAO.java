package database;

import model.Admin;
import model.Artist;
import model.Listener;
import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public boolean registerUser(User user) {
        String sql = "INSERT INTO users (username, password, email, role, favorite_genre, stage_name, bio, admin_level) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getRole());

            if (user instanceof Listener listener) {
                statement.setString(5, listener.getFavoriteGenre());
                statement.setString(6, null);
                statement.setString(7, null);
                statement.setString(8, null);
            } else if (user instanceof Artist artist) {
                statement.setString(5, null);
                statement.setString(6, artist.getStageName());
                statement.setString(7, artist.getBio());
                statement.setString(8, null);
            } else if (user instanceof Admin admin) {
                statement.setString(5, null);
                statement.setString(6, null);
                statement.setString(7, null);
                statement.setString(8, admin.getAdminLevel());
            } else {
                statement.setString(5, null);
                statement.setString(6, null);
                statement.setString(7, null);
                statement.setString(8, null);
            }

            return statement.executeUpdate() > 0;

        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("User with this username or email already exists.");
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public User login(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return extractUserFromResultSet(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                User user = extractUserFromResultSet(rs);
                if (user != null) {
                    users.add(user);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public boolean deleteUser(int id) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private User extractUserFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String username = rs.getString("username");
        String password = rs.getString("password");
        String email = rs.getString("email");
        String role = rs.getString("role");

        switch (role.toLowerCase()) {
            case "listener":
                return new Listener(
                        id,
                        username,
                        password,
                        email,
                        rs.getString("favorite_genre")
                );
            case "artist":
                return new Artist(
                        id,
                        username,
                        password,
                        email,
                        rs.getString("stage_name"),
                        rs.getString("bio")
                );
            case "admin":
                return new Admin(
                        id,
                        username,
                        password,
                        email,
                        rs.getString("admin_level")
                );
            default:
                return null;
        }
    }
}