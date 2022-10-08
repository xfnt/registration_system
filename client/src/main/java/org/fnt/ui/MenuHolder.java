package org.fnt.ui;

import org.fnt.model.entity.user.User;
import org.fnt.net.Client;
import org.fnt.service.*;
import org.fnt.ui.menu.*;
import org.fnt.ui.menu.authentication.LoginMenu;
import org.fnt.ui.menu.authentication.SignupMenu;
import org.fnt.ui.menu.rights.RightsMenu;
import org.fnt.ui.menu.timesheet.TimesheetMenu;
import org.fnt.ui.menu.timesheet.TimesheetPlainMenu;
import org.fnt.ui.menu.timesheet.TimesheetViewMenu;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class MenuHolder {
    private List<IMenu> menuList;
    private JPanel panel;
    private AuthenticationService authenticationService;
    private UserService userService;
    private UtilityService utilityService;
    private TimetableService timetableService;
    private User user;

    public MenuHolder(Client client) {
        menuList = new ArrayList<>();
        panel = new JPanel();
        MessageService messageService = new MessageService(client);
        authenticationService = new AuthenticationService(messageService);
        userService = new UserService(messageService);
        timetableService = new TimetableService(messageService);
        utilityService = new UtilityService(messageService);

        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);

        LoginMenu loginMenu = new LoginMenu(this, panel, layout);
        SignupMenu signupMenu = new SignupMenu(this, panel, layout);
        MainMenu mainMenu = new MainMenu(this, panel, layout);
        ProfileMenu profileMenu = new ProfileMenu(this, panel, layout);

        TimesheetMenu timesheetMenu = new TimesheetMenu(this, panel, layout);
        TimesheetPlainMenu timesheetPlainMenu = new TimesheetPlainMenu(this, panel, layout);
        TimesheetViewMenu timesheetViewMenu = new TimesheetViewMenu(this, panel, layout);

        RightsMenu rightsMenu = new RightsMenu(this, panel, layout);

        menuList.add(loginMenu);
        menuList.add(signupMenu);
        menuList.add(mainMenu);
        menuList.add(profileMenu);

        menuList.add(timesheetMenu);
        menuList.add(timesheetPlainMenu);
        menuList.add(timesheetViewMenu);
        menuList.add(rightsMenu);

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

    public TimetableService getTimetableService() {
        return timetableService;
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
