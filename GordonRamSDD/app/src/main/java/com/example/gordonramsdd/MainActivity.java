package com.example.gordonramsdd;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

// Class and Activity that shows the Recipe, Inventory, and GroceryList buttons
public class MainActivity extends Activity {

    // string used to send messages
    public final static String EXTRA_MESSAGE = "";

    @Override
    // Function called when activity is started
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // Redirect to Inventory
    public void goToInventory(View view) {
        Intent intent = new Intent(this, Inventory.class);
        startActivity(intent);
    }

    // Redirect to Recipe
    public void goToRecipe(View view) {
        Intent intent = new Intent(this, RecipeActivity.class);
        startActivity(intent);
    }

    // Redirect to Login
    public void goToLogin(View view) {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    // Redirect to Grocery List
    public void goToGroceryList(View view) {
        Intent intent = new Intent(this, GroceryList.class);
        startActivity(intent);
    }
}
