package com.example.android.smartchef;

/**
 * Created by Nikos on 7/11/2017.
 */


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

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepAdapterViewHolder> {

    private String[] mSteps;
    private String[] mImageUrls;
    private Context mContext;
    private final StepAdapterOnClickHandler mClickHandler;


    public StepAdapter(Context context, String[] steps ,String[] imageUrls ,StepAdapterOnClickHandler clickHandler ) {
        mContext = context;
        mSteps = steps;
        mImageUrls = imageUrls;
        mClickHandler = clickHandler;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public String[] getmSteps() {
        return mSteps;
    }

    public interface StepAdapterOnClickHandler {
        void onClick(int position);
    }


    @Override
    public StepAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.simple_step_description_layout;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup,shouldAttachToParentImmediately);
        return new StepAdapterViewHolder(view);
    }



    @Override
    public void onBindViewHolder(StepAdapterViewHolder stepAdapterViewHolder, int position) {

        String step = mSteps[position];
        if (mImageUrls!=null){
            if (mImageUrls[position]!=null){
                String imageUrl = mImageUrls[position];
                Picasso.with(mContext).load(imageUrl).into(stepAdapterViewHolder.mImage);

            }
        }
        stepAdapterViewHolder.mStep.setText(step);
    }


    @Override
    public int getItemCount() {
        if (null == mSteps) return 0;
        return mSteps.length;
    }

    public void setmSteps(String[] mSteps) {
        this.mSteps = mSteps;
        notifyDataSetChanged();
    }

    public void setmImageUrls(String[] mImageUrls) {
        this.mImageUrls = mImageUrls;
        notifyDataSetChanged();
    }



    public class StepAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

        @Bind(R.id.step_description) TextView mStep;
        @Bind(R.id.step_image) ImageView mImage;

        public StepAdapterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
            view.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            mClickHandler.onClick(adapterPosition);
        }
    }

}