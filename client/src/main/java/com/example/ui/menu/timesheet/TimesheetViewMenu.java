package com.example.ui.menu.timesheet;

import com.example.model.entity.Sendable;
import com.example.model.entity.Timetable;
import com.example.model.message.Message;
import com.example.model.message.MessageType;
import com.example.ui.MenuHolder;
import com.example.ui.menu.IMenu;
import com.example.ui.menu.MenuType;
import com.example.ui.menu.PopupMenu;
import com.example.ui.menu.model.TimetableTableModel;
import com.example.model.entity.user.User;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TimesheetViewMenu implements IMenu, ActionListener, MouseListener {

    private MenuType type = MenuType.TIMESHEET_VIEW;
    private MenuHolder menuHolder;
    private JPanel panel;
    private GroupLayout layout;

    private JLabel title;
    private JTable table;
    private TimetableTableModel timetableTableModel;
    private JScrollPane scrollPane;
    private JButton back;
    private Set<User> userSet;
    private com.example.ui.menu.PopupMenu popupMenu;

    public TimesheetViewMenu(MenuHolder menuHolder, JPanel panel, GroupLayout layout) {

        this.menuHolder = menuHolder;
        this.panel = panel;
        this.layout = layout;

        popupMenu = new PopupMenu();

        Font titleFont = new Font(Font.SERIF, Font.BOLD, 18);
        Font itemFont = new Font(Font.SERIF, Font.PLAIN, 14);

        title = new JLabel("<html>РАСПИСАНИЕ<html>", JLabel.CENTER);
        title.setFont(titleFont);


        timetableTableModel = new TimetableTableModel();
        table = new JTable(timetableTableModel);
        table.setDragEnabled(false);
        table.setFont(itemFont);
        table.getTableHeader().setReorderingAllowed(false);
        table.addMouseListener(this);
        scrollPane = new JScrollPane(table);

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
            }
        }
    }

    public void update() {
        userSet = new HashSet<>();
        Message<Sendable> message = menuHolder.getTimetableService().getAllTime(menuHolder.getUser().getId());
        if(message.getType().equals(MessageType.ERROR)) {
            JOptionPane.showMessageDialog(panel, "Не удалось получить данные...");
            timetableTableModel.setData(new ArrayList<>());
            return;
        }
        List<Timetable> timetableList = message.getBody().stream().map(t->(Timetable)t).toList();
        timetableList.stream().forEach(item->{
            item.setEmployeeId(menuHolder.getUser().getFirstName() +
                    " " +
                    menuHolder.getUser().getMiddleName() + " " +
                    menuHolder.getUser().getLastName());
            Message<Sendable> msg = menuHolder.getUserService().getUserById(item.getUserId());
            if(!msg.getType().equals(MessageType.ERROR)) {
                User u = (User) msg.getBody().get(0);
                userSet.add(u);
                item.setUserId(u.getFirstName() +
                        " " +
                        u.getMiddleName() + " " +
                        u.getLastName());
            }
        });

        timetableTableModel.setData(timetableList);
        RowSorter<TimetableTableModel> sorter = new TableRowSorter<TimetableTableModel>(
                timetableTableModel);
        table.setRowSorter(sorter);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        popupMenu.hide();
        if(e.getSource().equals(table)) {
            int row = table.rowAtPoint(e.getPoint());
            User user = null;
            for(User u:userSet){
                if((u.getFirstName()+" "+u.getMiddleName() +" "+ u.getLastName()).equals(table.getValueAt(row, 1))) {
                    user = u;
                }
            }
            if(user == null) return;
            popupMenu.draw(e.getXOnScreen()+50, e.getYOnScreen(),table, user);
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
        popupMenu.hide();
    }

    @Override
    public void show() {
        panel.removeAll();
        update();

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                .addComponent(title)
                .addComponent(scrollPane)
                .addComponent(back)
                );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(title)
                .addComponent(scrollPane)
                .addComponent(back)
        );
    }

    @Override
    public MenuType getType() {
        return type;
    }
}
