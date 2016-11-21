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
    // This function is called when the activity is stored
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_inventory);
    }

    // This function is executed when the user attempts to create a new inventory
    public void createNewInventory(View view) {
        // Get the inventory name that the user inputs
        Intent intent = new Intent(this, ExistInventory.class);
        EditText editText = (EditText) findViewById(R.id.new_inventory_text);
        String message = editText.getText().toString();

        // Create the file for the new inventory
        String file_name = message + "_Inventory.txt";

        FileOutputStream fos;

        try {
            fos = openFileOutput(file_name, Context.MODE_PRIVATE);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Start the next activity
        startActivity(intent);
    }
}
