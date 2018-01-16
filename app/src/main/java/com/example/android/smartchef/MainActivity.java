package com.example.android.smartchef;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.smartchef.utilities.NetworkUtils;

import java.net.URL;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.RecipeAdapterOnClickHandler {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final String RECIPE_URL =
            "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    private static final String LIST_STATE_KEY = "list-state-key";
    LinearLayoutManager layoutManager;
    private RecipeAdapter mRecipeAdapter;
    private String[] mRecipeImageUrls;
    private int mCurrentScrollPosition;

    @Bind(R.id.recyclerview_recipe) RecyclerView mRecyclerView;
    @Bind(R.id.tv_error_message_display) TextView mErrorMessageDisplay;
    @Bind(R.id.pb_loading_indicator) ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        /*
         * LinearLayoutManager can support HORIZONTAL or VERTICAL orientations. The reverse layout
         * parameter is useful mostly for HORIZONTAL layouts that should reverse for right to left
         * languages.
         */
        layoutManager = new LinearLayoutManager(this);

        mRecyclerView.setLayoutManager(layoutManager);



        /*
         * Use this setting to improve performance if you know that changes in content do not
         * change the child layout size in the RecyclerView
         */
        mRecyclerView.setHasFixedSize(true);

        mRecipeAdapter = new RecipeAdapter(this);
        mRecipeAdapter.setContext(this);


        /* Setting the adapter attaches it to the RecyclerView in our layout. */
        mRecyclerView.setAdapter(mRecipeAdapter);

        loadRecipeData();


    }


    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);

        // Retrieve list state and list/item positions
        if (state != null) {
            mCurrentScrollPosition = state.getInt(LIST_STATE_KEY);

        }
    }

    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);

        // Save list state
        mCurrentScrollPosition = layoutManager.findFirstVisibleItemPosition();
        state.putInt(LIST_STATE_KEY, mCurrentScrollPosition);
    }


    private void loadRecipeData() {
        showRecipeDataView();
        new FetchRecipeTask().execute(RECIPE_URL);
    }

    /**
     * This method is overridden by our MainActivity class in order to handle RecyclerView item
     * clicks.
     *
     * @param position The position for the recipe that was clicked
     */
    @Override
    public void onClick(int position) {
        Context context = this;
        Class destinationClass = RecipeDetailActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        intentToStartDetailActivity.putExtra(Intent.EXTRA_TEXT, position);
        intentToStartDetailActivity.putExtra(getString(R.string.title), mRecipeAdapter.getItem(position));
        startActivity(intentToStartDetailActivity);

    }


    private void showRecipeDataView() {
        /* First, make sure the error is invisible */
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        /* Then, make sure the recipes are visible */
        mRecyclerView.setVisibility(View.VISIBLE);
    }


    private void showErrorMessage() {
        /* First, hide the currently visible data */
        mRecyclerView.setVisibility(View.INVISIBLE);
        /* Then, show the error */
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }


    public class FetchRecipeTask extends AsyncTask<String, Void, String[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);


        }

        @Override
        protected String[] doInBackground(String... params) {

            if (params.length == 0) {
                return null;
            }

            try {

                URL url = new URL(RECIPE_URL);

                String jsonRecipeResponse = NetworkUtils.getResponseFromHttpUrl(url);

                String[] simpleJsonRecipes = NetworkUtils.getSimpleRecipeStringsFromJson(jsonRecipeResponse);

                mRecipeImageUrls = NetworkUtils.getSimpleRecipeImagesFromJson(jsonRecipeResponse);

                return simpleJsonRecipes;

            } catch (Exception e) {


                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String[] recipeNames) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (recipeNames != null) {
                showRecipeDataView();
                mRecipeAdapter.setRecipeNames(recipeNames);
                mRecipeAdapter.setRecipeImages(mRecipeImageUrls);
                layoutManager.scrollToPosition(mCurrentScrollPosition);
                new Thread() {
                    public void run() {
                        try {
                            Thread.sleep(1500);
                        } catch (Exception ex) {
                        }
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                if (mRecyclerView != null) {
                                    mRecyclerView.scrollToPosition(mCurrentScrollPosition);
                                }
                            }
                        });
                    }
                }.start();
            } else {
                showErrorMessage();
            }
        }
    }

}