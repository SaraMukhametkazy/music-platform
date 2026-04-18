package model;

public class Admin extends User {
    private String adminLevel;

    public Admin() {
    }

    public Admin(int id, String username, String password, String email, String adminLevel) {
        super(id, username, password, email);
        this.adminLevel = adminLevel;
    }

    @Override
    public String getRole() {
        return "Admin";
    }

    public String getAdminLevel() {
        return adminLevel;
    }

    public void setAdminLevel(String adminLevel) {
        this.adminLevel = adminLevel;
    }

    @Override
    public String getUserInfo() {
        return super.getUserInfo() + ", Admin Level: " + adminLevel;
    }
}