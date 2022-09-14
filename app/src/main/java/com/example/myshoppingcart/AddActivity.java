package com.example.myshoppingcart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {
    public static final String EXTRA_ID = "ITEM_ID";
    public static final String EXTRA_NAME = "ITEM_NAME";
    public static final String EXTRA_QNT = "ITEM_QNT";
    private EditText itemName;
    private NumberPicker itemQnt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        itemName = findViewById(R.id.ietm_name_input);
        itemQnt = findViewById(R.id.item_qnt_input);
        itemQnt.setMinValue(1);
        itemQnt.setMaxValue(99);

        Intent intent = getIntent();
        if(intent.hasExtra(EXTRA_ID)){
            itemName.setText(intent.getStringExtra(EXTRA_NAME));
            itemQnt.setValue(intent.getIntExtra(EXTRA_QNT, 1));
        }

        findViewById(R.id.save_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveItem();
            }
        });
    }
    public void saveItem(){
        String name = itemName.getText().toString().trim();
        int qnt = itemQnt.getValue();
        if(name.isEmpty()){
            Toast.makeText(this, "provide a name for the item", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent data = new Intent();
        data.putExtra(EXTRA_NAME, name);
        data.putExtra(EXTRA_QNT, qnt);
        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if(id != -1){
            data.putExtra(EXTRA_ID, id);
        }
        setResult(RESULT_OK, data);
        finish();
    }
}