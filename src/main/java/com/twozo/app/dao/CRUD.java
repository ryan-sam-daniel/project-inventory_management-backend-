package com.twozo.app.dao;

import java.sql.Connection;

public interface CRUD<T> {
    int store(T t, Connection connection);
    boolean remove(int id);
}
