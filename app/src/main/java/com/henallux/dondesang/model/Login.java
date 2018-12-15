package com.henallux.dondesang.model;

public class Login {

    private String UserName;
    private String Password;

    public Login(String login,String password)
    {
        this.UserName = login;
        this.Password = password;
    }

    public String getPassword() {
        return Password;
    }

    public String getLogin() {
        return UserName;
    }

    public void setPassword(String password) {
        this.Password = password;
    }

    public void setLogin(String login) {
        this.UserName = login;
    }
}
