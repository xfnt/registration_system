package org.fnt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalendarTest {

    public static void main(String[] args) {
        getDateTime();
    }

    public static void getDateTime() {
        EventQueue.invokeLater(()->{
            JFrame frame = new JFrame("TEST");
            frame.setSize(200, 100);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);

            JPanel panel = new JPanel();
            Calendar calendar = new Calendar();
            panel.setLayout(new BorderLayout());
            JButton button = new JButton("get date time");
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println(calendar.getDateTime());
                }
            });

            panel.add(calendar,BorderLayout.CENTER);
            panel.add(button, BorderLayout.SOUTH);

            frame.add(panel);
        });
    }
}
