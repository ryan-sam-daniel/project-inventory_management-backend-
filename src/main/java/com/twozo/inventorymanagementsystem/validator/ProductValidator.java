package com.twozo.inventorymanagementsystem.validator;

import com.twozo.inventorymanagementsystem.model.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class ProductValidator implements GeneralValidator<Product> {

    @Override
    public Collection<String> validate(final Product product) {
        final Collection<String> validationErrors = new ArrayList<>();

        if (!validateName(product.getName())) {
            validationErrors.add("Product name is required. ");
        }

        if (!validateAmount(product.getPurchasePrice())) {
            validationErrors.add("Purchase price must be greater than 0. ");
        }

        if (!validateAmount(product.getSellingPrice())) {
            validationErrors.add("Selling price must be greater than 0. ");
        }

        if (!validateMrp(product.getMrp(), product.getSellingPrice())) {
            validationErrors.add("MRP should not be less than selling price. ");
        }

        if (!validateTaxRate(product.getTaxPercentage())) {
            validationErrors.add("Tax percentage should not be negative . ");
        }

        if (!validateDiscountRate(product.getDiscountPercentage())) {
            validationErrors.add("Discount percentage must be between 0 and 100. ");
        }

        if (!validateStockQuantity(product.getStockQuantity())) {
            validationErrors.add("Stock quantity must be greater than 0. ");
        }

        return validationErrors;
    }

    private boolean validateStockQuantity(final int quantity) {
        return quantity > 0;
    }

    private boolean validateDiscountRate(final double rate) {
        return (rate >= 0 && rate <= 100);
    }

    private boolean validateTaxRate(final int rate) {
        return (rate >= 0 && rate <= 100);
    }

    private boolean validateAmount(final double amount) {
        return amount >= 0;
    }

    private boolean validateMrp(final double mrp, final double sellingPrice) {
        return mrp >= sellingPrice;
    }

    private boolean validateName(final String name) {
        return name == null;
    }

    @Override
    public String validateId(final int id) {

        if (id <= 0) {
            return "Invalid id ! ID should not be negative";
        }

        return null;
    }

    @Override
    public String validatePhoneNo(String phoneNo) {
        throw new UnsupportedOperationException("This operation is not supported ");
    }

}
