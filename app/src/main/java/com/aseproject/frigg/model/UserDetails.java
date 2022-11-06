package com.aseproject.frigg.model;

public class UserDetails {

    private String email;
    private String password;
    private int inviteCode;
    private int fridge_id;
    private String message;
    private String full_name;

    public UserDetails(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UserDetails(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.full_name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(int inviteCode) {
        this.inviteCode = inviteCode;
    }

    public int getFridge_id() {
        return fridge_id;
    }

    public void setFridge_id(int fridge_id) {
        this.fridge_id = fridge_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }
}
