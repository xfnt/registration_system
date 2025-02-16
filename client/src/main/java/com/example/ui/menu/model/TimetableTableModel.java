package com.example.ui.menu.model;

import com.example.model.entity.Timetable;
import com.example.util.DateFormatUtils;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TimetableTableModel extends AbstractTableModel {

    private String[] columnTitle = {"ФИО СОТРУДНИКА", "ФИО КЛИЕНТА", "ДАТА И ВРЕМЯ ПРИЕМА"};
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
        if(columnIndex==0) result = (Object) timetable.getEmployeeId();
        if(columnIndex==1) result = (Object) timetable.getUserId();
        if(columnIndex==2) result = (Object) DateFormatUtils.parseDateTimeToString(timetable.getTime());
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
