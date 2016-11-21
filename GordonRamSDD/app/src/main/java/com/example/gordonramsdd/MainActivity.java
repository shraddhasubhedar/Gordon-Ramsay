package com.example.gordonramsdd;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    public final static String EXTRA_MESSAGE = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToInventory(View view) {
        Intent intent = new Intent(this, Inventory.class);
        startActivity(intent);
    }

    public void goToRecipe(View view) {
        Intent intent = new Intent(this, RecipeActivity.class);
        startActivity(intent);
    }

    public void goToLogin(View view) {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    public void goToGroceryList(View view) {
        Intent intent = new Intent(this, GroceryList.class);
        startActivity(intent);
    }
}
