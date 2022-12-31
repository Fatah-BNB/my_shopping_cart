package com.example.myshoppingcart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.List;

public class ItemsListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_list);

        RecyclerView itemsRecyclerView = findViewById(R.id.items_recycler_view);

        itemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemsRecyclerView.setHasFixedSize(true);

        final ItemAdapter adapter = new ItemAdapter();
        itemsRecyclerView.setAdapter(adapter);

        MainActivity.itemViewModel.getItemsByList(getIntent().getStringExtra("LIST")).observe(this, new Observer<List<Item>>() {
            @Override
            public void onChanged(List<Item> items) {
                adapter.setItems(items);
            }
        });
    }
}