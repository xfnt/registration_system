package com.example.ui.menu.authentication;

import com.example.model.entity.AuthenticationInformation;
import com.example.model.entity.Sendable;
import com.example.model.message.Message;
import com.example.model.message.MessageType;
import com.example.ui.MenuHolder;
import com.example.ui.menu.IMenu;
import com.example.ui.menu.MenuType;
import com.example.model.entity.user.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class LoginMenu implements IMenu, ActionListener {

    private MenuType type = MenuType.LOGIN;

    private GroupLayout layout;
    private MenuHolder menuHolder;
    private JPanel panel;
    private JLabel loginLabel, passwordLabel, programName, title;
    private JTextField loginField;
    private JPasswordField passwordField;
    private JCheckBox showPass;
    private JButton logIn, signUp;

    public LoginMenu(MenuHolder menuHolder, JPanel panel, GroupLayout layout) {
        this.menuHolder = menuHolder;
        this.panel = panel;

        this.layout = layout;

        programName = new JLabel("<html>СИСТЕМА РЕГИСТРАЦИИ ПОЛЬЗОВАТЕЛЕЙ</html>", JLabel.CENTER);
        programName.setFont(new Font(Font.SERIF, Font.BOLD, 24));
        title = new JLabel("<html>МЕНЮ АУТЕНТИФИКАЦИИ<hr></html>", JLabel.CENTER);
        title.setFont(new Font(Font.SERIF, Font.BOLD, 18));

        loginLabel = new JLabel("Логин:");
        passwordLabel = new JLabel("Пароль:");

        loginField = new JTextField();
        loginField.setName("LOGIN");

        passwordField = new JPasswordField();
        passwordField.setName("PASSWORD");

        showPass = new JCheckBox();
        showPass.addActionListener(this);

        logIn = new JButton("ВОЙТИ");
        logIn.addActionListener(this);
        signUp = new JButton("РЕГИСТРАЦИЯ");
        signUp.addActionListener(this);
    }

    @Override
    public MenuType getType() {
        return type;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(showPass)){
            if (showPass.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('*');
            }
        }

        if(e.getSource().equals(logIn)){
            if(authenticate()){
                Message<Sendable> message = menuHolder.getUserService().getUserById(loginField.getText());
                if(message.getType().equals(MessageType.ERROR)) {
                    JOptionPane.showMessageDialog(panel,
                            "Не удалось получить данные о пользователе...");
                    return;
                }
                User user = (User) message.getBody().get(0);
                if(user.isDeleted()) {
                    JOptionPane.showMessageDialog(panel,
                            "Пользователь помечен на удаление, дальнейшая работа невозможна, обратитесь к администратору.");
                    return;
                }
                menuHolder.setUser(user);
            }else {
                return;
            }
            clearFields();
            menuHolder.getMenu(MenuType.MAIN).show();
        }

        if(e.getSource().equals(signUp)) {
            clearFields();
            menuHolder.getMenu(MenuType.SIGNUP).show();
        }
    }

    private boolean authenticate() {
        AuthenticationInformation authenticationInformation = new AuthenticationInformation();
        authenticationInformation.setLogin(loginField.getText());
        authenticationInformation.setPassword(
                new String(
                        Base64.getEncoder()
                                .encode(
                                        String.valueOf(passwordField.getPassword()).getBytes(StandardCharsets.UTF_8)
                                )
                )
        );

        Message<Sendable> message = menuHolder.getAuthService().authenticate(authenticationInformation);
        if(message.getType().equals(MessageType.ERROR)) {
            JOptionPane.showMessageDialog(panel,
                    "Аутентификация не удалась, проверьте правильность логина и пароля");
            return false;
        }
        return true;
    }

    private void clearFields() {
        loginField.setText("");
        passwordField.setText("");
    }

    @Override
    public void show() {
        panel.removeAll();

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup()
                .addComponent(programName)
                .addComponent(title)
                .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup()
                                .addComponent(loginLabel)
                                .addComponent(passwordLabel)
                        )
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(loginField)
                                .addComponent(passwordField)
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(logIn)
                                        .addComponent(signUp)
                                )
                        )
                        .addComponent(showPass)
                )
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(programName)
                .addComponent(title)
                .addGroup(layout.createParallelGroup()
                        .addComponent(loginLabel)
                        .addComponent(loginField)
                )
                .addGroup(layout.createParallelGroup()
                        .addComponent(passwordLabel)
                        .addComponent(passwordField)
                        .addComponent(showPass)
                )
                .addGroup(layout.createParallelGroup()
                        .addComponent(logIn)
                        .addComponent(signUp)
                )
        );

        layout.linkSize(SwingConstants.VERTICAL, loginLabel, loginField, passwordLabel, passwordField, logIn, signUp);
    }
}
