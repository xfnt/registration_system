package com.example.service;


import com.example.model.UserType;
import com.example.connection.ConnectionFactory;
import com.example.model.entity.Timetable;
import com.example.repository.TimetableRepository;

import java.util.ArrayList;
import java.util.List;

public class TimetableService {
    private TimetableRepository repository;

    public TimetableService(ConnectionFactory connectionFactory) {
        repository = new TimetableRepository(connectionFactory);
    }

    public List<Timetable> addTime(List<Timetable> timetableList) {
        List<Timetable> existTimeTableList = new ArrayList<>();
        for(Timetable tt : timetableList) {
            if(repository.getTimetable(tt.getEmployeeId(), tt.getTime()) == null || repository.getTimetable(tt.getEmployeeId(), tt.getTime()).getEmployeeId()==null) {
                repository.insert(tt);
            }else {
                existTimeTableList.add(tt);
            }
        }
        return existTimeTableList;
    }

    public List<Timetable> getFreeTime(String userId) {
        return repository.getFree(userId);
    }

    public List<Timetable> getAllTime(String userId, UserType type) {
        return repository.getAll(userId, type);
    }

    public boolean appoint(Timetable timetable) {
        return repository.update(timetable);
    }
}
