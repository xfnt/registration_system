package org.fnt.ui.menu.timesheet;

import org.fnt.ui.MenuHolder;
import org.fnt.ui.menu.IMenu;
import org.fnt.ui.menu.MenuType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class TimesheetMenu implements IMenu, MouseListener {

    private MenuType type = MenuType.TIMESHEET;
    private MenuHolder menuHolder;
    private JPanel panel;
    private GroupLayout layout;

    private JLabel title, viewTimesheet, setWorkingTime, backLabel;

    public TimesheetMenu(MenuHolder menuHolder, JPanel panel, GroupLayout layout) {
        this.menuHolder = menuHolder;
        this.panel = panel;
        this.layout = layout;

        Font titleFont = new Font(Font.SERIF, Font.BOLD, 18);
        Font labelFont = new Font(Font.SERIF, Font.PLAIN, 14);

        title = new JLabel("<html>МЕНЮ УЧЕТА РАБОЧЕГО ВРЕМЕНИ<hr></html>", JLabel.CENTER);
        title.setFont(titleFont);

        viewTimesheet = new JLabel("<html>РАСПИСАНИЕ</html>", JLabel.CENTER);
        viewTimesheet.setName("VIEW");
        viewTimesheet.setFont(labelFont);
        viewTimesheet.addMouseListener(this);

        setWorkingTime = new JLabel("<html>ПЛАНИРОВАНИЕ</html>", JLabel.CENTER);
        setWorkingTime.setName("TIME");
        setWorkingTime.setFont(labelFont);
        setWorkingTime.addMouseListener(this);

        backLabel = new JLabel("<html>НАЗАД</html>", JLabel.CENTER);
        backLabel.setName("BACK");
        backLabel.setFont(labelFont);
        backLabel.addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getComponent().getClass().equals(JLabel.class)) {
            JLabel label = (JLabel) e.getComponent();
            switch (label.getName()) {
                case "VIEW":
                    label.setForeground(Color.BLACK);
                    label.setBorder(null);
                    menuHolder.getMenu(MenuType.TIMESHEET_VIEW).show();
                    break;
                case "TIME":
                    label.setForeground(Color.BLACK);
                    label.setBorder(null);
                    menuHolder.getMenu(MenuType.TIMESHEET_PLAIN).show();
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

    public void update() {

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
                                        .addComponent(viewTimesheet)
                                        .addComponent(setWorkingTime)
                                        .addComponent(backLabel)
                        )
        );
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(title)
                        .addGroup(
                                layout.createSequentialGroup()
                                        .addComponent(viewTimesheet)
                                        .addComponent(setWorkingTime)
                                        .addComponent(backLabel)
                        )
        );
    }

    @Override
    public MenuType getType() {
        return type;
    }
}
