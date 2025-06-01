package com.twozo.inventorymanagementsystem.service;

import com.twozo.inventorymanagementsystem.dao.TradeDao;
import com.twozo.inventorymanagementsystem.model.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

@Service
public class PurchaseService implements TradeService<Product, Purchase, PurchaseItem, PurchaseTransactionRequest>{
    private final TradeDao<Vendor, Purchase, PurchaseItem> purchaseDataHandler;
    private final CheckExistenceServiceImpl checkExistenceServiceImpl;
    private final ProductService productService;
    private final VendorService vendorService;
    private Collection<PurchaseItem> cart ;

    public PurchaseService(final VendorService vendorService, final ProductService productService,
                           final TradeDao<Vendor, Purchase, PurchaseItem> purchaseDataHandler, final CheckExistenceServiceImpl checkExistenceServiceImpl){
        this.productService = productService;
        this.vendorService = vendorService;
        this.purchaseDataHandler = purchaseDataHandler;
        this.checkExistenceServiceImpl = checkExistenceServiceImpl;
        this.cart = new ArrayList<>();
    }

    @Override
    public boolean add(final Product product){
        final Product updatedProduct = productService.getProduct(product.getId());
        updatedProduct.setStockQuantity(product.getStockQuantity());

        for (final PurchaseItem item : cart) {

            if (item.getProductId() == updatedProduct.getId()) {
                updateCartItem(item, updatedProduct, CartUpdateMode.ADD);
                return true;
            }

        }

        final PurchaseItem item = summate(updatedProduct);
        final boolean added = cart.add(item);
        return added;
    }

    @Override
    public boolean remove(final Product product) {
        final Product updatedProduct = productService.getProduct(product.getId());
        updatedProduct.setStockQuantity(product.getStockQuantity());

        for (final PurchaseItem item : cart) {

            if (item.getProductId() == updatedProduct.getId()) {

                if (item.getQuantity() > updatedProduct.getStockQuantity()) {
                    updateCartItem(item, updatedProduct, CartUpdateMode.REMOVE);
                    return true;
                } else {
                    return cart.remove(item);
                }

            }

        }

        return false;
    }

    @Override
    public boolean clearCart(){
        cart = new ArrayList<>();
        return true;
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
    public boolean updateCartItem(final PurchaseItem item, final Product product, final CartUpdateMode mode) {
        final int quantityChange = product.getStockQuantity();
        final double subTotal = productService.summateProductSubTotal(product,"purchase");
        final double tax = productService.summateProductTaxAmount(subTotal, product);
        final double finalAmount = productService.summateProductFinalAmount(subTotal + tax, product);

        if (mode == CartUpdateMode.ADD) {
            item.setQuantity(item.getQuantity() + quantityChange);
            item.setSubTotal(item.getSubTotal() + subTotal);
            item.setTaxAmount(item.getTaxAmount() + tax);
            item.setFinalDiscountedAmount(item.getFinalDiscountedAmount() + finalAmount);
            return true;
        } else if (mode == CartUpdateMode.REMOVE) {
            item.setQuantity(item.getQuantity() - quantityChange);
            item.setSubTotal(item.getSubTotal() - subTotal);
            item.setTaxAmount(item.getTaxAmount() - tax);
            item.setFinalDiscountedAmount(item.getFinalDiscountedAmount() - finalAmount);
            return true;
        }

        return false;
    }

    @Override
    public double getSubtotal() {
        return new PurchaseItem().getCartSubtotal(cart,"purchase");
    }

    @Override
    public double getTax(){
        return new PurchaseItem().getCartTaxAmount(cart);
    }

    @Override
    public double getFinalAmount(final FinalAmountRequest finalAmountRequest){
        return new PurchaseItem().getCartFinalAmount(finalAmountRequest.getAmount(),finalAmountRequest.getRate());
    }

    @Override
    public boolean completeTrade(final PurchaseTransactionRequest purchaseTransactionRequest) {
        final String phoneNo = purchaseTransactionRequest.getPhoneNo();
        final String method = purchaseTransactionRequest.getMode();
        final double amount = purchaseTransactionRequest.getAmount();
        final LocalDate currentDate = LocalDate.now();
        final boolean isAvailable = checkExistenceServiceImpl.checkVendor(phoneNo);
        Vendor existingVendor = null;

        if (isAvailable) {
            existingVendor = vendorService.get(phoneNo);
        } else{
            return false;
        }

        final Payment payment = new Payment(method, amount, "debit", currentDate);

        if(!purchaseDataHandler.processTransaction(existingVendor,payment,purchaseTransactionRequest.getPurchase(),purchaseTransactionRequest.getCart())){
            return false;
        }
        else{
            return productService.updateStock(purchaseTransactionRequest.getCart());
        }

    }
}
