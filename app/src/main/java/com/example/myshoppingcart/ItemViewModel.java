package com.example.myshoppingcart;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ItemViewModel extends AndroidViewModel {
    private ItemsRepo repository;
    private LiveData<List<Item>> allItems;
    private LiveData<List<Item>> allItemsQnt;
    private LiveData<Integer> totalItems;

    public ItemViewModel(@NonNull Application application) {
        super(application);
        repository = new ItemsRepo(application);
        allItems = repository.getAllItems();
        totalItems = repository.getTotalItems();
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
    public LiveData<List<Item>> getAllItemsQnt(int qnt) {
        allItemsQnt = repository.getAllItemsQnt(qnt);
        return allItemsQnt;
    }
    public LiveData<Integer> getTotalItems() {
        return totalItems;
    }
}
