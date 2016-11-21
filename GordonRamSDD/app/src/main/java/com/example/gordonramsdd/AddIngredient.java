package com.example.gordonramsdd;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

// Class and Activity used to add ingredients to an inventory
public class AddIngredient extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredient);

        boolean grocery = false;

        // Get the string that was sent from the previous activity (the Inventory name)
        // and display in a TextView
        Intent intent = getIntent();
        final String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        if (message.equals("Grocery List")) {
            grocery = true;
        }
        TextView textView = new TextView(this);
        textView.setTextSize(20);
        if (grocery) {
            textView.setText(message);
        }
        else {
            textView.setText("Inventory: " + message);
        }

        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_add_ingredient);
        layout.addView(textView);

        // Execute the following if the button "add ingredients" is pressed
        Button button = (Button)findViewById(R.id.button_add_ingredient);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Create an Intent with the ShowInventory class and send the inventory
                Intent intent;
                if (message.equals("Grocery List")) {
                    intent = new Intent(view.getContext(), GroceryList.class);
                }
                else {
                    intent = new Intent(view.getContext(), ShowInventory.class);
                }
                intent.putExtra(MainActivity.EXTRA_MESSAGE, message);

                // Get the user input for the ingredient information with...
                // editText and message2 representing the ingredient name...
                EditText editText = (EditText) findViewById(R.id.new_ingredient_text) ;
                String message2 = editText.getText().toString();

                // editText2 and quantity representing the amount of the ingredient
                EditText editText2 = (EditText) findViewById(R.id.new_ingredient_quantity) ;
                String quantity = editText2.getText().toString();

                // editText3 and unitMeasurement representing the unit of the ingredient
                EditText editText3 = (EditText) findViewById(R.id.new_ingredient_unit) ;
                String unitMeasurement = editText3.getText().toString();

                // Add the ingredient to the inventory text file in the format "Name|Quantity|Unit"
                String combination = message2 + "|" + quantity + "|" + unitMeasurement + "\n";

                FileOutputStream fos;

                try {
                    if (message.equals("Grocery List")) {
                        fos = openFileOutput("grocery_list.txt", MODE_APPEND);
                    }
                    else {
                        fos = openFileOutput(message + "_Inventory.txt", MODE_APPEND);
                    }
                    OutputStreamWriter osw = new OutputStreamWriter(fos);
                    osw.write(combination);
                    osw.flush();
                    osw.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // Start the next activity (ShowInventory)
                startActivity(intent);
            }
        });
    }
}
