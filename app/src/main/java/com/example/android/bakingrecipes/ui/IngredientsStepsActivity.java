/*
 * This project is part of Android Developer Nanodegree Scholarship Program by
 * Udacity and Google
 * Created by Samuela Anastasi
 */
package com.example.android.bakingrecipes.ui;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingrecipes.R;
import com.example.android.bakingrecipes.data.Ingredient;
import com.example.android.bakingrecipes.data.Recipe;
import com.example.android.bakingrecipes.data.Step;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.android.bakingrecipes.utilities.Constants.ERROR_SHAPE;
import static com.example.android.bakingrecipes.utilities.Constants.PLACEHOLDER_SHAPE;
import static com.example.android.bakingrecipes.utilities.Utils.getDrawableRes;
import static com.example.android.bakingrecipes.utilities.Utils.isTabletLand;

public class IngredientsStepsActivity extends AppCompatActivity
        implements StepsFragment.OnStepsFragmentInteractionListener,
        StepFragment.OnStepFragmentInteractionListener {

    public static final String KEY_CURRENT_RECIPE = "currentRecipe";
    private static final String KEY_CURRENT_RECIPE_STEPS = "currentRecipeSteps";
    private static final String KEY_CURRENT_STEP_ID = "currentStepId";
    private static final String KEY_CURRENT_RECIPE_NAME = "currentRecipeName";

    private static final String INGREDIENTS_FRAGMENT_TITLE = "INGREDIENTS";
    private static final String STEPS_FRAGMENT_TITLE = "STEPS";

    boolean[] isButtonClicked = new boolean[] {false, false};

    private Recipe currentRecipe;
    private String currentRecipeName;
    private ArrayList<Ingredient> ingredientsList;
    private ArrayList<Step> stepList;
    private Step step;
    int currentStepId;
    int stepListSize;
    private FragmentManager fragmentManager;
    IngredientsFragment ingredientsFragment;
    StepsFragment stepsFragment;

    @BindView(R.id.iv_toolbar_image)
    ImageView toolbarImageView;
    @BindView(R.id.tb_ingredients_steps_toolbar)
    Toolbar toolbar;
    @BindView(R.id.tl_ingredients_steps_tabs)
    TabLayout tabLayout;
    @BindView(R.id.ctb_ingredients_steps)
    CollapsingToolbarLayout collapsingToolbarLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients_steps);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        viewPager = findViewById(R.id.vp_ingredients_steps_pager);

        Intent intent = getIntent();
        if (intent != null) {
            currentRecipe = intent.getParcelableExtra(KEY_CURRENT_RECIPE);
            ingredientsList = (ArrayList<Ingredient>) currentRecipe.getIngredients();
            stepList = (ArrayList<Step>) currentRecipe.getSteps();
            stepListSize = stepList.size();
            currentRecipeName = currentRecipe.getName();
            collapsingToolbarLayout.setTitle(currentRecipeName);
            String urlImage = currentRecipe.getImage();

            int imgRes;
            if (!(TextUtils.isEmpty(urlImage))) {
                Picasso.get()
                        .load(urlImage)
                        .placeholder(PLACEHOLDER_SHAPE)
                        .error(ERROR_SHAPE)
                        .into(toolbarImageView);
            } else {

                imgRes = getDrawableRes(currentRecipeName);
                Picasso.get()
                        .load(imgRes)
                        .placeholder(PLACEHOLDER_SHAPE)
                        .error(ERROR_SHAPE)
                        .into(toolbarImageView);
            }
            if (isTabletLand(this)) {
                final TextView ingredientsButton = findViewById(R.id.tv_ingredient_Label);
                final TextView stepsButton = findViewById(R.id.tv_step_Label);
                if (!isButtonClicked[1]) {
                    stepsButton.setTextColor(getResources().getColor(R.color.colorAccent));
                    ingredientsButton.setTextColor(getResources().getColor(R.color.colorTextPrimary));
                    isButtonClicked[0] = false;
                    isButtonClicked[1] = true;
                }

                fragmentManager = getSupportFragmentManager();
                final IngredientsFragment ingredientsFragment =
                        IngredientsFragment.newInstance(ingredientsList);
                fragmentManager.beginTransaction()
                        .replace(R.id.fl_ingredients_steps_container, ingredientsFragment)
                        .commit();

                final StepsFragment stepsFragment = StepsFragment.newInstance(stepList);
                fragmentManager.beginTransaction()
                        .replace(R.id.fl_ingredients_steps_container, stepsFragment)
                        .commit();

                StepFragment stepFragment = StepFragment.newInstance(stepList.get(0), stepListSize);
                fragmentManager.beginTransaction()
                        .replace(R.id.fl_step_detail_container, stepFragment).commit();

                ingredientsButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!isButtonClicked[0]) {
                            ingredientsButton.setTextColor(getResources().getColor(R.color.colorAccent));
                            stepsButton.setTextColor(getResources().getColor(R.color.colorTextPrimary));
                            isButtonClicked[1] = false;
                            isButtonClicked[0] = true;
                        }

                       if (ingredientsFragment == null) {
                           final IngredientsFragment ingredientsFragment = IngredientsFragment.newInstance(ingredientsList);
                           fragmentManager.beginTransaction().add(R.id.fl_ingredients_steps_container, ingredientsFragment).commit();
                       } else {
                           fragmentManager.beginTransaction().replace(R.id.fl_ingredients_steps_container, ingredientsFragment).commit();
                       }
                    }
                });

                stepsButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!isButtonClicked[1]) {
                            stepsButton.setTextColor(getResources().getColor(R.color.colorAccent));
                            ingredientsButton.setTextColor(getResources().getColor(R.color.colorTextPrimary));
                            isButtonClicked[0] = false;
                            isButtonClicked[1] = true;
                        }
                        if (stepsFragment == null) {
                            final StepsFragment stepsFragment = StepsFragment.newInstance(stepList);
                            fragmentManager.beginTransaction().add(R.id.fl_ingredients_steps_container, stepsFragment).commit();
                        } else {
                            fragmentManager.beginTransaction().replace(R.id.fl_ingredients_steps_container, stepsFragment).commit();
                        }
                    }
                });

            } else {
                final TextView labelView = findViewById(R.id.tv_viewLabel);
                labelView.setText(R.string.directions_label);

                setupViewPager(viewPager);
                tabLayout.setupWithViewPager(viewPager);
                tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        int tabId = tab.getPosition();
                        switch (tabId) {
                            case 0:
                                labelView.setText(R.string.directions_label);
                                break;
                            case 1:
                                labelView.setText(R.string.ingredients_label);
                                break;
                        }
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {

                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {

                    }
                });
            }
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        fragmentManager = getSupportFragmentManager();
        ViewPagerAdapter adapter = new ViewPagerAdapter(fragmentManager);
        if (ingredientsFragment == null) {
            ingredientsFragment = IngredientsFragment.newInstance(ingredientsList);
        }
        if (stepsFragment == null) {
            stepsFragment = StepsFragment.newInstance(stepList);
        }
        adapter.addFragment(stepsFragment, STEPS_FRAGMENT_TITLE);
        adapter.addFragment(ingredientsFragment, INGREDIENTS_FRAGMENT_TITLE);
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onStepsFragmentInteraction(int stepId) {
        if (isTabletLand(this)) {
            currentStepId = stepId-1;
            step = stepList.get(stepId-1);
            fragmentManager = getSupportFragmentManager();
            StepFragment stepFragment = StepFragment.newInstance(step, stepListSize);
            fragmentManager.beginTransaction()
                    .replace(R.id.fl_step_detail_container, stepFragment)
                    .commit();

        } else {
            Intent intent = new Intent(IngredientsStepsActivity.this,
                    StepActivity.class);
            intent.putExtra(KEY_CURRENT_RECIPE_NAME, currentRecipeName);
            intent.putExtra(KEY_CURRENT_STEP_ID, stepId);
            intent.putParcelableArrayListExtra(KEY_CURRENT_RECIPE_STEPS, stepList);
            startActivity(intent);
        }
    }

    @Override
    public void onStepFragmentInteraction(int stepId) {
        if (isTabletLand(this)) {
            Step step = stepList.get(stepId - 1);
            StepFragment stepFragment = StepFragment.newInstance(step, stepListSize);
            fragmentManager.beginTransaction()
                    .replace(R.id.fl_step_detail_container, stepFragment)
                    .commit();
        }
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> pagerFragmentList = new ArrayList<>();
        private final List<String> pagerFragmentTitleList = new ArrayList<>();

        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return pagerFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return pagerFragmentList.size();
        }

        void addFragment(Fragment fragment, String title) {
            pagerFragmentList.add(fragment);
            pagerFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return pagerFragmentTitleList.get(position);
        }
    }
}
