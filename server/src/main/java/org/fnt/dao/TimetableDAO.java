package org.fnt.dao;


import org.fnt.connection.ConnectionFactory;
import org.fnt.model.entity.Timetable;
import org.fnt.model.entity.user.UserType;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TimetableDAO implements DAO<Timetable> {
    private ConnectionFactory connectionFactory;
    private Connection connection;

    public TimetableDAO() {
        this.connectionFactory = new ConnectionFactory();
    }


    @Override
    public Timetable getById(String id) {
        return null;
    }

    @Override
    public List<Timetable> getAll() {
        return null;
    }

    public List<Timetable> getAll(String id, UserType type) {
        connection = connectionFactory.getConnection();
        String sql = null;
        if(type==UserType.USER) {
            sql = """
                SELECT * FROM timetable t
                WHERE t.user=?;
                """;
        }else {
            sql = """
                SELECT * FROM timetable t
                WHERE t.employee=?;
                """;
        }
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Timetable> timetableList= new ArrayList<>();

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,id);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                timetableList.add(new Timetable(
                        resultSet.getLong("id"),
                        resultSet.getString("employee"),
                        resultSet.getString("user"),
                        resultSet.getTimestamp("time").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            close();
            timetableList=null;
            throw new RuntimeException(e);
        }

        close();
        return timetableList;
    }

    public Timetable getTimetable(String employeeId, LocalDateTime time) {
        connection = connectionFactory.getConnection();
        String sql = """
                SELECT * FROM timetable t
                WHERE t.employee=? AND t.time=?;
                """;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Timetable timetable= null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,employeeId);
            preparedStatement.setTimestamp(2, Timestamp.valueOf(time));
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                timetable = new Timetable(
                        resultSet.getLong("id"),
                        resultSet.getString("employee"),
                        resultSet.getString("user"),
                        resultSet.getTimestamp("time").toLocalDateTime()
                        );
            }
        } catch (SQLException e) {
            close();
            timetable = null;
        }

        close();
        return timetable;
    }

    public List<Timetable> getFree(String employeeId) {
        connection = connectionFactory.getConnection();
        String sql = """
                SELECT * FROM timetable t
                WHERE t.employee=? AND t.user IS NULL AND t.time>now();
                """;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Timetable> timetableList= new ArrayList<>();

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,employeeId);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                timetableList.add(new Timetable(
                        resultSet.getLong("id"),
                        resultSet.getString("employee"),
                        resultSet.getString("user"),
                        resultSet.getTimestamp("time").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            close();
            timetableList=null;
            throw new RuntimeException(e);
        }

        close();
        return timetableList;
    }

    @Override
    public boolean insert(Timetable object) {
        connection = connectionFactory.getConnection();
        String sql = """
                INSERT INTO timetable (employee,time)
                    VALUES (?,?);
                """;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, object.getEmployeeId());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(object.getTime()));
            preparedStatement.execute();

        } catch (SQLException e) {
            close();
            return  false;
        }
        close();
        return true;
    }

    @Override
    public boolean update(Timetable object) {
        connection = connectionFactory.getConnection();
        String sql = """
                 UPDATE timetable
                	SET "user"=?
                	WHERE id=? AND employee=? AND "user" IS NULL AND "time"=?;
                """;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, object.getUserId());
            preparedStatement.setLong(2, object.getId());
            preparedStatement.setString(3, object.getEmployeeId());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(object.getTime()));
            preparedStatement.execute();

        } catch (SQLException e) {
            close();
            return  false;
        }
        close();
        return true;
    }

    @Override
    public boolean delete(Timetable object) {
        return false;
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
