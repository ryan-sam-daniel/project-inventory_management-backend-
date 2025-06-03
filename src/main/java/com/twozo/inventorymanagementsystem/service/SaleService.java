package com.twozo.inventorymanagementsystem.service;

import com.twozo.inventorymanagementsystem.dao.TradeDao;
import com.twozo.inventorymanagementsystem.model.Customer;
import com.twozo.inventorymanagementsystem.model.Sale;
import com.twozo.inventorymanagementsystem.model.SaleItem;
import com.twozo.inventorymanagementsystem.model.Product;
import com.twozo.inventorymanagementsystem.model.SaleTransactionRequest;
import com.twozo.inventorymanagementsystem.model.FinalAmountRequest;
import com.twozo.inventorymanagementsystem.model.CartUpdateMode;
import com.twozo.inventorymanagementsystem.model.Payment;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

@Service
public class SaleService implements TradeService<Product, Sale , SaleItem, SaleTransactionRequest>{

    private final TradeDao<Customer, Sale, SaleItem> saleDataHandler;
    private final ProductService productService;
    private final CustomerService customerService;
    private Collection<SaleItem> cart;
    
    public SaleService(final CustomerService customerService, final ProductService productService,
                       final TradeDao<Customer, Sale, SaleItem> saleDataHandler){
        this.customerService = customerService;
        this.productService = productService;
        this.saleDataHandler = saleDataHandler;
        this.cart = new ArrayList<>();
    }

    @Override
    public boolean add(final Product product){
        final Product updatedProduct = productService.getProduct(product.getId());
        updatedProduct.setStockQuantity(product.getStockQuantity());

        for (final SaleItem item : cart) {

            if (item.getProductId() == updatedProduct.getId()) {
                updateCartItem(item, updatedProduct, CartUpdateMode.ADD);
                return true;
            }

        }

        final SaleItem item = summate(updatedProduct);
        final boolean added = cart.add(item);
        return added;
    }

    @Override
    public boolean remove(final Product product) {
        final Product updatedProduct = productService.getProduct(product.getId());
        updatedProduct.setStockQuantity(product.getStockQuantity());

        for (final SaleItem item : cart) {

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
    public boolean updateCartItem(final SaleItem item, final Product product, final CartUpdateMode mode) {
        final int quantityChange = product.getStockQuantity();
        final double subTotal = productService.summateProductSubTotal(product, "sale");
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
        return new SaleItem().getCartSubtotal(cart,"sale");
    }

    @Override
    public double getTax(){
        return new SaleItem().getCartTaxAmount(cart);
    }

    @Override
    public double getFinalAmount(final FinalAmountRequest finalAmountRequest){
        return new SaleItem().getCartFinalAmount(finalAmountRequest.getAmount(), finalAmountRequest.getRate());
    }

    @Override
    public boolean completeTrade(final SaleTransactionRequest saleTransactionRequest) {
        final String phoneNo = saleTransactionRequest.getPhoneNo();
        final String method = saleTransactionRequest.getMode();
        final double amount = saleTransactionRequest.getAmount();
        final LocalDate currentDate = LocalDate.now();
        final boolean isAvailable = customerService.exists(phoneNo);
        Customer existingCustomer = null;

        if (isAvailable){
            existingCustomer = customerService.get(phoneNo);
        } else{
            return false;
        }

        final Payment payment = new Payment(method, amount, "credit", currentDate);

        if(!saleDataHandler.processTransaction(existingCustomer,payment,saleTransactionRequest.getSale(),saleTransactionRequest.getCart())){
            return false;
        } else{
            return productService.removeStock(saleTransactionRequest.getCart());
        }

    }

}
