package com.example.myshoppingcart;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ItemsRepo {
    private ItemDao itemDao;
    private LiveData<List<Item>> allItems;
    private LiveData<List<Item>> allItemsQnt;
    private LiveData<Integer> totalItems;

    public ItemsRepo(Application application){
        ItemsDatabase database = ItemsDatabase.getInstance(application);
        itemDao = database.useItemDao();
        allItems = itemDao.getAllItems();
        totalItems = itemDao.getTotalItems();
    }

    public void insert(Item item){
        new InsertAsync(itemDao).execute(item);
    }
    public void update(Item item){
        new UpdateAsync(itemDao).execute(item);
    }
    public void delete(Item item){
        new DeleteAsync(itemDao).execute(item);
    }
    public void deleteAllItems(){
        new DeleteAllAsync(itemDao).execute();
    }

    public LiveData<List<Item>> getAllItems() {
        return allItems;
    }
    public LiveData<List<Item>> getAllItemsQnt(int qnt) {
        allItemsQnt = itemDao.getAllItemsBasedOnQnt(qnt);
        return allItemsQnt;
    }
    public LiveData<Integer> getTotalItems() {
        return totalItems;
    }

    private static class InsertAsync extends AsyncTask<Item, Void, Void>{
        private ItemDao itemDao;
        private InsertAsync(ItemDao itemDao){this.itemDao = itemDao;}
        @Override
        protected Void doInBackground(Item... items) {
            itemDao.insert(items[0]);
            return null;
        }
    }
    private static class UpdateAsync extends AsyncTask<Item, Void, Void>{
        private ItemDao itemDao;
        private UpdateAsync(ItemDao itemDao){this.itemDao = itemDao;}
        @Override
        protected Void doInBackground(Item... items) {
            itemDao.update(items[0]);
            return null;
        }
    }
    private static class DeleteAsync extends AsyncTask<Item, Void, Void>{
        private ItemDao itemDao;
        private DeleteAsync(ItemDao itemDao){this.itemDao = itemDao;}
        @Override
        protected Void doInBackground(Item... items) {
            itemDao.delete(items[0]);
            return null;
        }
    }
    private static class DeleteAllAsync extends AsyncTask<Void, Void, Void>{
        private ItemDao itemDao;
        private DeleteAllAsync(ItemDao itemDao){this.itemDao = itemDao;}
        @Override
        protected Void doInBackground(Void... voids) {
            itemDao.deleteAll();
            return null;
        }
    }
}
