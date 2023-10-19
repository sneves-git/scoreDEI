package com.example.data;

import javax.persistence.*;

@Entity
@Table(name = "user_", schema = "public")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(unique=true, nullable = false)
    private String username, email, phone;
    @Column(nullable = false)
    private String name, password;
    @Column(nullable = false)
    private boolean admin;

    public User() {
    }

    public User(String username, String name, String email, String password, String phone, boolean admin) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.admin = admin;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", admin=" + admin +
                '}';
    }
}
