package com.example.ui.menu.appointment;

import com.example.model.entity.Sendable;
import com.example.model.entity.Timetable;
import com.example.model.message.Message;
import com.example.model.message.MessageType;
import com.example.ui.MenuHolder;
import com.example.ui.menu.IMenu;
import com.example.ui.menu.MenuType;
import com.example.util.DateFormatUtils;
import com.example.model.entity.user.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AppointmentPlainMenu implements IMenu, ActionListener {

    private MenuType type = MenuType.APPOINTMENT_PLAIN;
    private MenuHolder menuHolder;
    private JPanel panel;
    private GroupLayout layout;

    private JLabel title, employeeLabel, freeTimeLabel;
    private JComboBox<String> employee, freeTime;
    private JButton accept, back;

    private List<User> userList;
    private List<Timetable> timetableList;
    private String[] userNames;


    public AppointmentPlainMenu(MenuHolder menuHolder, JPanel panel, GroupLayout layout) {
        this.menuHolder = menuHolder;
        this.panel = panel;
        this.layout = layout;

        Font titleFont = new Font(Font.SERIF, Font.BOLD, 18);
        Font labelFont = new Font(Font.SERIF, Font.PLAIN, 14);

        title = new JLabel("<html>ЗАПИСЬ НА ПРИЕМ<html>", JLabel.CENTER);
        title.setFont(titleFont);

        employeeLabel = new JLabel("Сотрудник:");
        employeeLabel.setFont(labelFont);

        freeTimeLabel = new JLabel("Доступное для записи время");

        employee = new JComboBox<>();
        employee.addActionListener(this);

        freeTime = new JComboBox<>();

        accept = new JButton("ЗАПИСАТЬСЯ");
        accept.setName("ACCEPT");
        accept.addActionListener(this);
        back = new JButton("НАЗАД");
        back.setName("BACK");
        back.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(employee)) {
            String employeeId = null;
            for(User u:userList) {
                String userName = u.getFirstName() + " "
                        + u.getMiddleName() + " " +u.getLastName();
                if(userName.equals(employee.getSelectedItem())) {
                    employeeId = u.getId();
                }
            }

           Message<Sendable> message = menuHolder.getTimetableService().getFreeTime(employeeId);
            if(message.getType().equals(MessageType.ERROR)) {
                JOptionPane.showMessageDialog(panel, "Не удалось получить данные");
                return;
            }
            timetableList = message.getBody().stream().map(t->(Timetable) t).toList();
            String[] time = timetableList.stream().map(t-> DateFormatUtils.parseDateTimeToString(t.getTime())).toArray(String[]::new);
            freeTime.setModel(new DefaultComboBoxModel<>(time));
        }

        if(e.getSource().equals(accept)) {
            Timetable timetable = null;
            for (Timetable t:timetableList) {
                if(DateFormatUtils.parseDateTimeToString(t.getTime()).equals(freeTime.getSelectedItem())) {
                    timetable = t;
                }
            }
            if(timetable==null) return;
            timetable.setUserId(menuHolder.getUser().getId());
            Message<Sendable> message = menuHolder.getTimetableService().appoint(menuHolder.getUser().getId(), timetable);
            if(message.getType().equals(MessageType.ERROR)) {
                JOptionPane.showMessageDialog(panel, "Не удалось сделать запись...");
                return;
            }
            JOptionPane.showMessageDialog(panel, "Вы записались на прием");
        }

        if(e.getSource().equals(back)) menuHolder.getMenu(MenuType.APPOINTMENT).show();
    }

    public void update() {
        Message<Sendable> message = menuHolder.getUserService().getEmployees(menuHolder.getUser().getId());
        if(message.getType().equals(MessageType.ERROR)) {
            JOptionPane.showMessageDialog(panel, "Не удалось получить данные...");
            return;
        }
        userList = message.getBody().stream().map(u->(User)u).toList();
        String[] userNameList = userList.stream().map(u->u.getFirstName() + " "
                + u.getMiddleName() + " " +u.getLastName()).toArray(String[]::new);

        employee.setModel(new DefaultComboBoxModel<>(userNameList));
        employee.setSelectedItem(null);
    }

    @Override
    public void show() {
        panel.removeAll();
        update();

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup()
                .addComponent(title)
                .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup()
                                .addComponent(employeeLabel)
                                .addComponent(freeTimeLabel)
                        )
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(employee)
                                .addComponent(freeTime)
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
                        .addComponent(employeeLabel)
                        .addComponent(employee)
                )
                .addGroup(layout.createParallelGroup()
                        .addComponent(freeTimeLabel)
                        .addComponent(freeTime)
                )
                .addGroup(layout.createParallelGroup()
                        .addComponent(accept)
                        .addComponent(back)
                )
        );

        layout.linkSize(SwingConstants.VERTICAL,
                employeeLabel, employee, freeTimeLabel, freeTime
        );

        layout.linkSize(SwingConstants.VERTICAL, accept, back);
        layout.linkSize(SwingConstants.HORIZONTAL, accept,  back);
    }

    @Override
    public MenuType getType() {
        return type;
    }
}
