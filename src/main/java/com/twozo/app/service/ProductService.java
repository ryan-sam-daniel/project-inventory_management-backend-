package com.twozo.app.service;

import com.twozo.app.dao.ProductDbManager;
import com.twozo.app.model.Product;
import com.twozo.app.model.PurchaseItem;
import com.twozo.app.model.SaleItem;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ProductService implements ProductServiceManager{
    private final ProductDbManager productDataHandler;

    public ProductService(final ProductDbManager productDataHandler){
        this.productDataHandler = productDataHandler;
    }

    @Override
    public Collection<Product> getAllProducts() {
        return productDataHandler.getAll();
    }

    @Override
    public Product getProduct(final int id) {
        return productDataHandler.get(id);
    }


    @Override
    public int add(final Product product) {
        final Product product1 = new Product(
                product.getName(),
                product.getPurchasePrice(),
                product.getMrp(),
                product.getTaxPercentage(),
                product.getDiscountPercentage(),
                product.getSellingPrice(),
                product.getStockQuantity()
        );
        return productDataHandler.store(product1);
    }

    @Override
    public boolean remove(final int id) {
        return productDataHandler.remove(id);
    }

    @Override
    public boolean updateStock(final Collection<PurchaseItem> cart) {
        return productDataHandler.updateStock(cart);
    }

    @Override
    public boolean removeStock(final Collection<SaleItem> cart) {
        return productDataHandler.removeStock(cart);
    }

    @Override
    public double summateProductSubTotal(final Product product, final String mode) {

        try {

            if ("sale".equalsIgnoreCase(mode)) {
                return product.getSellingPrice() * product.getStockQuantity();
            }
            else if ("purchase".equalsIgnoreCase(mode)) {
                return product.getPurchasePrice() * product.getStockQuantity();
            }
            else {
                throw new IllegalArgumentException("Invalid mode. Mode must be 'sale' or 'purchase'.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error accessing price method");
        }

    }

    @Override
    public double summateProductTaxAmount(final double subTotal, final Product product){
        return (subTotal * product.getTaxPercentage())/100;
    }

    @Override
    public double summateProductFinalAmount(final double amount, final Product product){
        return (amount - ((amount*product.getDiscountPercentage())/100));
    }

}
