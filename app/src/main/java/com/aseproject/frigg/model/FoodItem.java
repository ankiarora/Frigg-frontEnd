package com.aseproject.frigg.model;

public class FoodItem {
    private String grocery_item_name;
    private Integer quantity;

    public String getItemName() {
        return grocery_item_name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
