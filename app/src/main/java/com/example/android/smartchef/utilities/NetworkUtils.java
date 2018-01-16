package com.example.android.smartchef.utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Nikos on 19/10/2017.
 */
public final class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();


    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    public static String[] getSimpleRecipeStringsFromJson(String recipeJsonStr)
            throws JSONException {


        JSONArray recipeArray = new JSONArray(recipeJsonStr);

        /* String array to hold each recipes name */
        String[] recipeNames = null;

        recipeNames = new String[recipeArray.length()];;

        for (int i = 0; i < recipeArray.length(); i++) {

            String recipeName;

            JSONObject recipe = recipeArray.getJSONObject(i);

            recipeName = recipe.getString("name");

            recipeNames[i]= recipeName;
        }

        return recipeNames;
    }


    public static String[] getSimpleIngredientsFromJson(String recipeJsonStr, int position)
            throws JSONException {


        JSONArray recipeArray = new JSONArray(recipeJsonStr);

        JSONObject recipe = recipeArray.getJSONObject(position);

        JSONArray ingredientsArray = recipe.getJSONArray("ingredients");

        String[] ingredients = new String[ingredientsArray.length()] ;


        for (int i = 0; i < ingredientsArray.length(); i++) {

            String ingredient;

            String quantity;

            String measurement;

            JSONObject ingredientObject = ingredientsArray.getJSONObject(i);

            ingredient = ingredientObject.getString("ingredient");

            quantity = ingredientObject.getString("quantity");

            measurement = ingredientObject.getString("measure");

            ingredients[i] = quantity + " " + measurement + " " + ingredient ;
        }

        return ingredients;

    }



    public static String[] getSimpleStepDescriptionFromJson(String recipeJsonStr, int position)
            throws JSONException {

        JSONArray recipeArray = new JSONArray(recipeJsonStr);

        JSONObject recipe = recipeArray.getJSONObject(position);

        JSONArray stepsArray = recipe.getJSONArray("steps");

        String[] stepsDescription = new String[stepsArray.length()];



        for (int i = 0; i < stepsArray.length(); i++) {

            String stepDescription;

            JSONObject stepObject = stepsArray.getJSONObject(i);

            stepDescription = stepObject.getString("shortDescription");

            stepsDescription[i] = stepDescription;
        }

        return stepsDescription;

    }

    public static String[] getSimpleStepInstructionFromJson(String recipeJsonStr, int position)
            throws JSONException {

        JSONArray recipeArray = new JSONArray(recipeJsonStr);

        JSONObject recipe = recipeArray.getJSONObject(position);

        JSONArray stepsArray = recipe.getJSONArray("steps");

        String[] stepsInstruction = new String[stepsArray.length()];


        for (int i = 0; i < stepsArray.length(); i++) {

            String stepInstruction;

            JSONObject stepObject = stepsArray.getJSONObject(i);

            stepInstruction = stepObject.getString("description");

            stepsInstruction[i] = stepInstruction;
        }

        return stepsInstruction;

    }

    public static String[] getSimpleStepVideoUrlFromJson(String recipeJsonStr, int position)
            throws JSONException {

        JSONArray recipeArray = new JSONArray(recipeJsonStr);

        JSONObject recipe = recipeArray.getJSONObject(position);

        JSONArray stepsArray = recipe.getJSONArray("steps");

        String[] videosUrl = new String[stepsArray.length()];

        for (int i = 0; i < stepsArray.length(); i++) {

            String videoUrl;

            JSONObject stepObject = stepsArray.getJSONObject(i);

            videoUrl = stepObject.getString("videoURL");

            if(videoUrl != null && !videoUrl.isEmpty()){
                videosUrl[i] = videoUrl;
            }
            else {
                videosUrl[i] = null;
            }

        }

        return videosUrl;

    }

    public static String[] getSimpleStepImageUrlFromJson(String recipeJsonStr, int position)
            throws JSONException {

        JSONArray recipeArray = new JSONArray(recipeJsonStr);

        JSONObject recipe = recipeArray.getJSONObject(position);

        JSONArray stepsArray = recipe.getJSONArray("steps");

        String[] imagesUrl = new String[stepsArray.length()];

        for (int i = 0; i < stepsArray.length(); i++) {

            String imageUrl;

            JSONObject stepObject = stepsArray.getJSONObject(i);

            imageUrl = stepObject.getString("thumbnailURL");

            if(imageUrl != null && !imageUrl.isEmpty()){
                imagesUrl[i] = imageUrl;
            }
            else {
                imagesUrl[i] = null;
            }

        }

        return imagesUrl;

    }

    public static String[] getSimpleRecipeImagesFromJson(String recipeJsonStr)
            throws JSONException {


        JSONArray recipeArray = new JSONArray(recipeJsonStr);

        /* String array to hold each recipes imageUrl */
        String[] recipeImages = null;

        recipeImages = new String[recipeArray.length()];;

        for (int i = 0; i < recipeArray.length(); i++) {

            String recipeImageUrl;

            JSONObject recipe = recipeArray.getJSONObject(i);

            recipeImageUrl = recipe.getString("image");

            recipeImages[i]= recipeImageUrl;
        }

        return recipeImages;
    }


}