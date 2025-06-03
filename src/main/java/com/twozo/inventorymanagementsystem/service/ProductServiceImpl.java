package com.twozo.inventorymanagementsystem.service;

import com.twozo.inventorymanagementsystem.dao.ProductDao;
import com.twozo.inventorymanagementsystem.model.Product;
import com.twozo.inventorymanagementsystem.model.PurchaseItem;
import com.twozo.inventorymanagementsystem.model.SaleItem;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductDao<Product> productDataHandler;

    public ProductServiceImpl(final ProductDao<Product> productDataHandler) {
        this.productDataHandler = productDataHandler;
    }

    @Override
    public Collection<Product> getAllProducts(final int pageNumber, final int pageSize) {
        return productDataHandler.getAll(pageNumber, pageSize);
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

    public boolean update(final Product product) {
        final Product existingProduct = productDataHandler.get(product.getId());
        final List<String> query = new ArrayList<>();

        if (product.getName() != null && !product.getName().isEmpty() && !product.getName().equals(existingProduct.getName())) {
            existingProduct.setName(product.getName());
            query.add("\"name\" = '" + product.getName() + "'");
        }

        if (product.getPurchasePrice() != existingProduct.getPurchasePrice() && product.getPurchasePrice() != 0) {
            existingProduct.setPurchasePrice(product.getPurchasePrice());
            query.add("\"purchase_price\" = " + product.getPurchasePrice());
        }

        if (product.getMrp() != existingProduct.getMrp() && product.getMrp() != 0) {
            existingProduct.setMrp(product.getMrp());
            query.add("\"mrp\" = " + product.getMrp());
        }

        if (product.getDiscountPercentage() != existingProduct.getDiscountPercentage() && product.getDiscountPercentage() != 0) {
            existingProduct.setDiscountPercentage(product.getDiscountPercentage());
            query.add("\"discount_rate\" = " + product.getDiscountPercentage());
        }

        if (product.getSellingPrice() != existingProduct.getSellingPrice() && product.getSellingPrice() != 0) {
            existingProduct.setSellingPrice(product.getSellingPrice());
            query.add("\"selling_price\" = " + product.getSellingPrice());
        }

        if (product.getTaxPercentage() != existingProduct.getTaxPercentage() && product.getTaxPercentage() != 0) {
            existingProduct.setTaxPercentage(product.getTaxPercentage());
            query.add("\"tax_rate\" = " + product.getTaxPercentage());
        }

        if (product.getStockQuantity() != existingProduct.getStockQuantity() && product.getStockQuantity() != 0) {
            existingProduct.setStockQuantity(product.getStockQuantity());
            query.add("\"stock_quantity\" = " + product.getStockQuantity());
        }

        if (query.isEmpty()) {
            return false;
        }

        return productDataHandler.update(existingProduct, query);
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
            } else if ("purchase".equalsIgnoreCase(mode)) {
                return product.getPurchasePrice() * product.getStockQuantity();
            } else {
                throw new IllegalArgumentException("Invalid mode. Mode must be 'sale' or 'purchase'.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error accessing price method");
        }

    }

    @Override
    public double summateProductTaxAmount(final double subTotal, final Product product) {
        return (subTotal * product.getTaxPercentage()) / 100;
    }

    @Override
    public double summateProductFinalAmount(final double amount, final Product product) {
        return (amount - ((amount * product.getDiscountPercentage()) / 100));
    }

}
