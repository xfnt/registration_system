package org.fnt.ui.menu;

import org.fnt.model.entity.Sendable;
import org.fnt.model.entity.user.User;
import org.fnt.model.message.Message;
import org.fnt.model.message.MessageType;
import org.fnt.ui.MenuHolder;
import org.fnt.util.DateFormatUtils;
import org.fnt.util.PhoneFormatUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ProfileMenu implements IMenu, ActionListener, MouseListener {

    private MenuType type = MenuType.USERPROFILE;
    private MenuHolder menuHolder;
    private JPanel panel;
    private GroupLayout layout;

    private JLabel title, idLabel, firstNameLabel, lastNameLabel, middleNameLabel, birthDateLabel, phoneNumberLabel;
    private JTextField idField, firstNameField, lastNameField, middleNameField, birthDateField, phoneNumberField;

    private JButton accept, back;

    public ProfileMenu(MenuHolder menuHolder, JPanel panel, GroupLayout layout) {
        this.menuHolder = menuHolder;
        this.panel = panel;

        this.layout = layout;

        Font titleFont = new Font(Font.SERIF, Font.BOLD, 18);
        Font lableFont = new Font(Font.SERIF, Font.PLAIN, 14);
        Font fieldFont = new Font(Font.SERIF, Font.BOLD, 14);

        title = new JLabel("<html>ИНФОРМАЦИЯ О ПОЛЬЗОВАТЕЛЕ<hr></html>");
        title.setFont(titleFont);
        title.setHorizontalAlignment(SwingConstants.CENTER);

        idLabel = new JLabel("ID:");
        idLabel.setFont(lableFont);
        idField = new JTextField();
        idField.setFont(fieldFont);
        idField.setEditable(false);

        firstNameLabel = new JLabel("Имя:");
        firstNameLabel.setFont(lableFont);
        firstNameField = new JTextField();
        firstNameField.setFont(fieldFont);
        firstNameField.setName("firstName");
        firstNameField.setEditable(false);
        firstNameField.addMouseListener(this);

        lastNameLabel = new JLabel("Фамилия:");
        lastNameLabel.setFont(lableFont);
        lastNameField = new JTextField();
        lastNameField.setFont(fieldFont);
        lastNameField.setName("lastName");
        lastNameField.setEditable(false);
        lastNameField.addMouseListener(this);

        middleNameLabel = new JLabel("Отчество:");
        middleNameLabel.setFont(lableFont);
        middleNameField = new JTextField();
        middleNameField.setFont(fieldFont);
        middleNameField.setName("middleName");
        middleNameField.setEditable(false);
        middleNameField.addMouseListener(this);

        birthDateLabel = new JLabel("Дата рождения:");
        birthDateLabel.setFont(lableFont);
        birthDateField = new JTextField();
        birthDateField.setFont(fieldFont);
        birthDateField.setName("birthDate");
        birthDateField.setEditable(false);
        birthDateField.addMouseListener(this);

        phoneNumberLabel = new JLabel("Номер телефона:");
        phoneNumberLabel.setFont(lableFont);
        phoneNumberField = new JTextField();
        phoneNumberField.setFont(fieldFont);
        phoneNumberField.setName("phoneNumber");
        phoneNumberField.setEditable(false);
        phoneNumberField.addMouseListener(this);

        accept = new JButton("СОХРАНИТЬ");
        accept.setName("ACCEPT");
        accept.addActionListener(this);

        back = new JButton("НАЗАД");
        back.setName("BACK");
        back.addActionListener(this);

    }

    @Override
    public MenuType getType() {
        return type;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().getClass().equals(JButton.class)) {
            JButton button = (JButton) e.getSource();
            switch (button.getName()) {
                case "ACCEPT":
                    checkFields();
                    update();
                    break;
                case "BACK":
                    menuHolder.getMenu(MenuType.MAIN).show();
                    break;
            }
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getComponent().getClass().equals(JTextField.class)){
            JTextField field = (JTextField) e.getComponent();
            if(field.getName() != null){
                field.setEditable(true);
                field.setFocusable(true);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if(e.getComponent().getClass().equals(JTextField.class)){
            JTextField field = (JTextField) e.getComponent();
            if(field.getName() != null){
                field.setFocusable(false);
                field.setEditable(false);
            }
        }
    }

    public void update() {
        menuHolder.getUser().setFirstName(firstNameField.getText());
        menuHolder.getUser().setMiddleName(middleNameField.getText());
        menuHolder.getUser().setLastName(lastNameField.getText());
        menuHolder.getUser().setBirthDate(DateFormatUtils.parseToLocalDate(birthDateField.getText()));
        menuHolder.getUser().setPhoneNumber(phoneNumberField.getText());

        Message<Sendable> message = menuHolder.getUserService().editUser(menuHolder.getUser().getId(),menuHolder.getUser());
        if(message.getType().equals(MessageType.ERROR)) {
            message = menuHolder.getUserService().getUserById(menuHolder.getUser().getId());
            if(!message.getType().equals(MessageType.ERROR)) {
                User user = (User) message.getBody().get(0);
                menuHolder.setUser(user);
            }else {
                JOptionPane.showMessageDialog(panel, "Что-то пошло не так, обратитесь к разработчику...");
                return;
            }
            JOptionPane.showMessageDialog(panel, "Не удалось внести изменения, попробуйте еще раз...");
            return;
        }else {
            JOptionPane.showMessageDialog(panel, "Данные о пользователе успешно сохранены...");
            return;
        }
    }

    private void fillField() {
        idField.setText(menuHolder.getUser().getId());
        firstNameField.setText(menuHolder.getUser().getFirstName());
        middleNameField.setText(menuHolder.getUser().getMiddleName());
        lastNameField.setText(menuHolder.getUser().getLastName());
        birthDateField.setText(DateFormatUtils.parseToString(menuHolder.getUser().getBirthDate()));
        phoneNumberField.setText(menuHolder.getUser().getPhoneNumber());
    }

    public boolean checkFields() {
        if(
            firstNameField.getText().isEmpty() ||
            lastNameField.getText().isEmpty() ||
            middleNameField.getText().isEmpty() ||
            birthDateField.getText().isEmpty() ||
            phoneNumberField.getText().isEmpty()
        ) {
            JOptionPane.showMessageDialog(panel,"Заполните указанные поля...");
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
        fillField();

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup()
                .addComponent(title)
                .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup()
                                .addComponent(idLabel)
                                .addComponent(firstNameLabel)
                                .addComponent(middleNameLabel)
                                .addComponent(lastNameLabel)
                                .addComponent(birthDateLabel)
                                .addComponent(phoneNumberLabel)
                        )
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(idField)
                                .addComponent(firstNameField)
                                .addComponent(middleNameField)
                                .addComponent(lastNameField)
                                .addComponent(birthDateField)
                                .addComponent(phoneNumberField)
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(accept)
                                        .addComponent(back)
                                )
                        )
                )
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(title)
                .addGroup(layout.createParallelGroup()
                        .addComponent(idLabel)
                        .addComponent(idField)
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
                        .addComponent(accept)
                        .addComponent(back)
                )
        );

        layout.linkSize(SwingConstants.VERTICAL,
                idLabel, idField,
                firstNameLabel, firstNameField,
                lastNameLabel, lastNameField,
                middleNameLabel, middleNameField,
                birthDateLabel, birthDateField,
                phoneNumberLabel, phoneNumberField
        );

        layout.linkSize(SwingConstants.VERTICAL, accept, back);
        layout.linkSize(SwingConstants.HORIZONTAL, accept,  back);
    }
}
