/*
 * This project is part of Android Developer Nanodegree Scholarship Program by
 * Udacity and Google
 * Created by Samuela Anastasi
 */
package com.example.android.bakingrecipes.widgets;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.bakingrecipes.R;
import com.example.android.bakingrecipes.data.Ingredient;
import com.example.android.bakingrecipes.data.Recipe;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class RecipeWidgetViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private static final String LOG_TAG = RecipeWidgetViewsFactory.class.getSimpleName();
    private static final String KEY_CURRENT_RECIPE = "currentRecipe";
    private Recipe recipe;
    private List<Ingredient> ingredientList = new ArrayList<>();
    private Context context;
    private int appWidgetId;
    private Intent intent;


    // Constructor
    public RecipeWidgetViewsFactory(Context context, Intent intent) {
        this.context = context;
        this.intent = intent;
        appWidgetId = this.intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String recipeString = preferences.getString(KEY_CURRENT_RECIPE, null);
        if (recipeString != null) {
            recipe = gson.fromJson(recipeString, Recipe.class);
            ingredientList = recipe.getIngredients();
        }
    }

    @Override
    public void onDestroy() {
        ingredientList.clear();
    }

    @Override
    public int getCount() {
        if (ingredientList == null) {
            return 0;
        } else {
            return ingredientList.size();
        }
    }

    @Override
    public RemoteViews getViewAt(int i) {
        final RemoteViews views = new RemoteViews(
                context.getPackageName(), R.layout.recipe_widget_list_item);

        // Get the Ingredient at the selected position
        Ingredient ingredient = ingredientList.get(i);
        views.setTextViewText(R.id.tv_ingredient_qty, String.valueOf(ingredient.getQuantity()));
        views.setTextViewText(R.id.tv_ingredient_unit, ingredient.getMeasure());
        views.setTextViewText(R.id.tv_ingredient_descr, ingredient.getIngredient());
        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
