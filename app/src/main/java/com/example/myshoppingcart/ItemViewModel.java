package com.example.myshoppingcart;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ItemViewModel extends AndroidViewModel {
    private ItemsRepo repository;
    private LiveData<List<Item>> allItems;
    private LiveData<List<String>> lists;
    private LiveData<List<Item>> itemsByLists;
    private LiveData<List<Item>> allItemsQnt;
    private LiveData<Integer> totalItems;
    private LiveData<Double> totalPrice;
    private LiveData<Double> totalPricList;

    public ItemViewModel(@NonNull Application application) {
        super(application);
        repository = new ItemsRepo(application);
        allItems = repository.getAllItems();
        totalItems = repository.getTotalItems();
        totalPrice = repository.getTotalPrice();
        lists = repository.getLists();
    }
    public void insertItem(Item itm){
        repository.insert(itm);
    }
    public void updateItem(Item itm){
        repository.update(itm);
    }
    public void deleteItem(Item itm){
        repository.delete(itm);
    }
    public void deleteAllItems(){
        repository.deleteAllItems();
    }
    public LiveData<List<Item>> getAllItems() {
        return allItems;
    }
    public LiveData<List<String>> getLists() {
        return lists;
    }
    public LiveData<List<Item>> getAllItemsQnt(int qnt) {
        allItemsQnt = repository.getAllItemsQnt(qnt);
        return allItemsQnt;
    }
    public LiveData<List<Item>> getItemsByList(String list) {
        itemsByLists = repository.getItemsByList(list);
        return itemsByLists;
    }
    public LiveData<Integer> getTotalItems() {
        return totalItems;
    }
    public LiveData<Double> getTotalPrice() {
        return totalPrice;
    }
    public LiveData<Double> getTotalPriceByList(String list) {
        totalPricList = repository.getTotalPriceByList(list);
        return totalPricList;
    }
}
