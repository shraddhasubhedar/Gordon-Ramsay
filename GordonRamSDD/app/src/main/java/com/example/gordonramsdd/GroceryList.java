package com.example.gordonramsdd;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import static com.example.gordonramsdd.R.layout.activity_grocery_list;

// Class and Activity used specifically for the Grocery List
public class GroceryList extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_grocery_list);

        // Create the file if the file does not exist
        FileOutputStream fos;
        FileInputStream fis;

        boolean file_exist = false;

        try {
            fis = openFileInput("grocery_list.txt");
            fis.close();
            file_exist = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!file_exist) {
            try {
                fos = openFileOutput("grocery_list.txt", Context.MODE_PRIVATE);
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Display the ingredients within the grocery list
        ArrayList<String> inventory_files = new ArrayList<>();

        try {
            fis = openFileInput("grocery_list.txt");
            if (fis != null) {
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader br = new BufferedReader(isr);

                String line;

                do {
                    line = br.readLine();
                    if (line == null)
                        continue;
                    String[] splitting = line.split("\\|");
                    line = "";
                    for (int i = 0; i < splitting.length - 1; i++) {
                        line += splitting[i];
                        line += "       ";
                    }
                    line += splitting[splitting.length-1];
                    inventory_files.add(line);
                } while (line != null);
            }
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Set up the ListView to display and select ingredients in the grocery list
        String[] inventory_files_array = inventory_files.toArray(new String[inventory_files.size()]);

        ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.activity_listview, inventory_files_array);

        ListView listView = (ListView) findViewById(R.id.ingredient_list);
        listView.setAdapter(adapter);

        // Set what happens when an ingredient is clicked -> go to EditInventory with the inventory name and
        // selected ingredient attribute
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle extras = new Bundle();
                String fname = String.valueOf(parent.getItemAtPosition(position));
                extras.putString("Inventory Name","Grocery List");
                extras.putString("Ingredient",fname);
                Intent intent = new Intent(view.getContext(), EditInventory.class);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });

        // Button to be used when adding ingredient to the grocery list
        Button button = (Button)findViewById(R.id.add_ingredient);

        // When add ingredient buton is clicked, go to AddIngredient
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddIngredient.class);
                intent.putExtra(MainActivity.EXTRA_MESSAGE, "Grocery List");
                startActivity(intent);
            }
        });

        // Button to be used when emptying grocery list
        Button button2 = (Button)findViewById(R.id.empty_grocery_list);

        // When empty button is clicked, rewrites the contents of the grocery list to be empty
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FileOutputStream fos;
                try {
                    fos = openFileOutput("grocery_list.txt", Context.MODE_PRIVATE);
                    fos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(v.getContext(), GroceryList.class);
                startActivity(intent);
            }
        });
    }

}
