package com.example.myshoppingcart;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import yuku.ambilwarna.AmbilWarnaDialog;

public class AddActivity extends AppCompatActivity {
    public static final String EXTRA_ID = "ITEM_ID";
    public static final String EXTRA_NAME = "ITEM_NAME";
    public static final String EXTRA_QNT = "ITEM_QNT";
    public static final String EXTRA_PRICE = "ITEM_PRICE";
    public static final String EXTRA_LIST = "ITEM_LIST";
    private EditText itemName, itemPrice, itemList;
    private Button saveBtn;
    private NumberPicker itemQnt;
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        itemName = findViewById(R.id.ietm_name_input);
        itemPrice = findViewById(R.id.ietm_price_input);
        itemList = findViewById(R.id.item_list_input);
        itemQnt = findViewById(R.id.item_qnt_input);
        saveBtn = findViewById(R.id.save_btn);
        itemQnt.setMinValue(1);
        itemQnt.setMaxValue(99);
        itemQnt.setTextColor(getResources().getColor(R.color.fancy_yellow));
        itemQnt.setBackgroundResource(R.color.black);
        itemQnt.setTextSize(100);
        itemQnt.setDividerPadding(50);

        Intent intent = getIntent();
        if(intent.hasExtra(EXTRA_ID)){
            itemName.setText(intent.getStringExtra(EXTRA_NAME));
            itemList.setText(intent.getStringExtra(EXTRA_LIST));
            itemPrice.setText(String.valueOf(intent.getDoubleExtra(EXTRA_PRICE, 1)));
            itemQnt.setValue(intent.getIntExtra(EXTRA_QNT, 1));
            saveBtn.setText("save Changes");

        }

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(itemName.getText().toString().trim().isEmpty() || itemPrice.getText().toString().trim().isEmpty() || itemList.getText().toString().trim().isEmpty()){
                    Toast.makeText(AddActivity.this, "provide a name and a unit price and a list name for the item", Toast.LENGTH_SHORT).show();
                }else if (Double.parseDouble(itemPrice.getText().toString().trim()) == 0.0) {
                    Toast.makeText(AddActivity.this, "price can't be 0.0", Toast.LENGTH_SHORT).show();
                }else{
                    saveItem();
                }
//                else{
//                    AmbilWarnaDialog dialog = new AmbilWarnaDialog(AddActivity.this, 0xff000000, true, new AmbilWarnaDialog.OnAmbilWarnaListener() {
//                        @Override
//                        public void onCancel(AmbilWarnaDialog dialog) {
//
//                        }
//
//                        @Override
//                        public void onOk(AmbilWarnaDialog dialog, int color) {
//                            saveItem();
//                        }
//                    }).show();
//                }
            }
        });
    }
    public void saveItem(){
        String name = itemName.getText().toString().trim();
        String listName = itemList.getText().toString().trim();
        String upperName = name.substring(0, 1).toUpperCase() + name.substring(1);
        String price = itemPrice.getText().toString().trim();
        int qnt = itemQnt.getValue();
            Intent data = new Intent();
            data.putExtra(EXTRA_NAME, upperName);
            data.putExtra(EXTRA_LIST, listName.toLowerCase());
            data.putExtra(EXTRA_PRICE, Double.parseDouble(price));
            data.putExtra(EXTRA_QNT, qnt);
            int id = getIntent().getIntExtra(EXTRA_ID, -1);
            if(id != -1){
                data.putExtra(EXTRA_ID, id);
            }
            setResult(RESULT_OK, data);
            finish();
    }
}