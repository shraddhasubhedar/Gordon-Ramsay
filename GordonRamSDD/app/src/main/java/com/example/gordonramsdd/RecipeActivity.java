package com.example.gordonramsdd;



import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.Buffer;
import java.util.ArrayList;

import static android.R.id.list;


public class RecipeActivity extends Activity implements View.OnClickListener{


    Button btnSearch;       //the search button
    String key;             //key to use for the Recipe API to access recipes
    String[] ids;           //list of recipe id's that we get from the search
    Recipe rec;             //a global recipe that we populate with data once a user selects one
    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_xml);
        key = "41d808a2b7e04d86a820a8345cd8da30";
        final TextView text3 = (TextView) findViewById(R.id.textView3);
        text3.setVisibility(View.INVISIBLE);
        //final ListView listylist = (ListView) findViewById(R.id.android_R_id_list);
        //listylist.setVisibility(View.INVISIBLE);
        btnSearch = (Button) findViewById(R.id.button);
        btnSearch.setOnClickListener(RecipeActivity.this);
    }

    @Override
    public void onClick(View v){
        final EditText edit1 = (EditText) findViewById(R.id.editText);
        String str1 = edit1.getText().toString();
        final EditText edit2 = (EditText) findViewById(R.id.editText2);
        String str2 = edit2.getText().toString();
        final EditText edit3 = (EditText) findViewById(R.id.editText3);
        String str3 = edit3.getText().toString();
        TextView field = (TextView)findViewById(R.id.textView3);
        try {
            recipeSearch(str1, str2, str3, field);
            /*edit1.setVisibility(View.INVISIBLE);
            edit2.setVisibility(View.INVISIBLE);
            edit3.setVisibility(View.INVISIBLE);
            final TextView text2 = (TextView) findViewById(R.id.textView2);
            text2.setVisibility(View.INVISIBLE);
            Button but = (Button) findViewById(R.id.button);
            but.setVisibility(View.INVISIBLE);*/
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void recipeSearch(String str1, String str2, String str3, TextView fi) throws IOException, JSONException {
        String ing1, ing2, ing3, result;
        ing1 = str1;
        ing2 = str2;
        ing3 = str3;
        result = recipe_connect(ing1,ing2,ing3);
        //System.out.println(result);
        if(result.equals("0")){
            fi.setVisibility(View.VISIBLE);
            fi.setText("\n\n\nNo recipes found");
            return;
        }
        String[] lines = result.split(System.getProperty("line.separator"));
        ids = new String[10];
        String s2 = "";
        int limit = 1 + lines.length/8;
        System.out.println(limit);
        for(int z = 0; z < limit; z++){
            System.out.println("z = "+z);
            s2+=lines[z*8];
            System.out.println("lines[z*8] = "+lines[z*8]);
            s2+="\n";
            ids[z] = lines[2+(z*8)];
        }
        display_recipes(s2);
        //String s1 = lines[0]+"\n"+lines[1]+"\n"+lines[2]+"\n"+lines[3]+"\n"+lines[4]+"\n"+lines[5]+"\n"+lines[6];
        //Recipe recipe1 = new Recipe(s1);
        //String ingredientss = get_ingredientss(lines[2]);
        //recipe1.put_ingredients(ingredientss);
        //String something = "\n\n\n";
        //ArrayList<String> idk = recipe1.getIngredients();
        //for(int i = 0; i < idk.size(); i++){
        //    something+=idk.get(i);
        //}
        fi.setText(s2);
        //fi.setText("\n\n\n"+ingredientss);
        //fi.setText("\n\n\n"+recipe1.getTitle()+"\n"+recipe1.getSource_url());
        //fi.setText("\n\n"+lines[0]+"\n"+lines[3]+"\n\n"+lines[8]+"\n"+lines[11]);
        //TextView field = new TextView(textView3);
        fi.setVisibility(View.VISIBLE);
        //fi.setText(result);
    }

    public String recipe_connect(String str1, String str2, String str3) throws IOException, JSONException {
        // http://food2fork.com/about/api
        // Make a URL to the web page
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String result = "";
        String ing1 = str1;
        String ing2 = str2;
        String ing3 = str3;
        // create this unique key for every user when new sign in takes place.
        String key = "41d808a2b7e04d86a820a8345cd8da30";
        //q is the variable for searching by different ingredients. Cn
        String[] ingredient = new String[10];//%2C
        //arranging them in single string format
        ingredient[0] = ing1;
        ingredient[1] = ing2;
        ingredient[2] = ing3;
        //ingredient[3] = "chicken";
        //ingredient[4] = "cheese";
        //ingredient[5] = "celery";
        //ingredient[6] = "onion";
        //ingredient[7] = "blue cheese";
        //ingredient[8] = "butter";
        //ingredient[0]="chicken";
        //ingredient[1]="cheddar cheese";
        //ingredient[2]="bread";
        //ingredient[3]="mayo";
        //ingredient[4]="hot sauce";


        String q = null;
        int index_space = -1;
        for (int i = 0; ingredient[i] != null; i++) {
            index_space = ingredient[i].indexOf(' ');
            if (index_space >= 0) {
                ingredient[i] = ingredient[i].substring(0, index_space) + "%20" + ingredient[i].substring(index_space + 1);
            }
            q = q + "%2C" + ingredient[i];
        }


        //Sorting Results by t= trendiness and r=ratings
        char sort = 't';
        //30 Results on each page
        int page = 1;


        URL url = new URL("http://food2fork.com/api/search?key=" + key + "&q=" + q);




        // Get the input stream through URL Connection
        URLConnection con = url.openConnection();
        InputStream is = con.getInputStream();


        BufferedReader br = new BufferedReader(new InputStreamReader(is));


        String line = null;
        String line2 = null;


        int i = 0;
        int j = 0;


        // read each line and write to System.out
        while ((line = br.readLine()) != null) {
            //result+="\n" + line;
            line2 = line;
            i = i + 1;
        }


        JSONObject obj = new JSONObject(line2);
        //String pageName = obj.getJSONObject("count");


        JSONArray arr = obj.getJSONArray("recipes");

        if(arr.length() < 1) return "0";
        for (int i1 = 0; i1 < arr.length(); i1++) {
            String publisher = arr.getJSONObject(i1).getString("publisher");
            String f2f_url = arr.getJSONObject(i1).getString("f2f_url");
            String title = arr.getJSONObject(i1).getString("title");
            String source_url = arr.getJSONObject(i1).getString("source_url");
            String recipe_id = arr.getJSONObject(i1).getString("recipe_id");
            String image_url = arr.getJSONObject(i1).getString("image_url");
            double social_rank = arr.getJSONObject(i1).getDouble("social_rank");
            String publisher_url = arr.getJSONObject(i1).getString("publisher_url");
            //String ingredients = arr.getJSONObject(i1).getString("ingredients");
            //System.out.println("\nIngredients = "+ingredients+"\n");
        }


        //result+="\n\n";


        while ((j < 10)) {
            result+=arr.getJSONObject(j).getString("title");
            result+="\n";
            result+=arr.getJSONObject(j).getString("publisher");
            result+="\n";
            result+=arr.getJSONObject(j).getString("recipe_id");
            result+="\n";
            result+=arr.getJSONObject(j).getString("source_url");
            result+="\n";
            result+=arr.getJSONObject(j).getDouble("social_rank");
            result+="\n";
            result+=arr.getJSONObject(j).getString("publisher_url");
            result+="\n";
            result+=arr.getJSONObject(j).getString("f2f_url");
            result+="\n";
            //result+=arr.getJSONObject(j).getString("ingredients");
            result+="\n";
            j = j + 1;
        }


        return result;
    }
    public String get_ingredientss(String id) throws IOException, JSONException{
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        URL url = new URL("http://food2fork.com/api/get?key="+key+"&rId="+id);
        URLConnection con = url.openConnection();
        InputStream is = con.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        //String line = null;
        //while((line = br.readLine()) != null){
        //    System.out.println(line);
        //}
        /*String line = null;
        String line2 = null;
        int i = 0;
        int j = 0;
        while((line = br.readLine()) != null){
            line2 = line;
            i = i+1;
        }*/
        String line = null;
        line = br.readLine();
        System.out.println(line);
        JSONObject obj = new JSONObject(line);
        String result = "";
        JSONObject obj2 = obj.getJSONObject("recipe");
        result+=obj2.getString("title");
        result+="\n";
        result+=obj2.getString("publisher");
        result+="\n";
        result+=id;
        result+="\n";
        result+=obj2.getString("source_url");
        result+="\n";
        result+=obj2.getDouble("social_rank");
        result+="\n";
        result+=obj2.getString("publisher_url");
        result+="\n";
        result+=obj2.getString("f2f_url");
        result+="\n";
        result+=obj2.getString("image_url");
        result+="\n";
        JSONArray arr = obj2.getJSONArray("ingredients");
        String ingredients = "";
        for(int i = 0; i< arr.length(); i++){
            ingredients+=arr.getString(i);
            ingredients+="\n";
            result+=arr.getString(i);
            result+="\n";
        }
        //String ingredients = arr.getJSONObject(0).getString("ingredients");
        return result;
        //return obj.getString("ingredients");
        //JSONArray arr = obj.getJSONArray("")
    }

    public void display_recipes(String titles) throws IOException, JSONException{
        setContentView(R.layout.recipes_display);
        ArrayList<String> listItems = new ArrayList<String>();
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
        final ListView listView = (ListView) findViewById(R.id.recipe_list);
        listView.setAdapter(adapter);
        String[] lines = titles.split(System.getProperty("line.separator"));
        for(int i = 0; i < lines.length; i++){
            listItems.add(lines[i]);
            adapter.notifyDataSetChanged();
        }
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg2, int position, long arg3) {
                goToRecipe(position);
            }
        });
    }
    private void goToRecipe(int num) {
        try {
            setContentView(R.layout.display_one_recipe);
            String info = get_ingredientss(ids[num]);
            String lines[] = info.split(System.getProperty("line.separator"));
            String smthidk = "";
            for(int i = 0; i < 8; i++){
                smthidk+=lines[i];
                smthidk+="\n";
            }
            rec = new Recipe(smthidk);
            String ings = "";
            for(int i = 8; i < lines.length; i++){
                ings+=lines[i];
                ings+="\n";
            }
            rec.put_ingredients(ings);
            String ing_display = rec.getIngredients();
            TextView tv = (TextView) findViewById(R.id.textView5);
            tv.setText(rec.getTitle()+"\n\n"+"Source:\n"+rec.getSource_url()+"\n\nIngredients:\n"+ing_display);
            final Button button = (Button) findViewById(R.id.button2);
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    decrementIngredients(rec.getIngredients());
                    button.setVisibility(View.INVISIBLE);
                }
            });
        }
        catch(org.json.JSONException exception){
            exception.printStackTrace();
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
    }

    private void decrementIngredients(String ings){
        //System.out.println(ings);
        ArrayList<String> ingredients = new ArrayList<String>();
        String[] lines = ings.split(System.getProperty("line.separator"));
        for(int i = 0; i < lines.length; i++){
            ingredients.add(lines[i]);
        }
        for(int i = 0; i < ingredients.size(); i++){
            String ing = ingredients.get(i);
            String words[] = ing.split("\\s+");
            double amt;
            String unit;
            String ing_name = "";
            if(!Character.isDigit(words[0].charAt(0))){
                continue;
            }
            if(words[0].length() > 1){
                if(words[0].charAt(1) == '/'){
                    amt = (double)Character.getNumericValue(words[0].charAt(0)) / (double)Character.getNumericValue(words[0].charAt(2));
                } else {
                    try{
                        amt = Double.parseDouble(words[0]);
                    }
                    catch(NumberFormatException e){
                        continue;
                    }
                }
            } else {
                try {
                    amt = Double.parseDouble(words[0]);
                } catch (NumberFormatException e) {
                    continue;
                }
            }
            unit = words[1];
            for(int z = 2; z < words.length; z++){
                ing_name+=words[z];
                ing_name+=" ";
            }
            ing_name = ing_name.substring(0,ing_name.length()-1);
            /*FileInputStream fis;
            try{
                fis = openFileInput("same_Inventory.txt");
                if(fis != null){
                    InputStreamReader isr = new InputStreamReader(fis);
                    BufferedReader br = new BufferedReader(isr);
                    String line = "";
                    do{
                        try{
                            line = br.readLine();
                            if(line == null) continue;
                            String[] splitting = line.split("\\|");
                            if(splitting[0].equals(ing_name)){

                            }
                        }
                        catch(IOException e){

                        }
                    } while (line != null);
                }
            }
            catch(FileNotFoundException e){
            }*/
            try {
                FileInputStream fis = openFileInput("inv1_Inventory.txt");
                if (fis != null) {
                    InputStreamReader isr = new InputStreamReader(fis);
                    BufferedReader br = new BufferedReader(isr);
                    String line;
                    String input = "";
                    String output = "";
                    int count = 0;
                    double fin_amt;
                    while ((line = br.readLine()) != null) {
                        input += line;
                        input += "\n";
                    }
                    fis.close();
                    String[] linez = input.split(System.getProperty("line.separator"));
                    for (int j = 0; j < linez.length; j++) {
                        //System.out.println(linez[j]);
                        count++;
                        String[] splitting = linez[j].split("\\|");
                        //System.out.println("Comparing name " + splitting[0] + " to ingredient " + ing_name + "\n");
                        if (splitting[0].equalsIgnoreCase(ing_name)) {
                            System.out.println("Comparing name " + splitting[0] + " to ingredient " + ing_name + "\n");
                            if (splitting[2].equalsIgnoreCase(unit) || splitting[2].equalsIgnoreCase(unit.substring(0, unit.length() - 1)) || unit.equalsIgnoreCase(splitting[2].substring(0, splitting[2].length()-1))) {
                                System.out.println("Matching units?");
                                fin_amt = Double.parseDouble(splitting[1]) - amt;
                                if (fin_amt < 0) fin_amt = 0.;
                            } else {
                                fin_amt = Double.parseDouble(splitting[1]) / 2.0;
                            }
                            replaceQuantity(fin_amt, count, ing_name, unit, input);
                        }
                    }
                }
            }
            catch(Exception e){
                continue;
            }
        }
    }
    public void replaceQuantity(double new_quantity, int line_num, String title, String unit, String full_text){
        String output = "";
        String lines[] = full_text.split(System.getProperty("line.separator"));
        line_num--;
        for(int i = 0; i < line_num; i++){
            output+=lines[i];
            output+="\n";
        }
        System.out.println("Replacing ingredient "+title+" to "+Double.toString(new_quantity));
        output+=title;
        output+="|";
        output+= Double.toString(new_quantity);
        output+="|";
        output+=unit;
        output+="\n";
        for(int i = line_num+1; i < lines.length; i++){
            output+=lines[i];
            output+="\n";
        }
        try{
            FileOutputStream fileOut = openFileOutput("inv1_Inventory.txt", Context.MODE_PRIVATE);
            fileOut.write(output.getBytes());
            fileOut.close();
        }
        catch(Exception e){

        }
    }
}