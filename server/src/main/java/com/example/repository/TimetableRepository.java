package com.example.repository;

import com.example.dao.TimetableDAO;
import com.example.connection.ConnectionFactory;

public class TimetableRepository extends TimetableDAO {

    public TimetableRepository(ConnectionFactory connectionFactory) {
        super(connectionFactory);
    }
}
