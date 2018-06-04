/*
 * This project is part of Android Developer Nanodegree Scholarship Program by
 * Udacity and Google
 * Created by Samuela Anastasi
 */
package com.example.android.bakingrecipes.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingrecipes.R;
import com.example.android.bakingrecipes.data.Step;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Steps {@link Fragment} subclass.
 * Use the {@link StepsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StepsFragment extends Fragment implements StepsAdapter.OnStepClickListener {
    // Fragment initialization parameter
    private static final String ARG_STEPS_LIST = "stepsList";
    private ArrayList<Step> stepsList;
    private StepsAdapter stepsAdapter;
    private LinearLayoutManager layoutManager;

    @BindView(R.id.rv_steps_list)
    RecyclerView stepsRecycler;

    private OnStepsFragmentInteractionListener mListener;

    public StepsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param steps Parameter.
     * @return A new instance of fragment StepsFragment.
     */
    public static StepsFragment newInstance(ArrayList<Step> steps) {
        StepsFragment fragment = new StepsFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_STEPS_LIST, steps);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            stepsList = savedInstanceState.getParcelableArrayList(ARG_STEPS_LIST);

        } else {
            if (getArguments() != null) {
                stepsList = getArguments().getParcelableArrayList(ARG_STEPS_LIST);
            }
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_steps, container, false);

        ButterKnife.bind(this, view);
        layoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        stepsRecycler.setHasFixedSize(true);
        stepsRecycler.setLayoutManager(layoutManager);
        stepsAdapter = new StepsAdapter(stepsList, this);
        stepsRecycler.setAdapter(stepsAdapter);
        return view;
    }

    @Override
    public void onStepClick(Step step, int position) {
        int stepId = step.getId();
        stepsAdapter.selected(position);
        mListener.onStepsFragmentInteraction(stepId );
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnStepsFragmentInteractionListener) {
            mListener = (OnStepsFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnStepFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnStepsFragmentInteractionListener {
        void onStepsFragmentInteraction(int stepId);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(ARG_STEPS_LIST, stepsList);
    }
}
