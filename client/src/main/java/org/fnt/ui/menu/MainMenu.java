package org.fnt.ui.menu;

import org.fnt.model.entity.user.UserType;
import org.fnt.ui.MenuHolder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MainMenu implements IMenu, MouseListener {

    private MenuType type = MenuType.MAIN_MENU;

    private GroupLayout layout;
    private MenuHolder menuHolder;
    private JPanel panel;
    private JLabel title, profileMenu, appointmentMenu, timesheetMenu, allUserListMenu;
    private JLabel exit;


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
        allUserListMenu.setName("USERLIST");
        allUserListMenu.setFont(new Font(Font.SERIF, Font.PLAIN, 14));
        allUserListMenu.addMouseListener(this);

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
//                    menuHolder.getMenu(MenuType.PROFILE_MENU).show();
                    break;
                case "APPOINTMENT":
                    label.setForeground(Color.BLACK);
                    label.setBorder(null);
//                    menuHolder.getMenu(MenuType.APPOINTMENT).show();
                    break;
                case "TIMESHEET":
                    label.setForeground(Color.BLACK);
                    label.setBorder(null);
//                    menuHolder.getMenu(MenuType.TIMESHEET).show();
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
    public void update() {}

    @Override
    public void show() {
        panel.removeAll();

        if(menuHolder.getUser().getType().equals(UserType.EMPLOYEE)) {
            allUserListMenu.setText("");
        } else if(menuHolder.getUser().getType().equals(UserType.USER)) {
            allUserListMenu.setText("");
            timesheetMenu.setText("");
        }

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
                        .addComponent(exit)
        );
    }
}
