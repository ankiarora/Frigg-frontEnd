package com.aseproject.frigg.model;

import java.io.Serializable;

public class GroceryItem<T> implements Serializable {
    private Integer grocery_item_id;
    private String grocery_item_name;
    private Integer quantity;

    public Integer getGrocery_item_id() {
        return grocery_item_id;
    }

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
