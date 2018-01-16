package com.example.android.smartchef;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.android.smartchef.utilities.NetworkUtils;

import java.net.URL;

public class RecipeDetailActivity extends AppCompatActivity  {

    private static final String TAG = RecipeDetailActivity.class.getSimpleName();

    private static final String RECIPE_URL =
            "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    private static final String LIST_STATE_KEY = "list-state-key";

    public static int position;
    private int mCurrentScrollPosition;
    private static String[] mIngredients;
    private static String[] mSteps;
    private static String[] mImageUrls;
    private static String[] mSimpleJsonStepInstructions1;
    private static String[] mSimpleJsonStepVideoUrls1;

    private static boolean mTwoPane;
    private String mTitle;



    public static String[] getmIngredients() {
        return mIngredients;
    }

    public static int getPosition() {
        return position;
    }

    public static String[] getmSteps() {
        return mSteps;
    }

    public static String[] getmImageUrls() {
        return mImageUrls;
    }

    public static boolean ismTwoPane() {
        return mTwoPane;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        Intent intentThatStartedTheActivity = getIntent();
        if (intentThatStartedTheActivity.hasExtra(Intent.EXTRA_TEXT)) {
            position = intentThatStartedTheActivity.getIntExtra(Intent.EXTRA_TEXT, 0);
        }
        if (intentThatStartedTheActivity.hasExtra(getString(R.string.title))) {
            mTitle = intentThatStartedTheActivity.getStringExtra(getString(R.string.title));
            setTitle(mTitle);
        }


        /* Once all of our views are setup, we can load the weather data. */

        loadRecipeData();


        if(findViewById(R.id.recipe_linear_layout) != null) {
            // This LinearLayout will only initially exist in the two-pane tablet case
            mTwoPane = true;

            if(savedInstanceState == null) {
                loadStepData();
            }
        } else {
            // We're in single-pane mode and displaying fragments on a phone in separate activities
            mTwoPane = false;
        }




    }


    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);

        if (state != null) {
            mCurrentScrollPosition = state.getInt(LIST_STATE_KEY);

        }
    }

    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);

        state.putInt(LIST_STATE_KEY, mCurrentScrollPosition);
    }

    public static String[] getmSimpleJsonStepInstructions1() {
        return mSimpleJsonStepInstructions1;
    }

    public static String[] getmSimpleJsonStepVideoUrls1() {
        return mSimpleJsonStepVideoUrls1;
    }

    private void loadRecipeData() {
        new FetchStepsTask().execute(RECIPE_URL);
        new FetchStepImagesTask().execute(RECIPE_URL);
        new FetchIngredientsTask().execute(RECIPE_URL);

    }


    public class FetchStepsTask extends AsyncTask<String, Void, String[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String[] doInBackground(String... params) {

            if (params.length == 0) {
                return null;
            }

            try {

                URL url = new URL(RECIPE_URL);

                String jsonRecipeResponse = NetworkUtils.getResponseFromHttpUrl(url);

                String[] simpleJsonSteps = NetworkUtils.getSimpleStepDescriptionFromJson(jsonRecipeResponse,position);

                return simpleJsonSteps;

            } catch (Exception e) {


                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String[] steps) {
            if (steps != null) {
                mSteps = steps;
                MasterListFragment masterListFragment = (MasterListFragment)
                        getSupportFragmentManager().findFragmentById(R.id.master_list_fragment);
                if (masterListFragment != null) {
                    masterListFragment.refreshDataSteps(mSteps);
                }
            } else {
            }
        }
    }

    public class FetchStepImagesTask extends AsyncTask<String, Void, String[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String[] doInBackground(String... params) {

            if (params.length == 0) {
                return null;
            }

            try {

                URL url = new URL(RECIPE_URL);

                String jsonRecipeResponse = NetworkUtils.getResponseFromHttpUrl(url);

                String[] simpleJsonImageUrls = NetworkUtils.getSimpleStepImageUrlFromJson(jsonRecipeResponse,position);


                return simpleJsonImageUrls;

            } catch (Exception e) {

                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String[] imageUrls) {
            if (imageUrls != null) {
                mImageUrls = imageUrls;
                MasterListFragment masterListFragment = (MasterListFragment)
                        getSupportFragmentManager().findFragmentById(R.id.master_list_fragment);
                if (masterListFragment != null) {
                    masterListFragment.refreshImagesSteps(mImageUrls);
                }
            } else {
            }
        }
    }

    public class FetchIngredientsTask extends AsyncTask<String, Void, String[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String[] doInBackground(String... params) {

            if (params.length == 0) {
                return null;
            }

            try {

                URL url = new URL(RECIPE_URL);

                String jsonRecipeResponse = NetworkUtils.getResponseFromHttpUrl(url);

                String[] simpleJsonIngredients = NetworkUtils.getSimpleIngredientsFromJson(jsonRecipeResponse,position);

                return simpleJsonIngredients;

            } catch (Exception e) {

                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String[] ingredients) {
            if (ingredients != null) {
                mIngredients = ingredients;
                 MasterListFragment masterListFragment = (MasterListFragment)
                         getSupportFragmentManager().findFragmentById(R.id.master_list_fragment);
                if (masterListFragment != null) {
                    masterListFragment.refreshDataIngredients(mIngredients);
                }
            } else {
            }
        }
    }

    private void loadStepData() {
        new FetchStepDataTask().execute(RECIPE_URL);

    }

    public class FetchStepDataTask extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... params) {

            if (params.length == 0) {
                return null;
            }

            try {

                URL url = new URL(RECIPE_URL);

                String jsonRecipeResponse = NetworkUtils.getResponseFromHttpUrl(url);

                mSimpleJsonStepInstructions1 = NetworkUtils.getSimpleStepInstructionFromJson(jsonRecipeResponse,position);

                mSimpleJsonStepVideoUrls1 = NetworkUtils.getSimpleStepVideoUrlFromJson(jsonRecipeResponse,position);

                return null;

            } catch (Exception e) {


                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {



            StepInstructionFragment instructionFragment = new StepInstructionFragment();

            instructionFragment.setmStepInstructions(mSimpleJsonStepInstructions1);

            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .add(R.id.instruction_container,instructionFragment)
                    .commit();

            VideoFragment videoFragment = new VideoFragment();

            videoFragment.setmStepVideos(mSimpleJsonStepVideoUrls1);

            fragmentManager.beginTransaction()
                    .add(R.id.video_container,videoFragment)
                    .commit();
        }
    }


}