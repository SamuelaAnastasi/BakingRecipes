/*
 * This project is part of Android Developer Nanodegree Scholarship Program by
 * Udacity and Google
 * Created by Samuela Anastasi
 */
package com.example.android.bakingrecipes.widgets;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class RecipeWidgetViewsService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return (new RecipeWidgetViewsFactory(this.getApplicationContext(),
                intent));
    }
}
