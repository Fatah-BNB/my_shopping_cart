package com.example.myshoppingcart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_ITEM_REQUEST = 1;
    public static final int EDIT_ITEM_REQUEST = 2;
    private ItemViewModel itemViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        TextView totalItems = findViewById(R.id.total_items);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final ItemAdapter adapter = new ItemAdapter();
        recyclerView.setAdapter(adapter);

        itemViewModel = new ViewModelProvider(this).get(ItemViewModel.class);
        itemViewModel.getAllItems().observe(this, new Observer<List<Item>>() {
            @Override
            public void onChanged(List<Item> items) {
                adapter.setItems(items);
            }
        });

        itemViewModel.getTotalItems().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer total) {
                totalItems.setText(String.valueOf(total));
            }
        });

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this, AddActivity.class), ADD_ITEM_REQUEST);
            }
        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                itemViewModel.deleteItem(adapter.getItemAtPosition(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Item deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new ItemAdapter.onItemClickListener() {
            @Override
            public void onItemClick(Item item) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                intent.putExtra(AddActivity.EXTRA_ID, item.getId());
                intent.putExtra(AddActivity.EXTRA_NAME, item.getName());
                intent.putExtra(AddActivity.EXTRA_QNT, item.getQuantity());

                startActivityForResult(intent, EDIT_ITEM_REQUEST);
            }
        });

        totalItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemViewModel.deleteAllItems();
                Toast.makeText(MainActivity.this, "All items deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ADD_ITEM_REQUEST && resultCode == RESULT_OK){
            String itemName = data.getStringExtra(AddActivity.EXTRA_NAME);
            int itemQnt = data.getIntExtra(AddActivity.EXTRA_QNT, 1);

            Item item = new Item(itemName, itemQnt, Color.BLACK);
            itemViewModel.insertItem(item);
            Toast.makeText(this, "item added", Toast.LENGTH_SHORT).show();
        } else if(requestCode == EDIT_ITEM_REQUEST && resultCode == RESULT_OK){
            int itemId = data.getIntExtra(AddActivity.EXTRA_ID, -1);
            String itemName = data.getStringExtra(AddActivity.EXTRA_NAME);
            int itemQnt = data.getIntExtra(AddActivity.EXTRA_QNT, 1);
            if(itemId == -1){
                Toast.makeText(this, "can't update item", Toast.LENGTH_SHORT).show();
                return;
            }
            Item item = new Item(itemName, itemQnt, Color.BLACK);
            item.setId(itemId);
            itemViewModel.updateItem(item);
            Toast.makeText(this, "item updated", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(this, "item not saved", Toast.LENGTH_SHORT).show();
        }
    }
}