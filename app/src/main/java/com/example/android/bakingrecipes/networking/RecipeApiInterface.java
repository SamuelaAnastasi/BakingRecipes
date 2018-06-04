/*
 * This project is part of Android Developer Nanodegree Scholarship Program by
 * Udacity and Google
 * Created by Samuela Anastasi
 */
package com.example.android.bakingrecipes.networking;

import com.example.android.bakingrecipes.data.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipeApiInterface {
    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<List<Recipe>> fetchRecipes();
}
