package com.example.android.smartchef;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Nikos on 6/11/2017.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeAdapterViewHolder> {

    /*
     * An on-click handler that we've defined to make it easy for an Activity to interface with
     * our RecyclerView
     */
    private final RecipeAdapterOnClickHandler mClickHandler;
    private String[] mRecipeNames;
    private String[] mRecipeImages;
    private Context mContext;

    /**
     * Creates a RecipeAdapter.
     *
     * @param clickHandler The on-click handler for this adapter. This single handler is called
     *                     when an item is clicked.
     */
    public RecipeAdapter(RecipeAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public String[] getmRecipeNames() {
        return mRecipeNames;
    }

    /**
     * This gets called when each new ViewHolder is created. This happens when the RecyclerView
     * is laid out. Enough ViewHolders will be created to fill the screen and allow for scrolling.
     *
     * @param viewGroup The ViewGroup that these ViewHolders are contained within.
     * @param viewType  If your RecyclerView has more than one type of item (which ours doesn't) you
     *                  can use this viewType integer to provide a different layout. See
     *                  {@link android.support.v7.widget.RecyclerView.Adapter#getItemViewType(int)}
     *                  for more details.
     * @return A new RecipeAdapterViewHolder that holds the View for each list item
     */

    @Override
    public RecipeAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.simple_recipe_layout;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new RecipeAdapterViewHolder(view);
    }



    @Override
    public void onBindViewHolder(RecipeAdapterViewHolder recipeAdapterViewHolder, int position) {

        String recipeName = mRecipeNames[position];

        if (mRecipeImages!=null){
            if (mRecipeImages[position]!=null && !mRecipeImages[position].isEmpty()){
                String recipeImage = mRecipeImages[position];
                Picasso.with(mContext).load(recipeImage).into(recipeAdapterViewHolder.mRecipeImage);
            }
        }
        recipeAdapterViewHolder.mRecipeName.setText(recipeName);

    }


    @Override
    public int getItemCount() {
        if (null == mRecipeNames) return 0;
        return mRecipeNames.length;
    }

    public void setRecipeNames(String[] recipeNames) {
        mRecipeNames = recipeNames;
        notifyDataSetChanged();
    }

    public void setRecipeImages(String[] recipeImages) {
        mRecipeImages = recipeImages;
        notifyDataSetChanged();
    }


    /**
     * The interface that receives onClick messages.
     */
    public interface RecipeAdapterOnClickHandler {
        void onClick(int position);
    }

    public String getItem(int position) {
        return mRecipeNames[position];
    }


    public class RecipeAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.recipe_name) TextView mRecipeName;
        @Bind(R.id.recipe_image) ImageView mRecipeImage;

        public RecipeAdapterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
            view.setOnClickListener(this);
        }

        /**
         * This gets called by the child views during a click.
         *
         * @param v The View that was clicked
         */
        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            mClickHandler.onClick(adapterPosition);
        }
    }

}