/*
 * This project is part of Android Developer Nanodegree Scholarship Program by
 * Udacity and Google
 * Created by Samuela Anastasi
 */
package com.example.android.bakingrecipes.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingrecipes.R;
import com.example.android.bakingrecipes.data.Step;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.android.bakingrecipes.utilities.Constants.ERROR_SHAPE;
import static com.example.android.bakingrecipes.utilities.Constants.NO_VIDEO_RES;
import static com.example.android.bakingrecipes.utilities.Constants.PLACEHOLDER_SHAPE;
import static com.example.android.bakingrecipes.utilities.Utils.isImageType;
import static com.example.android.bakingrecipes.utilities.Utils.isTabletLand;
import static com.example.android.bakingrecipes.utilities.Utils.isVideoType;
import static com.example.android.bakingrecipes.utilities.Utils.takeOffStartingDigits;

/**
 * Step {@link Fragment} subclass.
 * Use the {@link StepFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StepFragment extends Fragment {
    // Fragment initialization parameter
    private static final String ARG_CURRENT_STEP = "currentStep";
    private static final String ARG_STEPS_TOTAL = "stepsTotal";

    private Step step;
    private int stepQty;
    int currentStepId;

    public static final String VIDEO_PLAYBACK_POSITION = "playbackPosition";
    public static final String PLAY_STATUS_READY = "playStatusReady";
    private SimpleExoPlayer exoPlayer;
    private long playbackPosition;
    private boolean playStatusReady;

    private String videoURL;
    private String thumbnailUrl;
    private String videoUrl;

    private OnStepFragmentInteractionListener stepFragmentInteractionListener;

    @BindView(R.id.sepv_exoplayerView)
    PlayerView playerView;

    @BindView(R.id.ib_previous_video)
    ImageButton previousButton;

    @BindView(R.id.ib_next_video)
    ImageButton nextButton;

    @BindView(R.id.tv_step_longDescr)
    TextView descriptionView;

    @BindView(R.id.iv_video_thumbnail)
    ImageView thumbnailImageView;

    @BindView(R.id.tv_stepId)
    TextView stepIdView;

    @BindString(R.string.step_string_format)
    String stepStringFormat;

    public StepFragment() {
        // Required empty public constructor
    }

    /**
     * Factory method to create a new instance of
     * this fragment using the provided parameters.     *
     *
     * @param step     Parameter.
     * @param stepsQty Parameter
     * @return A new instance of fragment StepFragment.
     */
    public static StepFragment newInstance(Step step, int stepsQty) {
        StepFragment fragment = new StepFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_CURRENT_STEP, step);
        args.putInt(ARG_STEPS_TOTAL, stepsQty);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            step = savedInstanceState.getParcelable(ARG_CURRENT_STEP);
            stepQty = savedInstanceState.getInt(ARG_STEPS_TOTAL);
            currentStepId = step.getId();
            thumbnailUrl = step.getThumbnailURL();
            videoUrl = step.getVideoURL();

        } else {
            if (getArguments() != null) {
                step = getArguments().getParcelable(ARG_CURRENT_STEP);
                if (step != null) {
                    currentStepId = step.getId();
                }

                stepQty = getArguments().getInt(ARG_STEPS_TOTAL);
                thumbnailUrl = step.getThumbnailURL();
                videoUrl = step.getVideoURL();
            }
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_step, container, false);
        ButterKnife.bind(this, view);

        if (isTabletLand(getActivity())) {
            previousButton.setVisibility(View.INVISIBLE);
            nextButton.setVisibility(View.INVISIBLE);
        }

        if (currentStepId == 1) {
            previousButton.setVisibility(View.INVISIBLE);
        } else if (currentStepId == stepQty) {
            nextButton.setVisibility(View.INVISIBLE);
        }

        String stepDescription = step.getDescription();
        descriptionView.setText(takeOffStartingDigits(stepDescription));

        stepIdView.setText(String.format(stepStringFormat,
                step.getId(), stepQty));

        if (savedInstanceState != null) {
            playbackPosition = savedInstanceState.getLong(VIDEO_PLAYBACK_POSITION);
            playStatusReady = savedInstanceState.getBoolean(PLAY_STATUS_READY);
        }

        videoURL = step.getVideoURL();
        thumbnailUrl = step.getThumbnailURL();

        if (!TextUtils.isEmpty(videoUrl)) {
            if (isVideoType(thumbnailUrl)) {
                playerView.setVisibility(View.VISIBLE);
                thumbnailImageView.setVisibility(View.GONE);
                initExoPlayer(Uri.parse(videoURL));

            }
        } else {
            releaseExoPlayer();
            thumbnailImageView.setVisibility(View.VISIBLE);
            playerView.setVisibility(View.GONE);

            if (TextUtils.isEmpty(thumbnailUrl) || !isImageType(thumbnailUrl)) {

                Picasso.get()
                        .load(NO_VIDEO_RES)
                        .placeholder(PLACEHOLDER_SHAPE)
                        .error(ERROR_SHAPE)
                        .into(thumbnailImageView);
            } else {
                Picasso.get()
                        .load(thumbnailUrl)
                        .placeholder(PLACEHOLDER_SHAPE)
                        .error(ERROR_SHAPE)
                        .into(thumbnailImageView);
            }
        }

        return view;
    }

    @OnClick(R.id.ib_previous_video)
    protected void loadPreviousVideo () {
        if (currentStepId == 0) {
            return;
        }
        releaseExoPlayer();
        currentStepId -= 1;
        if (stepFragmentInteractionListener != null) {
            stepFragmentInteractionListener.onStepFragmentInteraction(currentStepId);
        }
    }

    @OnClick(R.id.ib_next_video)
    protected void loadNextVideo () {
        if (currentStepId == stepQty) {
            return;
        }

        releaseExoPlayer();
        currentStepId += 1;
        if (stepFragmentInteractionListener != null) {
            stepFragmentInteractionListener.onStepFragmentInteraction(currentStepId);
        }
    }

    private void initExoPlayer(Uri uri) {
        exoPlayer = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(getContext()),
                new DefaultTrackSelector(), new DefaultLoadControl());
        MediaSource mediaSource = buildMediaSource(uri);
        playerView.setPlayer(exoPlayer);
        exoPlayer.prepare(mediaSource, true, false);
        exoPlayer.setPlayWhenReady(playStatusReady);
        exoPlayer.seekTo(playbackPosition);

    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory(getString(R.string.app_name)))
                .createMediaSource(uri);
    }

    private void releaseExoPlayer() {
        if (exoPlayer != null) {
            playbackPosition = exoPlayer.getCurrentPosition();
            playStatusReady = exoPlayer.getPlayWhenReady();
            exoPlayer.stop();
            exoPlayer.release();
            exoPlayer = null;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initExoPlayer(Uri.parse(videoURL));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23 || exoPlayer == null)) {
            initExoPlayer(Uri.parse(videoURL));
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releaseExoPlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releaseExoPlayer();
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        releaseExoPlayer();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(ARG_CURRENT_STEP, step);
        outState.putInt(ARG_STEPS_TOTAL, stepQty);
        outState.putLong(VIDEO_PLAYBACK_POSITION, playbackPosition);
        outState.putBoolean(PLAY_STATUS_READY, playStatusReady);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnStepFragmentInteractionListener) {
            stepFragmentInteractionListener = (OnStepFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnStepFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        stepFragmentInteractionListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity
     */
    public interface OnStepFragmentInteractionListener {
        void onStepFragmentInteraction(int stepId);
    }
}
