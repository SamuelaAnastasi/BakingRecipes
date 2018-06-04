/*
 * This project is part of Android Developer Nanodegree Scholarship Program by
 * Udacity and Google
 * Created by Samuela Anastasi
 */
package com.example.android.bakingrecipes.ui;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingrecipes.R;
import com.example.android.bakingrecipes.data.Recipe;
import com.example.android.bakingrecipes.widgets.RecipeWidgetProvider;
import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.android.bakingrecipes.utilities.Utils.isPhoneLand;
import static com.example.android.bakingrecipes.utilities.Utils.isTablet;

/**
 * Recipes {@link Fragment} subclass.
 * Use the {@link RecipesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipesFragment extends Fragment implements RecipesAdapter.OnRecipeClickListener {
    // Fragment initialization parameter
    private static final String ARG_RECIPE_LIST = "recipeListKey";
    private static final String KEY_CURRENT_RECIPE = "currentRecipe";
    private static final String KEY_DEFAULT_RECIPE = "defaultRecipe";
    private ArrayList<Recipe> recipeList;
    private Recipe defaultRecipe;

    @BindView(R.id.rv_recipes_list)
    RecyclerView recipeRecycler;

    public RecipesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameter.     *
     *
     * @param recipeList Parameter.
     * @return A new instance of fragment RecipesFragment.
     */
    public static RecipesFragment newInstance(ArrayList<Recipe> recipeList) {
        RecipesFragment fragment = new RecipesFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_RECIPE_LIST, recipeList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            recipeList = savedInstanceState.getParcelableArrayList(ARG_RECIPE_LIST);
            defaultRecipe = savedInstanceState.getParcelable(KEY_DEFAULT_RECIPE);
            // Save first element on shared prefs to initially populate widget
            if (recipeList != null) {
                defaultRecipe = recipeList.get(0);
                saveRecipeOnPrefs(defaultRecipe);
            }
        } else {
            if (getArguments() != null) {
                recipeList = getArguments().getParcelableArrayList(ARG_RECIPE_LIST);
                // Save first element on shared prefs to initially populate widget
                if (recipeList != null) {
                    defaultRecipe = recipeList.get(0);
                    saveRecipeOnPrefs(defaultRecipe);
                }
            }
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipes, container, false);

        ButterKnife.bind(this, view);

        if (isTablet(getActivity()) || isPhoneLand(getActivity())) {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
            recipeRecycler.setHasFixedSize(true);
            recipeRecycler.setLayoutManager(gridLayoutManager);
        } else {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),
                    LinearLayoutManager.VERTICAL, false);
            recipeRecycler.setHasFixedSize(true);
            recipeRecycler.setLayoutManager(linearLayoutManager);
        }

        RecipesAdapter recipesAdapter = new RecipesAdapter(recipeList, this);
        recipeRecycler.setAdapter(recipesAdapter);
        return view;
    }

    @Override
    public void onRecipeClick(Recipe recipe) {
        notifyPrefsToWidget(recipe);
        Intent intent = new Intent(getActivity(), IngredientsStepsActivity.class);
        intent.putExtra(KEY_CURRENT_RECIPE, recipe);
        startActivity(intent);
    }

    public void notifyPrefsToWidget(Recipe recipe) {
        saveRecipeOnPrefs(recipe);
        notifyWidget();
    }

    // Store current recipe in SharedPreferences
    private void saveRecipeOnPrefs(Recipe recipe) {
        Gson gson = new Gson();
        String recipeString = gson.toJson(recipe);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_CURRENT_RECIPE, recipeString);
        editor.apply();
    }

    // Notify the widget shared prefs data has changed
    private void notifyWidget() {
        if (getActivity() != null) {
            ComponentName widget = new ComponentName(getActivity().getApplication(),
                    RecipeWidgetProvider.class);
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getActivity());
            int[] ids = appWidgetManager.getAppWidgetIds(widget);
            appWidgetManager.notifyAppWidgetViewDataChanged(ids, R.id.widget_list);
            RecipeWidgetProvider widgetProvider = new RecipeWidgetProvider();
            widgetProvider.updateWidget(getActivity().getApplication(), ids);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(ARG_RECIPE_LIST, recipeList);
        outState.putParcelable(KEY_DEFAULT_RECIPE, defaultRecipe);
    }
}
