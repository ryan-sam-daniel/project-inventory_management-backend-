package com.twozo.inventorymanagementsystem.service;

import com.twozo.inventorymanagementsystem.model.Product;
import com.twozo.inventorymanagementsystem.model.PurchaseItem;
import com.twozo.inventorymanagementsystem.model.SaleItem;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public interface ProductService {
    Collection<Product> getAllProducts() ;
    Product getProduct(int id);
    int add(Product product) ;
    boolean remove(int id);
    boolean update(Product product);
    boolean updateStock(Collection<PurchaseItem> cart);
    boolean removeStock(Collection<SaleItem> cart) ;
    double summateProductSubTotal(Product product, String mode);
    double summateProductTaxAmount(double subTotal, Product product);
    double summateProductFinalAmount(double amount, Product product);
}
