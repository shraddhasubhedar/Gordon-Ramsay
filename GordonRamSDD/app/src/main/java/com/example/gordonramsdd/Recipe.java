package com.example.gordonramsdd;

/**
 * Created by absnachos on 11/21/2016.
 */

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by segrea on 11/20/2016.
 */

//Recipe class, it isn't overly essential. But it makes displaying our recipe a little cleaner, and is much easier and friendlier to code
public class Recipe {
    String publisher, f2f_url, title, source_url, recipe_id, image_url, publisher_url;
    double social_rank; //easy member variables for all the information
    List<String> ingredients;

    public Recipe(){ //default constructor
        publisher = "";
        f2f_url = "";
        title = "";
        source_url = "";
        recipe_id = "";
        image_url = "";
        publisher_url = "";
        social_rank = 0.0;
    }
    public Recipe(String base){ //this is the one that will actually be used
        String[] lines = base.split(System.getProperty("line.separator")); //it's a big String with information separated by lines
        title = lines[0];
        publisher = lines[1];
        recipe_id = lines[2];
        source_url = lines[3];
        social_rank = Double.parseDouble(lines[4]);
        publisher_url = lines[5];
        f2f_url = lines[6];
        image_url = lines[7];
        ingredients = new ArrayList<String>(); //make dummy ingredients list
    }

    //it's the usual "String, with ingredients separated by \n" deal
    public void put_ingredients(String s){
        String lines[] = s.split(System.getProperty("line.separator"));
        for(int i = 0; i < lines.length; i++){
            ingredients.add(lines[i]);
        }
    }


    //retrieve the ingredients in our favorite format
    public String getIngredients(){
        String z = "";
        for(int i = 0; i < ingredients.size(); i++){
            z+=ingredients.get(i);
            z+="\n";
        }
        return z;
    }
    
    //i was going to implement this here, then there was a conflict with some Activity stuff, so I put it back in the RecipeActivity instead
    public void decrementInventory(){

    }

    //Get Functions
    public String getPublisher(){
        return publisher;
    }
    public String getF2f_url(){
        return f2f_url;
    }
    public String getTitle(){
        return title;
    }
    public String getSource_url(){
        return source_url;
    }
    public String getRecipe_id(){
        return recipe_id;
    }
    public String getImage_url(){
        return image_url;
    }
    public String getPublisher_url(){
        return publisher_url;
    }
}
