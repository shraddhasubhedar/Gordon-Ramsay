package com.example.gordonramsdd;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

// Class and Activity used to redirect users depending on the buttons they press
public class Inventory extends Activity {

    @Override
    // Function that is called when the activity is created
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
    }

    // Redirects to CreateInventory
    protected void create_inventory(View view) {
        Intent intent = new Intent(this, CreateInventory.class);
        startActivity(intent);
    }

    // Redirects to ExistInventory
    protected void exist_inventory(View view) {
        Intent intent = new Intent(this, ExistInventory.class);
        startActivity(intent);
    }
}
