package service;

import database.UserDAO;
import model.User;

public class AuthService {

    private UserDAO userDAO;

    public AuthService() {
        this.userDAO = new UserDAO();
    }

    public boolean register(User user) {
        if (user.getUsername() == null || user.getPassword() == null) {
            System.out.println("Username and password cannot be empty");
            return false;
        }

        return userDAO.registerUser(user);
    }

    public User login(String username, String password) {
        if (username == null || password == null) {
            System.out.println("Invalid input");
            return null;
        }

        return userDAO.login(username, password);
    }
}
