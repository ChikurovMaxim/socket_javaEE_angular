package com.server.entities;

/**
 * Created by Maksym on 27.11.2016.
 */
public enum Role {
    ADMIN("ADMIN"),PILOT("PILOT"),SUPERVISER("SUPERVISER");

    private String role;
    Role(String role) {
        this.role = role;
    }

    public Role getRoleValue(String role){
        return Role.valueOf(role);
    }

    public String getRole() {
        return role;
    }

}
