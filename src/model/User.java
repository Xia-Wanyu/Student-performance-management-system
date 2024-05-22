package model;

public class User {
    private int id;
    private String username;
    private String password;
    private String role;
    private boolean canViewGrades;
    private boolean canModifyGrades;
    private boolean canInputGrades;
    private boolean canModifyPersonalInfo;

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isCanViewGrades() {
        return canViewGrades;
    }

    public void setCanViewGrades(boolean canViewGrades) {
        this.canViewGrades = canViewGrades;
    }

    public boolean isCanModifyGrades() {
        return canModifyGrades;
    }

    public void setCanModifyGrades(boolean canModifyGrades) {
        this.canModifyGrades = canModifyGrades;
    }

    public boolean isCanInputGrades() {
        return canInputGrades;
    }

    public void setCanInputGrades(boolean canInputGrades) {
        this.canInputGrades = canInputGrades;
    }

    public boolean isCanModifyPersonalInfo() {
        return canModifyPersonalInfo;
    }

    public void setCanModifyPersonalInfo(boolean canModifyPersonalInfo) {
        this.canModifyPersonalInfo = canModifyPersonalInfo;
    }
}
