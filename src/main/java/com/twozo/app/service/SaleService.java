package com.twozo.app.service;

import com.twozo.app.model.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class SaleService implements TradeService<Product, Sale , SaleItem>{
    private final CartSummationService<SaleItem> cartCalculationService;
    private final ProductServiceManager productService;
    private Collection<SaleItem> cart;
    
    public SaleService(final CartSummationService<SaleItem> cartCalculationService, final ProductServiceManager productService){
        this.cartCalculationService = cartCalculationService;
        this.productService = productService;
        this.cart = new ArrayList<>();
    }

    @Override
    public String add(final TradeOperationDTO tradeOperationDTO){
        boolean found = false;
        final Product product = productService.getProduct(tradeOperationDTO.getId());
        product.setStockQuantity(tradeOperationDTO.getQuantity());

        for (final SaleItem item : cart) {

            if (item.getProductId() == product.getId()) {
                updateCartItem(item, product, "ADD");
                found = true;
                break;
            }

        }

        if (!found) {
            final SaleItem item = summate(product);
            cart.add(item);
            return "Product added to cart";
        }

        return "Product added to cart";
    }

    @Override
    public String remove(final TradeOperationDTO tradeOperationDTO){
        SaleItem toRemove = null;
        final Product product = productService.getProduct(tradeOperationDTO.getId());
        product.setStockQuantity(tradeOperationDTO.getQuantity());

        for (final SaleItem item : cart) {

            if (item.getProductId() == product.getId()) {

                if (item.getQuantity() > product.getStockQuantity()) {
                    updateCartItem(item, product, "REMOVE");
                } else {
                    toRemove = item;
                }

                break;
            }
        }

        if (toRemove != null) {
            cart.remove(toRemove);
            return "Product removed from cart";
        }

        return  "Product removed from cart";
    }

    @Override
    public String createNew(){
        cart = new ArrayList<>();
        return "New Cart is created";
    }

    public Collection<SaleItem> getCart(){
        return cart;
    }


    @Override
    public SaleItem summate(final Product product) {
        final double subTotal = productService.summateProductSubTotal(product, "sale");
        final double taxAmount = productService.summateProductTaxAmount(subTotal, product);
        final double finalAmount = productService.summateProductFinalAmount(subTotal + taxAmount, product);
        return new SaleItem(
                product.getId(),
                product.getStockQuantity(),
                product.getSellingPrice(),
                subTotal,
                taxAmount,
                finalAmount
        );
    }

    @Override
    public void updateCartItem(final SaleItem item, final Product product, final String mode) {
        final int quantityChange = product.getStockQuantity();
        final double subTotal = productService.summateProductSubTotal(product, "sale");
        final double tax = productService.summateProductTaxAmount(subTotal, product);
        final double finalAmount = productService.summateProductFinalAmount(subTotal + tax, product);

        if ("ADD".equalsIgnoreCase(mode)) {
            item.setQuantity(item.getQuantity() + quantityChange);
            item.setSubTotal(item.getSubTotal() + subTotal);
            item.setTaxAmount(item.getTaxAmount() + tax);
            item.setFinalDiscountedAmount(item.getFinalDiscountedAmount() + finalAmount);
        } else if ("REMOVE".equalsIgnoreCase(mode)) {
            item.setQuantity(item.getQuantity() - quantityChange);
            item.setSubTotal(item.getSubTotal() - subTotal);
            item.setTaxAmount(item.getTaxAmount() - tax);
            item.setFinalDiscountedAmount(item.getFinalDiscountedAmount() - finalAmount);
        }

    }

    @Override
    public double getSubtotal() {
        return cartCalculationService.getSubTotal(cart);
    }
    @Override
    public double getTax(){
        return cartCalculationService.getTaxAmount(cart);
    }

    @Override
    public double getFinalAmount(final FinalAmountRequest finalAmountRequest){
        return cartCalculationService.getFinalAmount(finalAmountRequest.getAmount(),finalAmountRequest.getRate());
    }

}
