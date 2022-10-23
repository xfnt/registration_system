package com.example.ui.menu.appointment;

import com.example.ui.MenuHolder;
import com.example.ui.menu.IMenu;
import com.example.ui.menu.MenuType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class AppointmentMenu implements IMenu, MouseListener {

    private MenuType type = MenuType.APPOINTMENT;
    private MenuHolder menuHolder;
    private JPanel panel;
    private GroupLayout layout;

    private JLabel title, makeAnAppointment, viewAppontmentRecord, backLabel;

    public AppointmentMenu(MenuHolder menuHolder, JPanel panel, GroupLayout layout) {
        this.menuHolder = menuHolder;
        this.panel = panel;
        this.layout = layout;

        Font titleFont = new Font(Font.SERIF, Font.BOLD, 18);
        Font labelFont = new Font(Font.SERIF, Font.PLAIN, 14);

        title = new JLabel("<html>МЕНЮ ЗАПИСИ НА ПРИЕМ<hr></html>", JLabel.CENTER);
        title.setFont(titleFont);

        makeAnAppointment = new JLabel("<html>ЗАПИСАТЬСЯ</html>", JLabel.CENTER);
        makeAnAppointment.setName("PLAIN");
        makeAnAppointment.setFont(labelFont);
        makeAnAppointment.addMouseListener(this);

        viewAppontmentRecord = new JLabel("<html>ПОСМОТРЕТЬ СВОИ ЗАПИСИ</html>", JLabel.CENTER);
        viewAppontmentRecord.setName("VIEW");
        viewAppontmentRecord.setFont(labelFont);
        viewAppontmentRecord.addMouseListener(this);

        backLabel = new JLabel("<html>НАЗАД</html>", JLabel.CENTER);
        backLabel.setName("BACK");
        backLabel.setFont(labelFont);
        backLabel.addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getComponent().getClass().equals(JLabel.class)) {
            JLabel label = (JLabel) e.getComponent();
            switch(label.getName()) {
                case "PLAIN":
                    label.setForeground(Color.BLACK);
                    label.setBorder(null);
                    menuHolder.getMenu(MenuType.APPOINTMENT_PLAIN).show();
                    break;
                case "VIEW":
                    label.setForeground(Color.BLACK);
                    label.setBorder(null);
                    menuHolder.getMenu(MenuType.APPOINTMENT_VIEW).show();
                    break;
                case "BACK":
                    label.setForeground(Color.BLACK);
                    label.setBorder(null);
                    menuHolder.getMenu(MenuType.MAIN).show();
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
                                layout.createParallelGroup()
                                        .addComponent(title)
                                        .addComponent(makeAnAppointment)
                                        .addComponent(viewAppontmentRecord)
                                        .addComponent(backLabel)
                        )
        );
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(title)
                        .addGroup(
                                layout.createSequentialGroup()
                                        .addComponent(makeAnAppointment)
                                        .addComponent(viewAppontmentRecord)
                                        .addComponent(backLabel)
                        )
        );
    }

    @Override
    public MenuType getType() {
        return type;
    }
}
