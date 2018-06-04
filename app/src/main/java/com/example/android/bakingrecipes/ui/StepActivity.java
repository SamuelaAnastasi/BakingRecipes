/*
 * This project is part of Android Developer Nanodegree Scholarship Program by
 * Udacity and Google
 * Created by Samuela Anastasi
 */
package com.example.android.bakingrecipes.ui;

import android.content.Intent;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;

import com.example.android.bakingrecipes.R;
import com.example.android.bakingrecipes.data.Step;

import java.util.ArrayList;

import butterknife.BindString;
import butterknife.ButterKnife;

import static com.example.android.bakingrecipes.utilities.Utils.isPhoneLand;
import static com.example.android.bakingrecipes.utilities.Utils.isTabletLand;

public class StepActivity extends AppCompatActivity
        implements StepFragment.OnStepFragmentInteractionListener {

    private static final String KEY_CURRENT_RECIPE_STEPS = "currentRecipeSteps";
    private static final String KEY_CURRENT_STEP_ID = "currentStepId";
    private static final String KEY_CURRENT_RECIPE_NAME = "currentRecipeName";
    private static final String KEY_CURRENT_STEPS_QTY = "currentStepsQty";

    private ArrayList<Step> stepsList;
    private int currentStepId;
    private Step currentStep;
    private String currentRecipeName;
    private int stepsQty;
    private FragmentManager fragmentManager;
    private StepFragment stepFragment;

    @BindString(R.string.instructions_label)
    String stepActivityTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isTabletLand(this)) {
            finish();
        } else {
            setContentView(R.layout.activity_step);
            ButterKnife.bind(this);
            Toolbar toolbar = findViewById(R.id.tb_step_toolbar);
            setSupportActionBar(toolbar);

            fragmentManager = getSupportFragmentManager();

            if (savedInstanceState != null) {
                stepsList = savedInstanceState.getParcelableArrayList(KEY_CURRENT_RECIPE_STEPS);
                currentStepId = savedInstanceState.getInt(KEY_CURRENT_STEP_ID, currentStepId);
                currentRecipeName = savedInstanceState.getString(KEY_CURRENT_RECIPE_NAME,
                        currentRecipeName);
                stepsQty = savedInstanceState.getInt(KEY_CURRENT_STEPS_QTY, stepsQty);
                currentStep = stepsList.get(currentStepId - 1);
            } else {
                Intent intent = getIntent();
                if (intent != null) {
                    stepsList = intent.getParcelableArrayListExtra(KEY_CURRENT_RECIPE_STEPS);
                    currentStepId = intent.getIntExtra(KEY_CURRENT_STEP_ID, 0);
                    currentRecipeName = intent.getStringExtra(KEY_CURRENT_RECIPE_NAME);
                    currentStep = stepsList.get(currentStepId - 1);
                    stepsQty = stepsList.size();
            }
//
                stepFragment = StepFragment.newInstance(currentStep, stepsQty);
                fragmentManager.beginTransaction()
                        .replace(R.id.fl_step_container, stepFragment)
                        .commit();

            }
            if (isPhoneLand(this)) {
                setTheme(R.style.FullscreenTheme);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    Window w = getWindow();
                    w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

                }
            }
            ActionBar actionBar = getSupportActionBar();

            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setTitle(currentRecipeName + " " + stepActivityTitle);
            }
        }
    }

    @Override
    public void onStepFragmentInteraction(int stepId) {
        Step step = stepsList.get(stepId - 1);
        stepFragment = StepFragment.newInstance(step, stepsQty);
        fragmentManager.beginTransaction()
                .replace(R.id.fl_step_container, stepFragment)
                .commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(KEY_CURRENT_RECIPE_STEPS, stepsList);
        outState.putInt(KEY_CURRENT_STEP_ID, currentStepId);
        outState.putString(KEY_CURRENT_RECIPE_NAME, currentRecipeName);
        outState.putInt(KEY_CURRENT_STEPS_QTY, stepsQty);
    }
}
