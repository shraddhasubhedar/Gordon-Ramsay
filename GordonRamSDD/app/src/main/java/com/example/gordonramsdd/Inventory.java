package com.example.gordonramsdd;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Inventory extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
    }

    protected void create_inventory(View view) {
        Intent intent = new Intent(this, CreateInventory.class);
        startActivity(intent);
    }

    protected void exist_inventory(View view) {
        Intent intent = new Intent(this, ExistInventory.class);
        startActivity(intent);
    }
}
