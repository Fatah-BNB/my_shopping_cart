package com.example.myshoppingcart;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = Item.class, version = 1)
public abstract class ItemsDatabase extends RoomDatabase {
    private static ItemsDatabase instance;
    public abstract ItemDao useItemDao();
    public static synchronized ItemsDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), ItemsDatabase.class, "items_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(callback).build();
        }
        return instance;
    }
    private static RoomDatabase.Callback callback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateTask(instance).execute();
        }
    };
    private static class PopulateTask extends AsyncTask<Void, Void, Void>{
        private ItemDao itemDao;
        private PopulateTask(ItemsDatabase db){itemDao = db.useItemDao();}
        @Override
        protected Void doInBackground(Void... voids) {
            itemDao.insert(new Item("Sugar 1KG", 1, 100, Color.BLACK, "grocery"));
            itemDao.insert(new Item("Oil 1L", 10, 135, Color.BLACK, "grocery"));
            itemDao.insert(new Item("Lemonade 2L", 3, 150, Color.BLACK, "drinks"));
            itemDao.insert(new Item("MAXON GATO", 5, 100, Color.BLACK, "cookies"));
            return null;
        }
    }
}
