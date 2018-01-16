package com.example.android.smartchef;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by Nikos on 11/11/2017.
 */


public class MasterListFragment extends Fragment implements StepAdapter.StepAdapterOnClickHandler{

    // Mandatory empty constructor
    public MasterListFragment() {
    }

    LinearLayoutManager ingredientsLayoutManager;

    public IngredientsAdapter mIngredientsAdapter;

    LinearLayoutManager stepsLayoutManager;

    public StepAdapter mStepsAdapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_master_list, container, false);

        ingredientsLayoutManager = new LinearLayoutManager(getContext());

        stepsLayoutManager = new LinearLayoutManager(getContext());


        RecyclerView ingredientsRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_ingredients);

        ingredientsRecyclerView.setLayoutManager(ingredientsLayoutManager);


        mIngredientsAdapter = new IngredientsAdapter(getContext(), RecipeDetailActivity.getmIngredients());

        ingredientsRecyclerView.setAdapter(mIngredientsAdapter);

        RecyclerView stepsRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_steps);

        stepsRecyclerView.setLayoutManager(stepsLayoutManager);


        mStepsAdapter = new StepAdapter(getActivity(), RecipeDetailActivity.getmSteps(),RecipeDetailActivity.getmImageUrls(),this);

        stepsRecyclerView.setAdapter(mStepsAdapter);

        // Return the root view
        return rootView;
    }

    public void refreshDataIngredients(String[] data) {
        mIngredientsAdapter.setmIngredients(data);
        mIngredientsAdapter.notifyDataSetChanged();
    }

    public void refreshDataSteps(String[] data) {
        mStepsAdapter.setmSteps(data);
        mStepsAdapter.notifyDataSetChanged();
    }

    public void refreshImagesSteps(String[] data) {
        mStepsAdapter.setmImageUrls(data);
        mStepsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(int stepPosition) {

        if (RecipeDetailActivity.ismTwoPane()) {
            StepInstructionFragment newInstructionFragment = new StepInstructionFragment();
            VideoFragment newVideoFragment = new VideoFragment();

            newInstructionFragment.setmStepInstructions(RecipeDetailActivity.getmSimpleJsonStepInstructions1());

            newInstructionFragment.setmIndex(stepPosition);

            FragmentManager fragmentManager = getFragmentManager();

            fragmentManager.beginTransaction()
                    .replace(R.id.instruction_container,newInstructionFragment)
                    .commit();

            newVideoFragment.setmStepVideos(RecipeDetailActivity.getmSimpleJsonStepVideoUrls1());

            newVideoFragment.setmIndex(stepPosition);

            fragmentManager.beginTransaction()
                    .replace(R.id.video_container,newVideoFragment)
                    .commit();

        } else {
            Context context = getContext();
            Class destinationClass = StepDetailActivity.class;
            Intent intentToStartStepDetailActivity = new Intent(context, destinationClass);
            intentToStartStepDetailActivity.putExtra(getString(R.string.step_postition), stepPosition);
            intentToStartStepDetailActivity.putExtra(getString(R.string.position), RecipeDetailActivity.getPosition());
            startActivity(intentToStartStepDetailActivity);
        }
    }
}