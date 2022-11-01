package com.example.ui;

import com.example.net.Client;
import com.example.model.entity.user.User;
import com.example.service.*;
import com.example.ui.menu.IMenu;
import com.example.ui.menu.MainMenu;
import com.example.ui.menu.MenuType;
import com.example.ui.menu.ProfileMenu;
import com.example.ui.menu.appointment.AppointmentMenu;
import com.example.ui.menu.appointment.AppointmentPlainMenu;
import com.example.ui.menu.appointment.AppointmentViewMenu;
import com.example.ui.menu.authentication.LoginMenu;
import com.example.ui.menu.authentication.SignupMenu;
import com.example.ui.menu.rights.RightsMenu;
import com.example.ui.menu.timesheet.TimesheetMenu;
import com.example.ui.menu.timesheet.TimesheetPlainMenu;
import com.example.ui.menu.timesheet.TimesheetViewMenu;

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
        AppointmentMenu appointmentMenu = new AppointmentMenu(this, panel, layout);
        AppointmentViewMenu appointmentViewMenu = new AppointmentViewMenu(this, panel, layout);
        AppointmentPlainMenu appointmentPlainMenu = new AppointmentPlainMenu(this, panel, layout);
        TimesheetMenu timesheetMenu = new TimesheetMenu(this, panel, layout);
        TimesheetPlainMenu timesheetPlainMenu = new TimesheetPlainMenu(this, panel, layout);
        TimesheetViewMenu timesheetViewMenu = new TimesheetViewMenu(this, panel, layout);
        RightsMenu rightsMenu = new RightsMenu(this, panel, layout);

        menuList.add(loginMenu);
        menuList.add(signupMenu);
        menuList.add(mainMenu);
        menuList.add(profileMenu);
        menuList.add(appointmentMenu);
        menuList.add(appointmentViewMenu);
        menuList.add(appointmentPlainMenu);
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
