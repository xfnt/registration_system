package org.fnt.ui.menu;

import org.fnt.model.entity.user.UserType;
import org.fnt.ui.MenuHolder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MainMenu implements IMenu, MouseListener {

    private MenuType type = MenuType.MAIN;

    private GroupLayout layout;
    private MenuHolder menuHolder;
    private JPanel panel;
    private JLabel title, profileMenu, appointmentMenu, timesheetMenu, allUserListMenu;
    private JLabel exit, login;


    public MainMenu(MenuHolder menuHolder, JPanel panel, GroupLayout layout) {
        this.menuHolder = menuHolder;
        this.panel = panel;

        this.layout = layout;

        title = new JLabel("<html>ГЛАВНОЕ МЕНЮ<hr></html>", JLabel.CENTER);
        title.setFont(new Font(Font.SERIF, Font.BOLD, 18));

        profileMenu = new JLabel("<html>ПРОФИЛЬ ПОЛЬЗОВАТЕЛЯ</html>", JLabel.CENTER);
        profileMenu.setName("PROFILE");
        profileMenu.setFont(new Font(Font.SERIF, Font.PLAIN, 14));
        profileMenu.addMouseListener(this);

        appointmentMenu = new JLabel("<html>ЗАПИСЬ НА ПРИЕМ</html>", JLabel.CENTER);
        appointmentMenu.setName("APPOINTMENT");
        appointmentMenu.setFont(new Font(Font.SERIF, Font.PLAIN, 14));
        appointmentMenu.addMouseListener(this);

        timesheetMenu = new JLabel("<html>РАСПИСАНИЕ (только для сотрудников)</html>", JLabel.CENTER);
        timesheetMenu.setName("TIMESHEET");
        timesheetMenu.setFont(new Font(Font.SERIF, Font.PLAIN, 14));
        timesheetMenu.addMouseListener(this);

        allUserListMenu = new JLabel("<html>СПИСОК ПОЛЬЗОВАТЕЛЕЙ (только для администратора)</html>", JLabel.CENTER);
        allUserListMenu.setName("RIGHTS");
        allUserListMenu.setFont(new Font(Font.SERIF, Font.PLAIN, 14));
        allUserListMenu.addMouseListener(this);

        login = new JLabel("<html>ЗАЙТИ ПОД ДРУГИМ ПОЛЬЗОВАТЕЛЕМ</html>", JLabel.CENTER);
        login.setName("LOGIN");
        login.setFont(new Font(Font.SERIF, Font.PLAIN, 14));
        login.addMouseListener(this);

        exit = new JLabel("<html>ВЫХОД</html>", JLabel.CENTER);
        exit.setName("EXIT");
        exit.setFont(new Font(Font.SERIF, Font.PLAIN, 14));
        exit.addMouseListener(this);
    }

    @Override
    public MenuType getType() {
        return type;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getComponent().getClass().equals(JLabel.class)) {
            JLabel label = (JLabel) e.getComponent();
            switch(label.getName()) {
                case "PROFILE":
                    label.setForeground(Color.BLACK);
                    label.setBorder(null);
                    menuHolder.getMenu(MenuType.USERPROFILE).show();
                    break;
                case "APPOINTMENT":
                    label.setForeground(Color.BLACK);
                    label.setBorder(null);
                    menuHolder.getMenu(MenuType.APPOINTMENT).show();
                    break;
                case "TIMESHEET":
                    label.setForeground(Color.BLACK);
                    label.setBorder(null);
                    menuHolder.getMenu(MenuType.TIMESHEET).show();
                    break;
                case "RIGHTS":
                    label.setForeground(Color.BLACK);
                    label.setBorder(null);
                    menuHolder.getMenu(MenuType.RIGHTS).show();
                    break;
                case "LOGIN":
                    label.setForeground(Color.BLACK);
                    label.setBorder(null);
                    menuHolder.setUser(null);
                    menuHolder.getMenu(MenuType.LOGIN).show();
                    break;
                case "EXIT":
                    label.setForeground(Color.BLACK);
                    label.setBorder(null);
                    menuHolder.getUtilityService().exit();
                    System.exit(0);
                    break;
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
        if(e.getComponent().getClass().equals(JLabel.class)) {
            JLabel label = (JLabel) e.getComponent();
            label.setForeground(Color.BLUE);
            label.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if(e.getComponent().getClass().equals(JLabel.class)) {
            JLabel label = (JLabel) e.getComponent();
            label.setForeground(Color.BLACK);
            label.setBorder(null);
        }
    }

    @Override
    public void show() {
        panel.removeAll();

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addGroup(
                                layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                        .addComponent(title)
                                        .addComponent(profileMenu)
                                        .addComponent(appointmentMenu)
                                        .addComponent(timesheetMenu)
                                        .addComponent(allUserListMenu)
                                        .addComponent(login)
                                        .addComponent(exit)
                        )
        );
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(title)
                        .addGroup(
                                layout.createSequentialGroup()
                                        .addComponent(profileMenu)
                                        .addComponent(appointmentMenu)
                                        .addComponent(timesheetMenu)
                                        .addComponent(allUserListMenu)
                        )
                        .addComponent(login)
                        .addComponent(exit)
        );

        if(menuHolder.getUser().getType().equals(UserType.EMPLOYEE) && !menuHolder.getUser().isAdmin()) {
            layout.replace(allUserListMenu, new JLabel());
        } else if(menuHolder.getUser().getType().equals(UserType.USER)) {
            layout.replace(timesheetMenu, new JLabel());
            layout.replace(allUserListMenu, new JLabel());
        }
    }
}
