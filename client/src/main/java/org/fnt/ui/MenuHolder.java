package org.fnt.ui;

import org.fnt.model.entity.user.User;
import org.fnt.net.Client;
import org.fnt.service.AuthenticationService;
import org.fnt.service.MessageService;
import org.fnt.service.UserService;
import org.fnt.service.UtilityService;
import org.fnt.ui.menu.IMenu;
import org.fnt.ui.menu.MainMenu;
import org.fnt.ui.menu.MenuType;
import org.fnt.ui.menu.authentication.LoginMenu;
import org.fnt.ui.menu.authentication.SignupMenu;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class MenuHolder {
    private List<IMenu> menuList;
    private JPanel panel;
    private AuthenticationService authenticationService;
    private UserService userService;
    private UtilityService utilityService;
    private User user;

    public MenuHolder(Client client) {
        menuList = new ArrayList<>();
        panel = new JPanel();
        MessageService messageService = new MessageService(client);
        authenticationService = new AuthenticationService(messageService);
        userService = new UserService(messageService);

        utilityService = new UtilityService(messageService);

        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);

        LoginMenu loginMenu = new LoginMenu(this, panel, layout);
        SignupMenu signupMenu = new SignupMenu(this, panel, layout);
        MainMenu mainMenu = new MainMenu(this, panel, layout);

        menuList.add(loginMenu);
        menuList.add(signupMenu);
        menuList.add(mainMenu);

        loginMenu.show();
    }

    public IMenu getMenu(MenuType type) {
        for (IMenu m : menuList) {
            if(m.getType().equals(type)) {
                return m;
            }
        }
        return null; // пока так, надо избавиться
    }

    public JPanel getPanel() {
        return panel;
    }

    public AuthenticationService getAuthService() {
        return authenticationService;
    }
    public UserService getUserService() {
        return userService;
    }
    public UtilityService getUtilityService() {
        return utilityService;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
