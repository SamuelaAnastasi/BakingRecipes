/*
 * This project is part of Android Developer Nanodegree Scholarship Program by
 * Udacity and Google
 * Created by Samuela Anastasi
 */
package com.example.android.bakingrecipes.ui;

import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.example.android.bakingrecipes.R;
import com.example.android.bakingrecipes.data.Recipe;
import com.example.android.bakingrecipes.data.Step;
import com.example.android.bakingrecipes.networking.RecipeApiInterface;
import com.example.android.bakingrecipes.networking.RecipeApiClientGenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private static final String ARG_RECIPE_LIST = "recipeListKey";
    private ArrayList<Recipe> recipeList = new ArrayList<>();
    private List<Step> stepList;
    private RecipesFragment recipesFragment;
    private FragmentManager fragmentManager;

    // Bind views
    @BindView(R.id.tb_main_toolbar)
    Toolbar toolbar;
    @BindView(R.id.no_internet)
    ConstraintLayout noInternetLayout;
    @BindView(R.id.ll_recipes_container)
    LinearLayout recipesContainer;

    @BindString(R.string.main_activity_title)
    String mainActivityTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        fragmentManager = getSupportFragmentManager();

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(mainActivityTitle);
        }
        if (savedInstanceState != null) {
            recipeList = savedInstanceState.getParcelableArrayList(ARG_RECIPE_LIST);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (recipeList != null && recipeList.size() > 0) {
            if (recipesFragment == null) {
                recipesFragment = RecipesFragment.newInstance(recipeList);
                fragmentManager.beginTransaction()
                        .add(R.id.ll_recipes_container, recipesFragment).commit();
            } else {
                fragmentManager.beginTransaction()
                        .replace(R.id.ll_recipes_container, recipesFragment).commit();
            }
        } else {
            final RecipeApiInterface recipeApiInterface =
                    RecipeApiClientGenerator.createService(RecipeApiInterface.class);
            Call<List<Recipe>> call = recipeApiInterface.fetchRecipes();
            call.enqueue(new Callback<List<Recipe>>() {
                @Override
                public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        recipeList.clear();
                        for (Object object : response.body()) {
                            if (object instanceof Recipe) {
                                recipeList.add((Recipe) object);
                                for (Recipe recipe : recipeList) {
                                    stepList = recipe.getSteps();
                                    if (stepList != null) {
                                        for (int i = 0; i < stepList.size(); i++) {
                                            stepList.get(i).setId(i + 1);
                                        }
                                    }

                                }
                            }
                        }
                        if (recipesFragment == null) {
                            recipesFragment = RecipesFragment.newInstance(recipeList);
                            fragmentManager.beginTransaction()
                                    .add(R.id.ll_recipes_container, recipesFragment).commit();
                        } else {
                            fragmentManager.beginTransaction()
                                    .replace(R.id.ll_recipes_container, recipesFragment).commit();
                        }

                    } else {
                        Toast.makeText(MainActivity.this,
                                R.string.error_data_load,
                                Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Recipe>> call, Throwable t) {
                    if (t instanceof IOException) {
                        recipesContainer.setVisibility(View.GONE);
                        noInternetLayout.setVisibility(View.VISIBLE);
                    } else {
                        Log.d(LOG_TAG, "error is: ", t);
                        Toast.makeText(MainActivity.this,
                                R.string.error_data_conversion,
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(ARG_RECIPE_LIST, recipeList);
    }
}
