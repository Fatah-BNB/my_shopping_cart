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
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_ITEM_REQUEST = 1;
    public static final int EDIT_ITEM_REQUEST = 2;
    static ItemViewModel itemViewModel;
    static RelativeLayout root;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        TextView totalItems = findViewById(R.id.total_items);
        TextView totalPrice = findViewById(R.id.total_price);
        TextView empty = findViewById(R.id.empty);
        root = findViewById(R.id.root);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final ItemAdapter adapter = new ItemAdapter();
        recyclerView.setAdapter(adapter);

        findViewById(R.id.icon_bar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View vi = findViewById(R.id.lists_recycler_view);
                int v = (vi.getVisibility() == View.GONE)? View.VISIBLE: View.GONE;
                TransitionManager.beginDelayedTransition(findViewById(R.id.nav_bar),new AutoTransition());
                vi.setVisibility(v);
            }
        });


        itemViewModel = new ViewModelProvider(this).get(ItemViewModel.class);
        itemViewModel.getAllItems().observe(this, new Observer<List<Item>>() {
            @Override
            public void onChanged(List<Item> items) {
                adapter.setItems(items);
                if(items.isEmpty()){
                    empty.setVisibility(View.VISIBLE);
                }else{
                    empty.setVisibility(View.GONE);
                }
            }
        });


        itemViewModel.getTotalItems().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer total) {
                if(total == 0){totalItems.setText("No items");
//                    findViewById(R.id.empty).setVisibility(View.VISIBLE);
                }
                else if (total == 1){totalItems.setText("One item");
//                    findViewById(R.id.empty).setVisibility(View.GONE);
                }
                else{totalItems.setText(String.valueOf(total)+" items");
//                    findViewById(R.id.empty).setVisibility(View.GONE);
                }
            }
        });

        findViewById(R.id.delete_all_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemViewModel.deleteAllItems();
                Toast.makeText(MainActivity.this, "all items deleted", Toast.LENGTH_SHORT).show();
            }
        });

        itemViewModel.getTotalPrice().observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double totalPriceValue) {
                if (totalPriceValue == null){totalPriceValue = 0.0;}
                totalPrice.setText(" ??? "+totalPriceValue+" DA");
            }
        });

        findViewById(R.id.add_new_item).setOnClickListener(new View.OnClickListener() {
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
                intent.putExtra(AddActivity.EXTRA_LIST, item.getList());
                intent.putExtra(AddActivity.EXTRA_PRICE, item.getPrice());
                intent.putExtra(AddActivity.EXTRA_QNT, item.getQuantity());
                intent.putExtra(AddActivity.EXTRA_COUNTED, item.isCounted());

                startActivityForResult(intent, EDIT_ITEM_REQUEST);
            }
        });


        RecyclerView listRecyclerView = findViewById(R.id.lists_recycler_view);

        listRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        listRecyclerView.setHasFixedSize(true);


        final ListAdapter list_adapter = new ListAdapter();
        listRecyclerView.setAdapter(list_adapter);


        MainActivity.itemViewModel.getLists().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> lists) {
                lists.add("all");
                list_adapter.setLists(lists);
            }
        });

        list_adapter.setOnListClickListener(new ListAdapter.onListClickListener() {
            @Override
            public void onListClick(String list) {
                TextView allItmes = findViewById(R.id.all_items);
                allItmes.setText(list);
                if (list.equals("all")){
                    itemViewModel.getAllItems().observe(MainActivity.this, new Observer<List<Item>>() {
                        @Override
                        public void onChanged(List<Item> items) {
                            adapter.setItems(items);
                            itemViewModel.getTotalPrice().observe(MainActivity.this, new Observer<Double>() {
                                @Override
                                public void onChanged(Double price) {
                                    if (price == null){price = 0.0;}
                                    totalPrice.setText(" ??? "+price+" DA");
                                }
                            });
                            itemViewModel.getTotalItems().observe(MainActivity.this, new Observer<Integer>() {
                                @Override
                                public void onChanged(Integer total) {
                                    if(total == 0){totalItems.setText("No items");}
                                    else if (total == 1){totalItems.setText("One item");}
                                    else{totalItems.setText(String.valueOf(total)+" items");}
                                }
                            });
                        }
                    });
                }else {
                    itemViewModel.getItemsByList(list).observe(MainActivity.this, new Observer<List<Item>>() {
                        @Override
                        public void onChanged(List<Item> items) {
                            adapter.setItems(items);
                            itemViewModel.getTotalPriceByList(list).observe(MainActivity.this, new Observer<Double>() {
                                @Override
                                public void onChanged(Double price) {
                                    if (price == null) {
                                        price = 0.0;
                                    }
                                    totalPrice.setText(" ??? " + price + " DA");
                                }
                            });
                            itemViewModel.getTotalItemsByList(list).observe(MainActivity.this, new Observer<Integer>() {
                                @Override
                                public void onChanged(Integer total) {
                                    if (total == 0) {
                                        totalItems.setText("No items");
                                    } else if (total == 1) {
                                        totalItems.setText("One item");
                                    } else {
                                        totalItems.setText(String.valueOf(total) + " items");
                                    }
                                }
                            });
                        }
                    });
                }
            }
        });

        findViewById(R.id.all_items).setOnClickListener(view -> {
//            itemViewModel.getAllItems().observe(this, new Observer<List<Item>>() {
//                @Override
//                public void onChanged(List<Item> items) {
//                    adapter.setItems(items);
//                    itemViewModel.getTotalPrice().observe(MainActivity.this, new Observer<Double>() {
//                        @Override
//                        public void onChanged(Double price) {
//                            if (price == null){price = 0.0;}
//                            totalPrice.setText(" ??? "+price+" DA");
//                        }
//                    });
//                    itemViewModel.getTotalItems().observe(MainActivity.this, new Observer<Integer>() {
//                        @Override
//                        public void onChanged(Integer total) {
//                            if(total == 0){totalItems.setText("No items");}
//                            else if (total == 1){totalItems.setText("One item");}
//                            else{totalItems.setText(String.valueOf(total)+" items");}
//                        }
//                    });
//                }
//            });
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ADD_ITEM_REQUEST && resultCode == RESULT_OK){
            String itemName = data.getStringExtra(AddActivity.EXTRA_NAME);

            String itemListName = data.getStringExtra(AddActivity.EXTRA_LIST); double itemPrice = data.getDoubleExtra(AddActivity.EXTRA_PRICE, 1);
            int itemQnt = data.getIntExtra(AddActivity.EXTRA_QNT, 1);

            Item item = new Item(itemName, itemQnt, itemPrice, Color.BLACK, itemListName);
            itemViewModel.insertItem(item);
            Toast.makeText(this, "item added", Toast.LENGTH_SHORT).show();
        } else if(requestCode == EDIT_ITEM_REQUEST && resultCode == RESULT_OK){
            int itemId = data.getIntExtra(AddActivity.EXTRA_ID, -1);
            String itemName = data.getStringExtra(AddActivity.EXTRA_NAME);
            String itemListName = data.getStringExtra(AddActivity.EXTRA_LIST);
            double itemPrice = data.getDoubleExtra(AddActivity.EXTRA_PRICE, 1);
            int itemQnt = data.getIntExtra(AddActivity.EXTRA_QNT, 1);
            boolean counted = data.getBooleanExtra(AddActivity.EXTRA_COUNTED, true);
            if(itemId == -1){
                Toast.makeText(this, "can't update item", Toast.LENGTH_SHORT).show();
                return;
            }
            Item item = new Item(itemName, itemQnt, itemPrice, Color.BLACK, itemListName);
            item.setId(itemId);
            item.setCounted(counted);
            itemViewModel.updateItem(item);
            //Toast.makeText(this, "item updated", Toast.LENGTH_SHORT).show();
        } else{
            //Toast.makeText(this, "item not saved", Toast.LENGTH_SHORT).show();
        }
    }
}