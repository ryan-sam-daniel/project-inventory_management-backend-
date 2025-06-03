package com.twozo.inventorymanagementsystem.dao;

import java.sql.Connection;

public interface WritableDao<T> {

    int store(T t, Connection connection);

    boolean remove(int id);
}
