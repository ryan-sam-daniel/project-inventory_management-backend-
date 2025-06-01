package com.twozo.inventorymanagementsystem.validator;

import java.util.Collection;

public interface GeneralValidator<T> {
    Collection<String> validate(T t);
    String validateId(int id);
    String validatePhoneNo (final String phoneNo);
}
