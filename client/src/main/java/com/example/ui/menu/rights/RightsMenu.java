package com.example.ui.menu.rights;

import com.example.model.entity.Sendable;
import com.example.model.message.Message;
import com.example.ui.MenuHolder;
import com.example.ui.menu.IMenu;
import com.example.ui.menu.MenuType;
import com.example.model.entity.user.User;
import com.example.model.entity.user.UserType;
import com.example.model.message.MessageType;
import com.example.ui.menu.model.RightsTableModel;

import javax.swing.*;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class RightsMenu implements IMenu, ActionListener {

    private MenuType type = MenuType.RIGHTS;
    private MenuHolder menuHolder;
    private JPanel panel;
    private GroupLayout layout;

    private JLabel title;

    private JScrollPane scrollPane;
    private JTable table;
    private RightsTableModel rightsTableModel;

    private JButton accept, back, backward, forward;
    private int pageNumber = 0;

    public RightsMenu(MenuHolder menuHolder, JPanel panel, GroupLayout layout) {
        this.menuHolder = menuHolder;
        this.panel = panel;
        this.layout = layout;

        Font titleFont = new Font(Font.SERIF, Font.BOLD, 18);
        title = new JLabel("<html>СПИСОК ПОЛЬЗОВАТЕЛЕЙ<hr></html>");
        title.setFont(titleFont);
        title.setHorizontalAlignment(SwingConstants.CENTER);

        rightsTableModel = new RightsTableModel();
        table = new JTable(rightsTableModel);
        scrollPane = new JScrollPane(table);

        TableColumn purchasedColumn = table.getColumnModel().getColumn(6);
        JComboBox<UserType> purchasedClasses = new JComboBox<>();
        purchasedClasses.addItem(UserType.EMPLOYEE);
        purchasedClasses.addItem(UserType.USER);
        purchasedColumn.setCellEditor(new DefaultCellEditor(purchasedClasses));

        forward = new JButton(">>");
        forward.setName("FORWARD");
        forward.addActionListener(this);

        backward = new JButton("<<");
        backward.setName("BACKWARD");
        backward.setEnabled(false);
        backward.addActionListener(this);

        accept = new JButton("СОХРАНИТЬ");
        accept.setName("ACCEPT");
        accept.addActionListener(this);

        back = new JButton("НАЗАД");
        back.setName("BACK");
        back.addActionListener(this);
    }

    @Override
    public MenuType getType() {
        return type;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource().equals(forward)) {
            pageNumber++;

            if(!backward.isEnabled()) {
                backward.setEnabled(true);
            }
            List<User> userList = readUserList();
            if(userList.isEmpty()){
                forward.setEnabled(false);
                pageNumber--;
                return;
            }

            rightsTableModel.setData(readUserList());
        }

        if(e.getSource().equals(backward)) {
            if(pageNumber > 0) {
                pageNumber--;
                if(pageNumber == 0) backward.setEnabled(false);
            } else {
                backward.setEnabled(false);
            }

            if(!forward.isEnabled()) {
                forward.setEnabled(true);
            }

            List<User> userList = readUserList();
            if(userList.isEmpty()){
                forward.setEnabled(false);
            }
            rightsTableModel.setData(readUserList());
        }

        if(e.getSource().equals(accept)) {
            if(!rightsTableModel.getChangedUserList().isEmpty()) {
                Message<Sendable> message = menuHolder.getUserService()
                        .updateAll(
                                menuHolder.getUser().getId(),
                                rightsTableModel.getChangedUserList()
                        );
                if(message.getType().equals(MessageType.ERROR)) {
                    JOptionPane.showMessageDialog(panel, "Не удалось обновить некоторых пользователей...");
                }else {
                    JOptionPane.showMessageDialog(panel, "Пользователи успешно обновлены");
                    rightsTableModel.getChangedUserList().clear();
                }
            }else {
                JOptionPane.showMessageDialog(panel, "Нечего обновлять");
            }
        }

        if(e.getSource().equals(back)) {
            menuHolder.getMenu(MenuType.MAIN).show();
        }
    }

    private List<User> readUserList() {
        Message<Sendable> message = menuHolder.getUserService().getAll(menuHolder.getUser().getId(), pageNumber, 5);
        if(message.getType().equals(MessageType.ERROR)) {
            JOptionPane.showMessageDialog(panel, "Неудалось выгрузить список пользователей, обратитесь к разработчику...");
            menuHolder.getMenu(MenuType.MAIN).show();
        }
        return  message.getBody().stream().map(u->(User)u).toList();
    }

    @Override
    public void show() {
        panel.removeAll();

        rightsTableModel.setData(readUserList());
        RowSorter<RightsTableModel> sorter = new TableRowSorter<RightsTableModel>(
                rightsTableModel);
        table.setRowSorter(sorter);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                .addComponent(title)
                .addComponent(scrollPane)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(backward)
                        .addComponent(forward)
                )
                .addGroup(layout.createSequentialGroup()
                    .addComponent(accept)
                    .addComponent(back)
                )
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(title)
                .addComponent(scrollPane)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(backward)
                        .addComponent(forward)
                )
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(accept)
                    .addComponent(back)
                )
        );


        layout.linkSize(SwingConstants.VERTICAL, accept, back);
        layout.linkSize(SwingConstants.HORIZONTAL, accept,  back);
    }
}
