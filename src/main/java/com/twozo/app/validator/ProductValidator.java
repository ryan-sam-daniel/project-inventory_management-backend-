package com.twozo.app.validator;

import com.twozo.app.model.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class ProductValidator {
    public Collection<String> validateAdding(final Product product){
        final Collection<String> validationErrors = new ArrayList<>();

        if (product.getName() == null || product.getName().trim().isEmpty()) {
            validationErrors.add("Product name is required. ");
        }

        if (product.getPurchasePrice() <= 0) {
            validationErrors.add("Purchase price must be greater than 0. ");
        }

        if (product.getSellingPrice() <= 0) {
            validationErrors.add("Selling price must be greater than 0. ");
        }

        if (product.getMrp() < product.getSellingPrice()) {
            validationErrors.add("MRP should not be less than selling price. ");
        }

        if (product.getTaxPercentage() < 0) {
            validationErrors.add("Tax percentage should not be negative . ");
        }

        if (product.getDiscountPercentage() < 0 || product.getDiscountPercentage() > 100) {
            validationErrors.add("Discount percentage must be between 0 and 100. ");
        }

        if (product.getStockQuantity() <= 0) {
            validationErrors.add("Stock quantity must be greater than 0. ");
        }

        return validationErrors;
    }
}
