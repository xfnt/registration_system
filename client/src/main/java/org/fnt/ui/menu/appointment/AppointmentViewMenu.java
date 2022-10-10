package org.fnt.ui.menu.appointment;

import org.fnt.model.entity.Sendable;
import org.fnt.model.entity.Timetable;
import org.fnt.model.message.Message;
import org.fnt.model.message.MessageType;
import org.fnt.ui.MenuHolder;
import org.fnt.ui.menu.IMenu;
import org.fnt.ui.menu.MenuType;
import org.fnt.ui.menu.model.TimetableTableModel;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AppointmentViewMenu implements IMenu, ActionListener {

    private MenuType type = MenuType.APPOINTMENT_VIEW;
    private MenuHolder menuHolder;
    private JPanel panel;
    private GroupLayout layout;

    private JLabel title;
    private JTable table;
    private TimetableTableModel timetableTableModel;
    private JScrollPane scrollPane;
    private JButton back;



    public AppointmentViewMenu(MenuHolder menuHolder, JPanel panel, GroupLayout layout) {

        this.menuHolder = menuHolder;
        this.panel = panel;
        this.layout = layout;

        Font titleFont = new Font(Font.SERIF, Font.BOLD, 18);
        Font itemFont = new Font(Font.SERIF, Font.PLAIN, 14);

        title = new JLabel("<html>РАСПИСАНИЕ<html>", JLabel.CENTER);
        title.setFont(titleFont);

        timetableTableModel = new TimetableTableModel();
        table = new JTable(timetableTableModel);
        table.setDragEnabled(false);
        table.setFont(itemFont);
        table.getTableHeader().setReorderingAllowed(false);
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
                    menuHolder.getMenu(MenuType.APPOINTMENT).show();
                    break;
            }
        }
    }

    public void update() {
        Message<Sendable> message = menuHolder.getTimetableService().getUserTimetable(menuHolder.getUser().getId());
        if(message.getType().equals(MessageType.ERROR)) {
            JOptionPane.showMessageDialog(panel, "Не удалось получить данные...");
            timetableTableModel.setData(new ArrayList<>());
            return;
        }
        timetableTableModel.setData(message.getBody().stream().map(t->(Timetable) t).toList());
        RowSorter<TimetableTableModel> sorter = new TableRowSorter<TimetableTableModel>(
                timetableTableModel);
        table.setRowSorter(sorter);
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
