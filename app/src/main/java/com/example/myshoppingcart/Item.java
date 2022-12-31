package com.example.myshoppingcart;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "items_table")
public class Item {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private int quantity;
    private double price;
    private double totalPrice;
    private int color;
    private boolean isCounted;
    private String list;

    public Item(String name, int quantity, double price, int color, String list) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.color = color;
        this.list = list;
        this.totalPrice = price * quantity;
        isCounted = true;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setList(String list) {
        this.list = list;
    }

    public void setCounted(boolean counted) {
        isCounted = counted;
    }

    public boolean isCounted() {
        return isCounted;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public int getColor() {
        return color;
    }

    public String getList() {
        return list;
    }
}
