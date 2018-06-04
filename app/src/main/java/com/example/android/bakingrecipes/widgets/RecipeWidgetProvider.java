/*
 * This project is part of Android Developer Nanodegree Scholarship Program by
 * Udacity and Google
 * Created by Samuela Anastasi
 */
package com.example.android.bakingrecipes.widgets;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;

import com.example.android.bakingrecipes.R;
import com.example.android.bakingrecipes.data.Recipe;
import com.google.gson.Gson;

import static com.example.android.bakingrecipes.utilities.Utils.getDrawableRes;

public class RecipeWidgetProvider extends AppWidgetProvider {
    private static final String LOG_TAG = RecipeWidgetProvider.class.getSimpleName();
    private static final String KEY_CURRENT_RECIPE = "currentRecipe";
    private int[] appWidgetIds;
    private Recipe recipe;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        this.appWidgetIds = appWidgetIds;
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(context.getPackageName(),
                    R.layout.recipe_widget);

            // Update views on widget to match recipe saved on sharedPrefs
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            Gson gson = new Gson();
            String recipeString = preferences.getString(KEY_CURRENT_RECIPE, null);
            if (recipeString != null) {
                recipe = gson.fromJson(recipeString, Recipe.class);
                String name = recipe.getName();
                int imgRes = getDrawableRes(name);
                views.setImageViewResource(R.id.widget_img_launcher, imgRes);

                views.setTextViewText(R.id.recipe_name, name);
                views.setTextColor(R.id.recipe_name, Color.WHITE);
            }

            Intent serviceIntent = new Intent(context, RecipeWidgetViewsService.class);
            serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

            views.setRemoteAdapter(R.id.widget_list, serviceIntent);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (appWidgetIds != null) {
            onUpdate(context, AppWidgetManager.getInstance(context), appWidgetIds);
        }
        super.onReceive(context, intent);
    }

    public void updateWidget(Context context, int[] ids) {
        onUpdate(context, AppWidgetManager.getInstance(context), ids);
    }
}

