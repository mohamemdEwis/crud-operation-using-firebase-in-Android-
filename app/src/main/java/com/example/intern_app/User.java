package com.example.intern_app;

public class User {


    String user;
    String password;
    String phone;
    String email;

    public User() {
    }

    public User(String user, String password, String phone, String email) {
        this.user =user;
        this.password = password;
        this.phone = phone;
        this.email = email;
    }

    public String getuser() {
        return user;
    }

    public void setuser(String user) {
        this.user = user;
    }

    public String getpassword() {
        return password;
    }

    public void setpassword(String password) {
        this.password = password;
    }

    public String getphone() {
        return phone;
    }

    public void setphone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
