package com.twozo.inventorymanagementsystem.dao;

public interface ReadableDao<T> {

    T get(String id);
}
