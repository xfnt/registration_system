package org.fnt.dao;

import java.util.List;

public interface DAO<T> {

    T getById(String id);
    List<T> getAll();
    boolean insert(T object);
    boolean update(T object);
    boolean delete(T object);
    void close();
}
