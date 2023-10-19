package com.example.formdata;

import javax.persistence.Column;

public class FormUser {
    private String username, email, phone;
    private String name, password;
    private boolean admin;

    public FormUser() {}

    public FormUser(String username, String email, String phone, String name, String password, boolean admin) {
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.name = name;
        this.password = password;
        this.admin = admin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
