package com.example.gordonramsdd;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static android.R.attr.button;

// Activity and class that shows the ingredients in an inventory
public class ShowInventory extends Activity {

    // variables used for showing the ingredients
    String message = "";
    private TextView textView;

    // Function that is called when the activity is started
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_inventory);

        // Set the textView to show the inventory name
        Intent intent = getIntent();
        message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        textView = (TextView) findViewById(R.id.inventory_name);
        textView.setTextSize(20);
        textView.setText("Inventory: " + message);

        // Gets the ingredients in the inventory and store into inventory_files
        ArrayList<String> inventory_files = new ArrayList<String>();

        FileInputStream fis;

        try {
            fis = openFileInput(message + "_Inventory.txt");

            if (fis != null) {
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader br = new BufferedReader(isr);

                String line = "";

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
        };

        // Set the ListView to show the ingredients stored in inventory_files
        String[] inventory_files_array = inventory_files.toArray(new String[inventory_files.size()]);

        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, inventory_files_array);

        ListView listView = (ListView) findViewById(R.id.ingredient_list);
        listView.setAdapter(adapter);

        // When an ingredient in the ListView is clicked
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Create a bundle and store the inventory name and ingredient attributes into it
                Bundle extras = new Bundle();
                String fname = String.valueOf(parent.getItemAtPosition(position));
                extras.putString("Inventory Name",message);
                extras.putString("Ingredient",fname);

                // Start EditInventory
                Intent intent = new Intent(view.getContext(), EditInventory.class);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });

        // Button used to add ingredients
        Button button = (Button)findViewById(R.id.add_ingredient);

        // When the add ingredient button has been clicked, go to AddIngredient
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddIngredient.class);
                intent.putExtra(MainActivity.EXTRA_MESSAGE, message);
                startActivity(intent);
            }
        });

        // Button used to delete the inventory
        Button button2 = (Button)findViewById(R.id.delete_inventory);

        // When the delete inventory button is pressed, the file is found and deleted
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                File dir = getFilesDir();
                File file = new File(dir, message + "_Inventory.txt");
                boolean deleted = file.delete();
                Intent intent = new Intent(v.getContext(), ExistInventory.class);
                startActivity(intent);
            }
        });
    }

}
