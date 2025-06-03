package com.twozo.inventorymanagementsystem.dao;

import com.twozo.inventorymanagementsystem.model.PurchaseItem;
import com.twozo.inventorymanagementsystem.model.SaleItem;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ProductDao<T> extends WritableDao<T> {

    Collection<T> getAll(int pageNumber, int pageSize);

    T get(int id);

    int store(T product);

    boolean updateStock(Collection<PurchaseItem> cart);

    boolean removeStock(Collection<SaleItem> cart);

    boolean update(T product, List<String> queryList);
}
