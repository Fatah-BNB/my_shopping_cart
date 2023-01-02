package com.example.myshoppingcart;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ItemDao {
    @Insert
    void insert (Item item);
    @Update
    void update (Item item);
    @Delete
    void delete (Item item);
    @Query("DELETE FROM items_table")
    void deleteAll();
    @Query("SELECT * FROM items_table")
    LiveData<List<Item>> getAllItems();
    @Query("SELECT DISTINCT list FROM items_table")
    LiveData<List<String>> getLists();
    @Query("SELECT * FROM items_table WHERE list = :list")
    LiveData<List<Item>> getItemsByList(String list);
    @Query("SELECT * FROM items_table WHERE quantity = :qnt")
    LiveData<List<Item>> getAllItemsBasedOnQnt(int qnt);
    @Query("SELECT COUNT(*) FROM items_table")
    LiveData<Integer> getTotalItems();
    @Query("SELECT COUNT(*) FROM items_table where list = :list")
    LiveData<Integer> getTotalItemsByList(String list);
    @Query("SELECT SUM(quantity * price) FROM items_table WHERE isCounted")
    LiveData<Double> getTotalPrice();
    @Query("SELECT SUM(quantity * price) FROM items_table WHERE isCounted and list = :list")
    LiveData<Double> getTotalByList(String list);

}
