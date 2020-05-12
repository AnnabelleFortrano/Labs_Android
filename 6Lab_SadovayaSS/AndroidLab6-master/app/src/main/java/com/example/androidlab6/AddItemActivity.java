package com.example.androidlab6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        Button button = findViewById(R.id.add_btn_add_activity);
        final EditText nameText = findViewById(R.id.name_add_activity);
        final EditText priceText = findViewById(R.id.price_add_activity);
        final EditText countText = findViewById(R.id.count_add_activity);
        final EditText descriptionText = findViewById(R.id.desc_add_activity);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nameText.getText().toString() != ""
                && priceText.getText().toString() != ""
                && countText.getText().toString() != ""
                && descriptionText.getText().toString() != "") {
                    try {
                        String name = nameText.getText().toString();
                        int price = Integer.parseInt(priceText.getText().toString());
                        int count = Integer.parseInt(countText.getText().toString());
                        String description = descriptionText.getText().toString();
                        Item newItem = new Item(0, name, price, count, description);
                        ItemService.getInstance().addItem(newItem);
                        finish();
                    } catch (NumberFormatException e) {
                        Toast.makeText(getApplicationContext(), "Неверный ввод", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Не все поля заполнены", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
