package org.fnt.repository;

import org.fnt.connection.ConnectionFactory;
import org.fnt.dao.TimetableDAO;

public class TimetableRepository extends TimetableDAO {

    public TimetableRepository(ConnectionFactory connectionFactory) {
        super(connectionFactory);
    }
}
