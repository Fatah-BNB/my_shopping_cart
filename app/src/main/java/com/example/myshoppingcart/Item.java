package com.example.myshoppingcart;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "items_table")
public class Item {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private int quantity;
    private int color;

    public Item(String name, int quantity, int color) {
        this.name = name;
        this.quantity = quantity;
        this.color = color;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getColor() {
        return color;
    }
}
