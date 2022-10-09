package org.fnt.listener;

import org.fnt.Calendar;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.YearMonth;

public class CalendarListener implements ChangeListener {

    private Calendar calendar;
    private LocalDateTime localDateTime;

    private YearMonth yearMonth;

    public CalendarListener(Calendar calendar) {
        this.calendar = calendar;
        localDateTime = calendar.getLocalDateTime();
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        updateYearMonth();

        if(e.getSource().equals(calendar.getYearSpinner())) {
            localDateTime = localDateTime.withYear((Integer) calendar.getYearSpinner().getValue());
            updateYearMonth();

            if(Month.FEBRUARY.equals(Month.valueOf(calendar.getMonthSpinner().getValue().toString()))) {
                if(Month.FEBRUARY.equals(Month.valueOf(calendar.getMonthSpinner().getValue().toString())) &&
                        (Integer) calendar.getDaySpinner().getValue() > yearMonth.lengthOfMonth()) {
                    changeDateIfLessThanSpinnerDate();
                }else {
                    changeDate();
                }
            }
        }

        if(e.getSource().equals(calendar.getMonthSpinner())) {
            localDateTime = localDateTime.withMonth(Month.valueOf(calendar.getMonthSpinner().getValue().toString()).getValue());
            updateYearMonth();

            if((Integer) calendar.getDaySpinner().getValue() > yearMonth.lengthOfMonth()) {
                 changeDateIfLessThanSpinnerDate();
            }else {
                changeDate();
            }
        }

        if(e.getSource().equals(calendar.getDaySpinner())) {
            localDateTime = localDateTime.withDayOfMonth((Integer) calendar.getDaySpinner().getValue());
            updateYearMonth();
        }

        if(e.getSource().equals(calendar.getHourSpinner())) {
            localDateTime = localDateTime.withHour((Integer) calendar.getHourSpinner().getValue());
        }

        if(e.getSource().equals(calendar.getMinuteSpinner())) {
            localDateTime = localDateTime.withMinute((Integer) calendar.getMinuteSpinner().getValue());
        }

        calendar.setLocalDateTime(localDateTime);
    }

    private void updateYearMonth() {
        yearMonth = YearMonth.of(localDateTime.getYear(), localDateTime.getMonth());
    }

    private void changeDateIfLessThanSpinnerDate() {
        calendar.getDaySpinner().setValue(yearMonth.lengthOfMonth());
        calendar.getDaySpinner().setModel(new SpinnerNumberModel(
                yearMonth.lengthOfMonth(),
                1,
                yearMonth.lengthOfMonth(),
                1)
        );
    }

    private void changeDate() {
        calendar.getDaySpinner().setModel(new SpinnerNumberModel(
                localDateTime.getDayOfMonth(),
                1,
                yearMonth.lengthOfMonth(),
                1));
    }
}
