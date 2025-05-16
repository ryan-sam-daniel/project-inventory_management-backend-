package com.twozo.app.service;

import com.twozo.app.model.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class PurchaseService implements TradeService<Product, Purchase, PurchaseItem>{
    private final CartSummationService<PurchaseItem> cartCalculationService;
    private final ProductServiceManager productService;
    private Collection<PurchaseItem> cart ;

    public PurchaseService(final CartSummationService<PurchaseItem> cartCalculationService, final ProductServiceManager productService){
        this.cartCalculationService = cartCalculationService;
        this.productService = productService;
        this.cart = new ArrayList<>();
    }

    @Override
    public String add(final TradeOperationDTO tradeOperationDTO){
        boolean found = false;
        final Product product = productService.getProduct(tradeOperationDTO.getId());
        product.setStockQuantity(tradeOperationDTO.getQuantity());

        for (final PurchaseItem item : cart) {

            if (item.getProductId() == product.getId()) {
                updateCartItem(item, product, "ADD");
                found = true;
                break;
            }

        }

        if (!found) {
            final PurchaseItem item = summate(product);
            final boolean added = cart.add(item);

            if (added) {
                return "Product added to cart";
            } else {
                return "Product not added";
            }

        }

        return "Product added to cart";
    }

    @Override
    public String remove(final TradeOperationDTO tradeOperationDTO){
        PurchaseItem toRemove = null;
        final Product product = productService.getProduct(tradeOperationDTO.getId());
        product.setStockQuantity(tradeOperationDTO.getQuantity());

        for (final PurchaseItem item : cart) {

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
            final boolean removed = cart.remove(toRemove);
            return (removed?"Product removed from cart":"Product not removed");
        }

        return  "Product removed from cart";
    }

    @Override
    public String createNew(){
        cart = new ArrayList<>();
        return "New Cart is created";
    }

    public Collection<PurchaseItem> getCart(){
        return cart;
    }


    @Override
    public PurchaseItem summate(final Product product) {
        final double subTotal = productService.summateProductSubTotal(product,"purchase");
        final double taxAmount = productService.summateProductTaxAmount(subTotal, product);
        final double finalAmount = productService.summateProductFinalAmount(subTotal+taxAmount, product);
        return new PurchaseItem(
            product.getId(),
            product.getStockQuantity(),
            product.getPurchasePrice(),
            subTotal,
            taxAmount,
            finalAmount
        );
    }

    @Override
    public void updateCartItem(final PurchaseItem item, final Product product, final String mode) {
        final int quantityChange = product.getStockQuantity();
        final double subTotal = productService.summateProductSubTotal(product,"purchase");
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
