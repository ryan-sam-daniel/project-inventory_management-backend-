package com.twozo.app.dao;

import com.twozo.app.model.Product;
import com.twozo.app.model.PurchaseItem;
import com.twozo.app.model.SaleItem;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ProductDbManager extends CRUD<Product>{
    Collection<Product> getAll() ;
    Product get(int id);
    int store(Product product) ;
    boolean updateStock(Collection<PurchaseItem> cart);
    boolean removeStock(Collection<SaleItem> cart);
}
