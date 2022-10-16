package org.fnt.ui.menu;

import org.fnt.model.entity.user.User;
import org.fnt.model.entity.user.UserType;

import javax.swing.*;
import java.awt.*;

public class PopupMenu {
    private int xCoord;
    private int yCoord;
    private Component parent;
    private User user;
    private PopupFactory popupFactory;
    private Popup popup;

    public PopupMenu() {
        popupFactory = new PopupFactory();
    }


    public void draw(int xCoord, int yCoord, Component parent, User user) {
        if(popup != null) {
            popup.hide();
        }

        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        sb.append("<br>");
        sb.append("Имя: ");
        sb.append(user.getFirstName());
        sb.append("<br>");
        sb.append("Отчество: ");
        sb.append(user.getMiddleName());
        sb.append("<br>");
        sb.append("Фамилия: ");
        sb.append(user.getLastName());
        sb.append("<br>");
        sb.append("Номер телефона: ");
        sb.append(user.getPhoneNumber());
        sb.append("</html>");

        popup = new PopupFactory().getPopup(parent, new JLabel(sb.toString()), xCoord, yCoord);
        popup.show();
    }

    public void hide() {
        if(popup == null) return;
        popup.hide();
    }
}
