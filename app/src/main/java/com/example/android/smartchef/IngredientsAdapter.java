package com.example.android.smartchef;

/**
 * Created by Nikos on 7/11/2017.
 */


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Nikos on 6/11/2017.
 */

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientsAdapterViewHolder> {

    private String[] mIngredients;
    private Context mContext;


    public IngredientsAdapter(Context context, String[] ingredients ) {
        mContext = context;
        mIngredients = ingredients;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public String[] getmIngredients() {
        return mIngredients;
    }


    @Override
    public IngredientsAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.simple_ingredient_layout;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup,shouldAttachToParentImmediately);
        return new IngredientsAdapterViewHolder(view);
    }



    @Override
    public void onBindViewHolder(IngredientsAdapterViewHolder ingredientsAdapterViewHolder, int position) {

        String ingredient = mIngredients[position];
        ingredientsAdapterViewHolder.mIngredient.setText(ingredient);

    }


    @Override
    public int getItemCount() {
        if (null == mIngredients) return 0;
        return mIngredients.length;
    }

    public void setmIngredients(String[] mIngredients) {
        this.mIngredients = mIngredients;
        notifyDataSetChanged();
    }





    public class IngredientsAdapterViewHolder extends RecyclerView.ViewHolder  {

        @Bind(R.id.ingredient) TextView mIngredient;

        public IngredientsAdapterViewHolder(View view) {
            super(view);

            ButterKnife.bind(this,view);
        }


    }

}