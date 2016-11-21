package com.example.gordonramsdd;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.FileOutputStream;

// Class and Activity used to create a new inventory
public class CreateInventory extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_inventory);
    }

    public void createNewInventory(View view) {
        Intent intent = new Intent(this, ExistInventory.class);
        EditText editText = (EditText) findViewById(R.id.new_inventory_text);
        String message = editText.getText().toString();

        String file_name = message + "_Inventory.txt";

        FileOutputStream fos;

        try {
            fos = openFileOutput(file_name, Context.MODE_PRIVATE);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        startActivity(intent);
    }
}
