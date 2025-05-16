package com.twozo.app.dao;

import java.sql.Connection;
import org.springframework.stereotype.Repository;

@Repository
public interface StakeHolderDbHandler<T>  extends CRUD<T>{
   T checkExistence(String phoneNo);
   int store(T t);
}
