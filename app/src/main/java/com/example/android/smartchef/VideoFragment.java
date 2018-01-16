package com.example.android.smartchef;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;



/**
 * Created by Nikos on 11/11/2017.
 */

public class VideoFragment extends Fragment {

    private String[] mStepVideos;
    private int mIndex;
    public static final String VIDEOS_KEY = "videos";
    private static final String INDEX_KEY = "index_key";
    private static final String VIDEO_PLAYBACK_KEY = "video_playback_key";
    private SimpleExoPlayer mExoPlayer;
    private long position;
    private SimpleExoPlayerView mPlayerView;

    public VideoFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState!=null){
            mStepVideos = savedInstanceState.getStringArray(VIDEOS_KEY);
            mIndex = savedInstanceState.getInt(INDEX_KEY);
        }

        View rootview = inflater.inflate(R.layout.fragment_video,container,false);
        mPlayerView = (SimpleExoPlayerView) rootview.findViewById(R.id.playerView);

        if (mStepVideos!=null){
            if(mStepVideos[mIndex]!=null){
                initializePlayer(Uri.parse(mStepVideos[mIndex]));
            }
            else{
                mPlayerView.setDefaultArtwork(BitmapFactory.decodeResource
                        (getContext().getResources(), R.drawable.no_video_available));            }
        } else {
            Log.e(getString(R.string.video_fragment) , getString(R.string.fragment_error));
        }
        return rootview;
    }



    public void setmStepVideos(String[] mStepVideos) {
        this.mStepVideos = mStepVideos;
    }

    public void setmIndex(int mIndex) {
        this.mIndex = mIndex;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putStringArray(VIDEOS_KEY, mStepVideos);
        outState.putInt(INDEX_KEY,mIndex);
        if (mExoPlayer != null) {
            outState.putLong(VIDEO_PLAYBACK_KEY, mExoPlayer.getCurrentPosition());
        }
    }

    @Override
    public void onPause() {
        if (mExoPlayer != null) {
            position = mExoPlayer.getCurrentPosition();
            mExoPlayer.release();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        if (mStepVideos!=null){
            if(mStepVideos[mIndex]!=null){
                initializePlayer(Uri.parse(mStepVideos[mIndex]));
                mExoPlayer.seekTo(position);
            }
            else{
                mPlayerView.setDefaultArtwork(BitmapFactory.decodeResource
                        (getContext().getResources(), R.drawable.no_video_available));            }
        } else {
            Log.e(getString(R.string.video_fragment) , getString(R.string.fragment_error));
        }
        super.onResume();
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState!=null && mExoPlayer != null){
            position = savedInstanceState.getLong(VIDEO_PLAYBACK_KEY);
        }
    }

    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);

            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getContext(), "SmartChef");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    /**
     * Release ExoPlayer.
     */
    private void releasePlayer() {
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }

    /**
     * Release the player when the activity is no longer visible.
     */
    @Override
    public void onStop() {
        super.onStop();
        if (mExoPlayer != null) {
            releasePlayer();
        }
    }


}
