package com.example.gordonramsdd;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static android.R.id.list;

// Class and Activity used to find any existing inventories
public class ExistInventory extends Activity {

    @Override
    // Function that is called when the activity is started
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exist_inventory);

        // Find the inventory file names and store into private_files
        String[] private_files = fileList();
        ArrayList<String> inventory_files = new ArrayList<String>();

        for(int i = 0; i < private_files.length; i++) {
            if(private_files[i].contains("_Inventory")) {
                inventory_files.add(private_files[i].replace("_Inventory.txt",""));
            }
        }

        // Set the inventory file names in private files to a ListView and display
        String[] inventory_files_array = inventory_files.toArray(new String[inventory_files.size()]);

        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, inventory_files_array);

        ListView listView = (ListView) findViewById(R.id.inventory_files);
        listView.setAdapter(adapter);

        // Set what happens when you click on an inventory name -> goes to ShowInventory with the selected inventory name
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String fname = String.valueOf(parent.getItemAtPosition(position));

                Intent intent = new Intent(view.getContext(), ShowInventory.class);
                intent.putExtra(MainActivity.EXTRA_MESSAGE, fname);
                startActivity(intent);
            }
        });

    }

}
