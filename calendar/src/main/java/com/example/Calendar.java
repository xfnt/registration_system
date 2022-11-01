package com.example;

import com.example.listener.CalendarListener;
import com.example.model.MonthModel;

import javax.swing.*;
import java.time.*;
import java.util.Arrays;

public class Calendar extends JPanel implements ICalendar {

    private JSpinner yearSpinner;
    private JSpinner monthSpinner;
    private JSpinner daySpinner;
    private JSpinner hourSpinner;
    private JSpinner minuteSpinner;

    private int startYear = 1800;
    private int lastYear = 3000;

    private JLabel year, month, day, hour, minute;

    private LocalDateTime localDateTime;

    private GroupLayout layout;

    public Calendar() {
        layout = new GroupLayout(this);
        this.setLayout(layout);

        localDateTime = LocalDateTime.now();
        localDateTime = localDateTime.withHour(8).withMinute(0);
        CalendarListener calendarListener = new CalendarListener(this);

        year = new JLabel("ГОД: ");
        month = new JLabel("МЕСЯЦ: ");
        day = new JLabel("ДЕНЬ: ");
        hour = new JLabel("ЧАС: ");
        minute = new JLabel("МИНУТЫ: ");

        yearSpinner = new JSpinner(new SpinnerNumberModel(localDateTime.getYear(), startYear, lastYear, 1));
        yearSpinner.setName("year");
        JSpinner.NumberEditor numberEditorYear = new JSpinner.NumberEditor(yearSpinner, "####");
        numberEditorYear.getTextField().setEditable(false);
        yearSpinner.setEditor(numberEditorYear);
        yearSpinner.addChangeListener(calendarListener);

        monthSpinner = new JSpinner(new MonthModel(Arrays.stream(Month.values()).toList(), localDateTime.getMonth()));
        monthSpinner.setName("month");
        monthSpinner.addChangeListener(calendarListener);

        daySpinner = new JSpinner(
                new SpinnerNumberModel(localDateTime.getDayOfMonth(),
                        1,
                        YearMonth.of(localDateTime.getYear(), localDateTime.getMonth()).lengthOfMonth(),
                        1));
        daySpinner.setName("day");
        JSpinner.NumberEditor numberEditorDay = new JSpinner.NumberEditor(daySpinner, "##00");
        numberEditorDay.getTextField().setEditable(false);
        daySpinner.setEditor(numberEditorDay);
        daySpinner.addChangeListener(calendarListener);

        hourSpinner = new JSpinner(new SpinnerNumberModel(8, 8, 19, 1));
        hourSpinner.setName("hour");
        JSpinner.NumberEditor numberEditorHour = new JSpinner.NumberEditor(hourSpinner, "##00");
        numberEditorHour.getTextField().setEditable(false);
        hourSpinner.setEditor(numberEditorHour);
        hourSpinner.addChangeListener(calendarListener);

        minuteSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 55, 5));
        minuteSpinner.setName("minute");
        JSpinner.NumberEditor numberEditorMinute = new JSpinner.NumberEditor(minuteSpinner, "##00");
        numberEditorMinute.getTextField().setEditable(false);
        minuteSpinner.setEditor(numberEditorMinute);
        minuteSpinner.addChangeListener(calendarListener);

        setUpLayout();
    }

    public JSpinner getYearSpinner() {
        return yearSpinner;
    }

    public JSpinner getMonthSpinner() {
        return monthSpinner;
    }

    public JSpinner getDaySpinner() {
        return daySpinner;
    }

    public JSpinner getHourSpinner() {
        return hourSpinner;
    }

    public JSpinner getMinuteSpinner() {
        return minuteSpinner;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    public void setLastYear(int lastYear) {
        this.lastYear = lastYear;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    @Override
    public LocalDateTime getDateTime() {
        localDateTime = localDateTime.withSecond(0).withNano(0);
        return localDateTime;
    }

    private void setUpLayout() {
        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addComponent(year)
                        .addComponent(yearSpinner)
                        .addGroup(
                                layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(month)
                                                .addComponent(monthSpinner)
                                        )
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(hour)
                                                .addComponent(hourSpinner)
                                                .addComponent(minute)
                                                .addComponent(minuteSpinner)
                                        )
                        )
                        .addComponent(day)
                        .addComponent(daySpinner)
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGroup(
                                layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(year)
                                        .addComponent(yearSpinner)
                                        .addComponent(month)
                                        .addComponent(monthSpinner)
                                        .addComponent(day)
                                        .addComponent(daySpinner)
                        )
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(hour)
                                .addComponent(hourSpinner)
                                .addComponent(minute)
                                .addComponent(minuteSpinner)
                        )
        );

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.linkSize(SwingConstants.VERTICAL, year, yearSpinner, month, monthSpinner, day, daySpinner, hour, hourSpinner, minute, minuteSpinner);
    }
}
