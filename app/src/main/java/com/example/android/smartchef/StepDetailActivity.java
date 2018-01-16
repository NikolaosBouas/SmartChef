package com.example.android.smartchef;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.smartchef.utilities.NetworkUtils;

import java.net.URL;

import butterknife.Bind;
import butterknife.ButterKnife;

public class StepDetailActivity extends AppCompatActivity {

    private static final String RECIPE_URL =
            "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    private int position;
    private int stepPosition;
    private final static String STEP_POSITION_KEY = "step_key";
    private static String[] mSimpleJsonStepInstructions;
    private static String[] mSimpleJsonStepVideoUrls;
    @Bind(R.id.next_button) Button nextButton;
    @Bind(R.id.previous_button) Button previousButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent intentThatStartedTheActivity = getIntent();

        if (intentThatStartedTheActivity.hasExtra(getString(R.string.step_postition))) {
            stepPosition = intentThatStartedTheActivity.getIntExtra(getString(R.string.step_postition), 0);
        }

        if (intentThatStartedTheActivity.hasExtra(getString(R.string.position))) {
            position = intentThatStartedTheActivity.getIntExtra(getString(R.string.position), 0);
        }


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stepPosition = nextStep(stepPosition);
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stepPosition = previousStep(stepPosition);
            }
        });

        if (savedInstanceState == null){
            loadStepData();
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(STEP_POSITION_KEY,stepPosition);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);
        stepPosition = savedInstanceState.getInt(STEP_POSITION_KEY);

    }

    private int nextStep(int stepPosition){
        if(stepPosition < mSimpleJsonStepInstructions.length-1){
            stepPosition++;

            StepInstructionFragment instructionFragment = new StepInstructionFragment();

            instructionFragment.setmStepInstructions(mSimpleJsonStepInstructions);

            instructionFragment.setmIndex(stepPosition);

            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .replace(R.id.instruction_container,instructionFragment)
                    .commit();

            VideoFragment videoFragment = new VideoFragment();

            videoFragment.setmStepVideos(mSimpleJsonStepVideoUrls);

            videoFragment.setmIndex(stepPosition);

            fragmentManager.beginTransaction()
                    .replace(R.id.video_container,videoFragment)
                    .commit();
        }
        else {
            Toast.makeText(this,getString(R.string.no_further_steps),Toast.LENGTH_SHORT).show();
        }
        return stepPosition;
    }

    private int previousStep(int stepPosition){
        if(stepPosition > 0){
            stepPosition--;

            StepInstructionFragment instructionFragment = new StepInstructionFragment();

            instructionFragment.setmStepInstructions(mSimpleJsonStepInstructions);

            instructionFragment.setmIndex(stepPosition);

            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .replace(R.id.instruction_container,instructionFragment)
                    .commit();

            VideoFragment videoFragment = new VideoFragment();

            videoFragment.setmStepVideos(mSimpleJsonStepVideoUrls);

            videoFragment.setmIndex(stepPosition);

            fragmentManager.beginTransaction()
                    .replace(R.id.video_container,videoFragment)
                    .commit();
        }
        else {
            Toast.makeText(this,getString(R.string.no_previous_steps),Toast.LENGTH_SHORT).show();
        }
        return stepPosition;
    }

    public static String[] getmSimpleJsonStepInstructions() {
        return mSimpleJsonStepInstructions;
    }

    public static String[] getmSimpleJsonStepVideoUrls() {
        return mSimpleJsonStepVideoUrls;
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

                mSimpleJsonStepInstructions = NetworkUtils.getSimpleStepInstructionFromJson(jsonRecipeResponse,position);

                mSimpleJsonStepVideoUrls = NetworkUtils.getSimpleStepVideoUrlFromJson(jsonRecipeResponse,position);

                return null;

            } catch (Exception e) {


                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {



            StepInstructionFragment instructionFragment = new StepInstructionFragment();

            instructionFragment.setmStepInstructions(mSimpleJsonStepInstructions);

            instructionFragment.setmIndex(stepPosition);

            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .add(R.id.instruction_container,instructionFragment)
                    .commit();

            VideoFragment videoFragment = new VideoFragment();

            videoFragment.setmStepVideos(mSimpleJsonStepVideoUrls);

            videoFragment.setmIndex(stepPosition);

            fragmentManager.beginTransaction()
                    .add(R.id.video_container,videoFragment)
                    .commit();
        }
    }
}
