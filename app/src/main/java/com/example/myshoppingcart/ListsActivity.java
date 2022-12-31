package com.example.myshoppingcart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import java.util.List;

public class ListsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lists);

        RecyclerView listRecyclerView = findViewById(R.id.lists_recycler_view);

        listRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        listRecyclerView.setHasFixedSize(true);

        final ListAdapter adapter = new ListAdapter();
        listRecyclerView.setAdapter(adapter);


        MainActivity.itemViewModel.getLists().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> lists) {
                adapter.setLists(lists);
            }
        });

        adapter.setOnListClickListener(new ListAdapter.onListClickListener() {
            @Override
            public void onListClick(String list) {
                Intent intent = new Intent(ListsActivity.this, ItemsListActivity.class);
                intent.putExtra("LIST", list);
                startActivity(intent);
            }
        });
    }
}