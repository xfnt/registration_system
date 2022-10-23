package com.example.model;

import javax.swing.*;
import java.io.Serializable;
import java.util.List;

public class MonthModel extends AbstractSpinnerModel implements Serializable {
    private List<?> list;
    private int index;

    public MonthModel(List<?> values, Object value) {
        if (values == null || values.size() == 0) {
            throw new IllegalArgumentException("SpinnerListModel(List) expects non-null non-empty List");
        }
        this.list = values;
        this.index = values.indexOf(value);
    }

    @Override
    public Object getValue() {
        return list.get(index);
    }

    @Override
    public void setValue(Object value) {
        int index = list.indexOf(value);
        if (index == -1) {
            throw new IllegalArgumentException("invalid sequence element");
        }
        else if (index != this.index) {
            this.index = index;
            fireStateChanged();
        }
    }

    @Override
    public Object getNextValue() {
        return (index >= (list.size() - 1)) ? null : list.get(index + 1);
    }

    @Override
    public Object getPreviousValue() {
        return (index <= 0) ? null : list.get(index - 1);
    }
}