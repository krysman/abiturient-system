package com.sevsu.util;


public class UserDetails {

    private final int userId;
    private final String userName;
    private final String userRole;

    public UserDetails(int userId, String userName, String userRole) {
        this.userId = userId;
        this.userName = userName;
        this.userRole = userRole;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserRole() {
        return userRole;
    }
}