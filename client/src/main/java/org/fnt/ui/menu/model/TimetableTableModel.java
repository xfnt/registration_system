package org.fnt.ui.menu.model;

import org.fnt.model.entity.Timetable;
import org.fnt.util.DateFormatUtils;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TimetableTableModel extends AbstractTableModel {

    private String[] columnTitle = {"id", "id сотрудника", "id пользователя", "дата приема"};
    private List<Timetable> data;

    public TimetableTableModel() {
    }

    public TimetableTableModel(List<Timetable> data) {
        this.data = data;
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
        Timetable timetable = data.get(rowIndex);
        if(columnIndex==0) result = (Object) timetable.getId();
        if(columnIndex==1) result = (Object) timetable.getEmployeeId();
        if(columnIndex==2) result = (Object) timetable.getUserId();
        if(columnIndex==3) result = (Object) DateFormatUtils.parseDateTimeToString(timetable.getTime());
        return result;
    }

    public String getColumnName(int columnIndex) {
        return columnTitle[columnIndex];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public void setValueAt(Object value, int rowIndex, int columnIndex) {
    }

    public void deleteRow(int rowIndex) {
        updateTable();
    }

    public void updateTable() {
        fireTableDataChanged();
    }

    public void setData(List<Timetable> data) {
        this.data = data;
    }
}
