package org.fnt.ui.menu.rights;

import org.fnt.model.entity.user.User;
import org.fnt.model.entity.user.UserType;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class RightsTableModel extends AbstractTableModel {

    private String[] columnTitle = {"ID", "Имя", "Отчество", "Фамилия",
            "Дата рождения", "Номер телефона", "Тип пользователя", "Удален"};
    private List<User> data;
    private List<User> changedUserList;

    public RightsTableModel() {
        changedUserList = new ArrayList<>();
    }

    public RightsTableModel(List<User> data) {
        this.data = data;
        changedUserList = new ArrayList<>();
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columnTitle.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object result = null;
        User user = data.get(rowIndex);
        if(columnIndex==0) result = (Object) user.getId();
        if(columnIndex==1) result = (Object) user.getFirstName();
        if(columnIndex==2) result = (Object) user.getMiddleName();
        if(columnIndex==3) result = (Object) user.getLastName();
        if(columnIndex==4) result = (Object) user.getBirthDate();
        if(columnIndex==5) result = (Object) user.getPhoneNumber();
        if(columnIndex==6) result = (Object) user.getType();
        if(columnIndex==7) result = (Object) user.isDeleted();
        return result;
    }

    public String getColumnName(int columnIndex) {
        return columnTitle[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return getValueAt(0, columnIndex).getClass();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if(columnIndex == 6 || columnIndex == 7) {
            return true;
        }
        return false;
    }

    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        User user = data.get(rowIndex);

        if(columnIndex == 6) {
            user.setType((UserType) value);
            updateTable();
        }
        if(columnIndex == 7){
            user.setDeleted((boolean) value);
            updateTable();
        }
        changedUserList.add(user);
    }

    public void deleteRow(int rowIndex) {
        updateTable();
    }

    public void updateTable() {
        fireTableDataChanged();
    }

    public void setData(List<User> data) {
        this.data = data;
    }

    public List<User> getChangedUserList() {
        return changedUserList;
    }
}
