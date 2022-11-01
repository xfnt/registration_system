package com.example.ui.menu.timesheet;

import com.example.Calendar;
import com.example.model.entity.Sendable;
import com.example.model.entity.Timetable;
import com.example.model.message.Message;
import com.example.model.message.MessageType;
import com.example.ui.MenuHolder;
import com.example.ui.menu.IMenu;
import com.example.ui.menu.MenuType;
import com.example.util.DateFormatUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class TimesheetPlainMenu implements IMenu, ActionListener, MouseListener {
    private MenuType type = MenuType.TIMESHEET_PLAIN;
    private MenuHolder menuHolder;
    private JPanel panel;
    private GroupLayout layout;

    private JLabel title;
    private Calendar calendar;
    private JPanel recordPanel;
    private JScrollPane scrollPane;
    private JButton addTime, save, back;

    private Set<LocalDateTime> localDateTimeSet;

    public TimesheetPlainMenu(MenuHolder menuHolder, JPanel panel, GroupLayout layout) {
        this.menuHolder = menuHolder;
        this.panel = panel;
        this.layout = layout;

        localDateTimeSet = new TreeSet<>();

        Font titleFont = new Font(Font.SERIF, Font.BOLD, 18);

        title = new JLabel("<html>МЕНЮ ПЛАНИРОВАНИЯ РАБОЧЕГО ВРЕМЕНИ<html>", JLabel.CENTER);
        title.setFont(titleFont);

        calendar = new Calendar();

        recordPanel = new JPanel();
        recordPanel.setLayout(new BoxLayout(recordPanel,BoxLayout.Y_AXIS));
        scrollPane = new JScrollPane(recordPanel);

        addTime = new JButton("ДОБАВИТЬ");
        addTime.setName("ADD");
        addTime.addActionListener(this);

        save = new JButton("СОХРАНИТЬ");
        save.setName("SAVE");
        save.addActionListener(this);

        back = new JButton("НАЗАД");
        back.setName("BACK");
        back.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().getClass().equals(JButton.class)) {
            JButton button = (JButton) e.getSource();
            switch (button.getName()) {
                case "BACK":
                    menuHolder.getMenu(MenuType.TIMESHEET).show();
                    break;
                case "SAVE":
                    List<Timetable> timetableList = new ArrayList<>();
                    for(LocalDateTime ldt : localDateTimeSet) {
                        timetableList.add(new Timetable(menuHolder.getUser().getId(),ldt));
                    }

                    Message<Sendable> message = menuHolder.getTimetableService().addTime(menuHolder.getUser().getId(),timetableList);
                    if(message.getType().equals(MessageType.ERROR)) {
                        JOptionPane.showMessageDialog(panel, "Не удалось сделать запись, обратитесь к администратору...");
                        return;
                    }else {
                        JOptionPane.showMessageDialog(panel, "Ваши часы внесены в систему...");
                    }

                    break;
                case "ADD":
                    LocalDateTime localDateTime = calendar.getDateTime();
                    if(!localDateTimeSet.contains(localDateTime)){
                        JLabel label = new JLabel(String.format(DateFormatUtils.parseDateTimeToString(localDateTime)));
                        label.addMouseListener(this);
                        localDateTimeSet.add(localDateTime);
                        recordPanel.add(label);
                        recordPanel.revalidate();
                    }
                    break;
            }
        }
    }

    public void update() {

    }

    @Override
    public void show() {
        panel.removeAll();

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                .addComponent(title)
                .addComponent(calendar)
                .addComponent(addTime)
                .addComponent(scrollPane)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(save)
                        .addComponent(back)
                )
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(title)
                .addComponent(calendar)
                .addComponent(addTime)
                .addComponent(scrollPane)
                .addGroup(layout.createParallelGroup()
                        .addComponent(save)
                        .addComponent(back)
                )
        );
    }

    @Override
    public MenuType getType() {
        return type;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        JLabel label = (JLabel) e.getSource();
        recordPanel.remove(label);
        recordPanel.repaint();
        recordPanel.revalidate();

        for(LocalDateTime t : localDateTimeSet) {
            if(String.format("%1$TD %1$TH:%1$TM",t).equals(label.getText())) {
                localDateTimeSet.remove(t);
                return;
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

    }
}
