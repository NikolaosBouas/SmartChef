package com.example.android.smartchef;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Nikos on 11/11/2017.
 */

public class StepInstructionFragment extends Fragment {

    private String[] mStepInstructions;
    private int mIndex;

    public static final String INSTRUCTIONS_KEY = "instructions";
    private static final String INDEX_KEY = "index_key";

    public StepInstructionFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState!=null){
            mStepInstructions = savedInstanceState.getStringArray(INSTRUCTIONS_KEY);
            mIndex = savedInstanceState.getInt(INDEX_KEY);
        }

        View rootview = inflater.inflate(R.layout.frament_step_instruction,container,false);
        TextView textView = (TextView) rootview.findViewById(R.id.step_instruction_textview);
        if (mStepInstructions!=null){
            textView.setText(mStepInstructions[mIndex]);
        } else {
            Log.e(getString(R.string.instruction_fragment) , getString(R.string.fragment_error));
        }
        return rootview;
    }

    public void setmStepInstructions(String[] mStepInstructions) {
        this.mStepInstructions = mStepInstructions;
    }

    public void setmIndex(int mIndex) {
        this.mIndex = mIndex;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putStringArray(INSTRUCTIONS_KEY, mStepInstructions);
        outState.putInt(INDEX_KEY,mIndex);
    }
}
