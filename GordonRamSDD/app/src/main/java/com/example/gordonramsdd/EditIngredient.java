package com.example.gordonramsdd;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;

import static android.R.id.message;

public class EditIngredient extends Activity {

    String inventory_name = "";
    String ingredient_name = "";
    Double ingredient_quantity = 0.0;
    String ingredient_unit = "";
    TextView textView;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_ingredient);

        Intent intent = getIntent();
        final Bundle extras = intent.getExtras();
        inventory_name = extras.getString("Inventory Name");
        textView = (TextView) findViewById(R.id.inventory_name);
        textView.setTextSize(20);
        if (inventory_name.equals("Grocery List")) {
            textView.setText(inventory_name);
        }
        else {
            textView.setText("Inventory: " + inventory_name);
        }

        String[] splitting = extras.getString("Ingredient").split(" ");
        ingredient_name = splitting[0];

        FileInputStream fis;
        try {
            if (inventory_name.equals("Grocery List")) {
                fis = openFileInput("grocery_list.txt");
            }
            else {
                fis = openFileInput(message + "_Inventory.txt");
            }
            if (fis != null) {
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader br = new BufferedReader(isr);

                String line = "";

                do {
                    line = br.readLine();
                    if (line == null)
                        continue;
                    splitting = line.split("\\|");
                    if (!splitting[0].equals(ingredient_name))
                        continue;
                    ingredient_quantity = Double.parseDouble(splitting[1]);
                    ingredient_unit = splitting[2];
                    break;
                } while (line != null);
            }
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        };

        textView = (TextView) findViewById(R.id.ingredient_name);
        textView.setTextSize(18);
        textView.setText("NAME:" + ingredient_name);

        editText = (EditText) findViewById(R.id.ingredient_quantity);
        editText.setHint("QUANTITY: " + ingredient_quantity.toString());
        editText.setTextSize(18);

        textView = (TextView) findViewById(R.id.ingredient_unit);
        textView.setHint("UNIT: " + ingredient_unit);
        textView.setTextSize(18);

        Button button = (Button)findViewById(R.id.submit_edit_ingredient);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent;
                if (inventory_name.equals("Grocery List")) {
                    intent = new Intent(view.getContext(), GroceryList.class);
                } else {
                    intent = new Intent(view.getContext(), ShowInventory.class);
                }
                intent.putExtra(MainActivity.EXTRA_MESSAGE, inventory_name);

                EditText editText2 = (EditText) findViewById(R.id.ingredient_quantity) ;
                String quantity = editText2.getText().toString();

                EditText editText3 = (EditText) findViewById(R.id.ingredient_unit);
                String unit = editText3.getText().toString();

                //EditText editText3 = (EditText) findViewById(R.id.ingredient_unit) ;
                //String unitMeasurement = editText3.getText().toString();
                if (quantity != "" && quantity != null) {
                    FileInputStream fis;
                    FileOutputStream fos;
                    ArrayList<String> inventory_files = new ArrayList<String>();

                    try {
                        fis = openFileInput("grocery_list.txt");

                        if (fis != null) {
                            InputStreamReader isr = new InputStreamReader(fis);
                            BufferedReader br = new BufferedReader(isr);

                            String line = "";

                            do {
                                line = br.readLine();
                                if (line == null)
                                    continue;
                                String[] splitting = line.split("\\|");
                                if (!splitting[0].equals(ingredient_name)) {
                                    inventory_files.add(line);
                                }
                            } while (line != null);
                        }
                        fis.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    };

                    String temp2;
                    if (unit != null && unit != "") {
                        temp2 = ingredient_name + "|" + quantity + "|" + unit;
                    }
                    else {
                        temp2 = ingredient_name + "|" + quantity + "|" + ingredient_unit;
                    }
                    inventory_files.add(temp2);

                    try {
                        if (inventory_name.equals("Grocery List")) {
                            fos = openFileOutput("grocery_list.txt", MODE_PRIVATE);
                            fos.close();
                            fos = openFileOutput("grocery_list.txt", MODE_APPEND);
                        }
                        else {
                            fos = openFileOutput(inventory_name + "_Inventory.txt", MODE_APPEND);
                        }
                        OutputStreamWriter osw = new OutputStreamWriter(fos);
                        Iterator<String> iterator;
                        iterator = inventory_files.iterator();
                        while(iterator.hasNext()) {
                            osw.write(iterator.next());
                            osw.flush();
                        }
                        osw.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                startActivity(intent);
            }
        });
    }

}
