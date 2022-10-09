package org.fnt.ui.menu.authentication;

import org.fnt.model.entity.AuthenticationInformation;
import org.fnt.model.entity.Sendable;
import org.fnt.model.entity.user.User;
import org.fnt.model.entity.user.UserType;
import org.fnt.model.message.Message;
import org.fnt.model.message.MessageType;
import org.fnt.ui.MenuHolder;
import org.fnt.ui.menu.IMenu;
import org.fnt.ui.menu.MenuType;
import org.fnt.util.AuthUtils;
import org.fnt.util.DateFormatUtils;
import org.fnt.util.PhoneFormatUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class SignupMenu implements IMenu, ActionListener {
    private MenuType type = MenuType.SIGNUP;

    private GroupLayout layout;
    private MenuHolder menuHolder;
    private JPanel panel;
    private JLabel title, loginLabel, passwordLabel, firstNameLabel,
            lastNameLabel, middleNameLabel, birthDateLabel, phoneNumberLabel;
    private JTextField loginField, firstNameField, lastNameField, middleNameField, birthDateField, phoneNumberField;
    private JPasswordField passwordField;
    private JCheckBox showPass;
    private JButton signUp, back;

    public SignupMenu(MenuHolder menuHolder, JPanel panel, GroupLayout layout) {
        this.menuHolder = menuHolder;
        this.panel = panel;

        this.layout = layout;

        Font titleFont = new Font(Font.SERIF, Font.BOLD, 18);
        Font lableFont = new Font(Font.SERIF, Font.PLAIN, 14);
        Font fieldFont = new Font(Font.SERIF, Font.BOLD, 14);

        title = new JLabel("<html>МЕНЮ РЕГИСТРАЦИИ<hr></html>");
        title.setFont(titleFont);
        title.setHorizontalAlignment(SwingConstants.CENTER);

        loginLabel = new JLabel("Логин: ");
        loginLabel.setFont(lableFont);
        loginField = new JTextField();
        loginField.setFont(fieldFont);

        passwordLabel = new JLabel("Пароль: ");
        passwordLabel.setFont(lableFont);
        passwordField = new JPasswordField();

        showPass = new JCheckBox();
        showPass.addActionListener(this);

        firstNameLabel = new JLabel("Имя:");
        firstNameLabel.setFont(lableFont);
        firstNameField = new JTextField();
        firstNameField.setFont(fieldFont);
        firstNameField.setName("firstName");

        lastNameLabel = new JLabel("Фамилия:");
        lastNameLabel.setFont(lableFont);
        lastNameField = new JTextField();
        lastNameField.setFont(fieldFont);
        lastNameField.setName("lastName");

        middleNameLabel = new JLabel("Отчество:");
        middleNameLabel.setFont(lableFont);
        middleNameField = new JTextField();
        middleNameField.setFont(fieldFont);
        middleNameField.setName("middleName");

        birthDateLabel = new JLabel("Дата рождения:");
        birthDateLabel.setFont(lableFont);
        birthDateField = new JTextField();
        birthDateField.setFont(fieldFont);
        birthDateField.setName("birthDate");

        phoneNumberLabel = new JLabel("Номер телефона:");
        phoneNumberLabel.setFont(lableFont);
        phoneNumberField = new JTextField();
        phoneNumberField.setFont(fieldFont);
        phoneNumberField.setName("phoneNumber");

        signUp = new JButton("Зарегистрироваться");
        signUp.setName("SIGNUP");
        signUp.addActionListener(this);

        back = new JButton("Назад");
        back.setName("BACK");
        back.addActionListener(this);
    }

    private boolean registration() {
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
        Message<Sendable> message = menuHolder.getAuthService().registration(authenticationInformation);
        if(message.getType().equals(MessageType.ERROR)) {
            JOptionPane.showMessageDialog(panel,"Такой аккаунт уже существует");
            return false;
        }
        User user = new User(
                authenticationInformation.getLogin(),
                firstNameField.getText(),
                lastNameField.getText(),
                middleNameField.getText(),
                DateFormatUtils.parseToLocalDate(birthDateField.getText()),
                phoneNumberField.getText(),
                UserType.USER,
                false,
                false
        );
        message = menuHolder.getUserService().createUser(user);
        if(!message.getType().equals(MessageType.ERROR)) {
            JOptionPane.showMessageDialog(panel,"Пользователь создан успешно");
        }else {
            JOptionPane.showMessageDialog(panel,"Создать пользователя не удалось");
            return false;
        }
        return true;
    }

    @Override
    public MenuType getType() {
        return type;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource().equals(showPass)) {
            if(showPass.isSelected()) {
                passwordField.setEchoChar((char) 0);
            }else {
                passwordField.setEchoChar('*');
            }
        }
        if(e.getSource().equals(signUp)) {
            if(checkFields()) {
                if(registration()){
                    clearFields();
                    menuHolder.getMenu(MenuType.LOGIN).show();
                }
            }
        }

        if(e.getSource().equals(back)) {
            clearFields();
            menuHolder.getMenu(MenuType.LOGIN).show();
        }
    }

    private void clearFields() {
        loginField.setText("");
        passwordField.setText("");
        firstNameField.setText("");
        lastNameField.setText("");
        middleNameField.setText("");
        birthDateField.setText("");
        phoneNumberField.setText("");
    }

    public boolean checkFields() {
        if(
            loginField.getText().isEmpty() ||
            passwordField.getPassword().length == 0 ||
            firstNameField.getText().isEmpty() ||
            lastNameField.getText().isEmpty() ||
            middleNameField.getText().isEmpty() ||
            birthDateField.getText().isEmpty() ||
            phoneNumberField.getText().isEmpty()

        ) {
            JOptionPane.showMessageDialog(panel,"Заполните указанные поля...");
            return false;
        }

        if(!AuthUtils.isNormalLogin(loginField.getText())) {
            JOptionPane.showMessageDialog(panel,"Логин должен состоять из букв латинского алфавита и знака '_'...");
            return false;
        }

        if(!AuthUtils.isNormalPass(String.valueOf(passwordField.getPassword()))) {
            JOptionPane.showMessageDialog(panel,"Пароль должен состоять из цифр и букв латинского алфавита...");
            return false;
        }

        if(!DateFormatUtils.isParsebaleDate(birthDateField.getText())) {
            JOptionPane.showMessageDialog(panel,"Дата рождения должна быть в формате dd/MM/yyyy...");
            return false;
        }

        if(!PhoneFormatUtils.isParsablePhone(phoneNumberField.getText())) {
            JOptionPane.showMessageDialog(panel,"Номер телефона должен быть в формате +7XXXXXXXXXX...");
            return false;
        }

        return true;
    }
    @Override
    public void show() {
        panel.removeAll();

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup()
                .addComponent(title)
                .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup()
                                .addComponent(loginLabel)
                                .addComponent(passwordLabel)
                                .addComponent(firstNameLabel)
                                .addComponent(middleNameLabel)
                                .addComponent(lastNameLabel)
                                .addComponent(birthDateLabel)
                                .addComponent(phoneNumberLabel)
                        )
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(loginField)
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(passwordField)
                                        .addComponent(showPass)
                                )
                                .addComponent(firstNameField)
                                .addComponent(middleNameField)
                                .addComponent(lastNameField)
                                .addComponent(birthDateField)
                                .addComponent(phoneNumberField)
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(signUp)
                                        .addComponent(back)
                                )
                        )
                )
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
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
                        .addComponent(firstNameLabel)
                        .addComponent(firstNameField)
                )
                .addGroup(layout.createParallelGroup()
                        .addComponent(middleNameLabel)
                        .addComponent(middleNameField)
                )
                .addGroup(layout.createParallelGroup()
                        .addComponent(lastNameLabel)
                        .addComponent(lastNameField)
                )
                .addGroup(layout.createParallelGroup()
                        .addComponent(birthDateLabel)
                        .addComponent(birthDateField)
                )
                .addGroup(layout.createParallelGroup()
                        .addComponent(phoneNumberLabel)
                        .addComponent(phoneNumberField)
                )
                .addGroup(layout.createParallelGroup()
                        .addComponent(signUp)
                        .addComponent(back)
                )
        );

        layout.linkSize(SwingConstants.VERTICAL,
                loginLabel, loginField,
                passwordLabel, passwordField,
                firstNameLabel, firstNameField,
                lastNameLabel, lastNameField,
                middleNameLabel, middleNameField,
                birthDateLabel, birthDateField,
                phoneNumberLabel, phoneNumberField
        );

        layout.linkSize(SwingConstants.VERTICAL, signUp, back);
        layout.linkSize(SwingConstants.HORIZONTAL, signUp,  back);
    }
}
