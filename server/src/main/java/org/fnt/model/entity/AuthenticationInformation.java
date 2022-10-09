package org.fnt.model.entity;

public class AuthenticationInformation implements Sendable {

    private String login;
    private String password;

    public AuthenticationInformation() {
    }

    public AuthenticationInformation(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
