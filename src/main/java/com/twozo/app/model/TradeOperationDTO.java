package com.twozo.app.model;

public class TradeOperationDTO {
    private int id;
    private int quantity;

    public TradeOperationDTO(){
        //no-args constructor
    }

    public TradeOperationDTO(final int id, final int quantity){
        this.id = id;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(final int quantity) {
        this.quantity = quantity;
    }
}
