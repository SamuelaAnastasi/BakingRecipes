/*
 * This project is part of Android Developer Nanodegree Scholarship Program by
 * Udacity and Google
 * Created by Samuela Anastasi
 */
package com.example.android.bakingrecipes.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Recipe implements Parcelable {
    private static final String TAG = Recipe.class.getSimpleName();

    private int id;
    private String name;
    private int servings;
    private String image;
    private List<Ingredient> ingredients;
    private List<Step> steps;

    // constructor
    public Recipe() {
    }

    protected Recipe(Parcel in) {
        id = in.readInt();
        name = in.readString();
        servings = in.readInt();
        image = in.readString();
        ingredients = in.createTypedArrayList(Ingredient.CREATOR);
        steps = in.createTypedArrayList(Step.CREATOR);
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    // getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getServings() {
        return servings;
    }

    public String getImage() {
        return image;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    // setters
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public String toString() {
        return TAG + ": " + this.id + "\n" + this.name + "\n" + this.ingredients + "\n" + this.steps;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeInt(servings);
        parcel.writeString(image);
        parcel.writeTypedList(ingredients);
        parcel.writeTypedList(steps);
    }

    // Testing purpose dummy object
    public static Recipe dummyObject() {
        List<Step> stepLists = new ArrayList<>();
        stepLists.add(Step.dummyObject());
        stepLists.add(Step.dummyObject());
        stepLists.add(Step.dummyObject());
        stepLists.add(Step.dummyObject());

        List<Ingredient> ingredientsList = new ArrayList<>();
        ingredientsList.add(Ingredient.dummyObject());
        ingredientsList.add(Ingredient.dummyObject());
        ingredientsList.add(Ingredient.dummyObject());
        ingredientsList.add(Ingredient.dummyObject());

        Recipe recipe = new Recipe();
        recipe.setId(1);
        recipe.setName("Nutella Pie");
        recipe.setServings(8);
        recipe.setImage("");
        recipe.setIngredients(ingredientsList);
        recipe.setSteps(stepLists);

        return recipe;
    }
}

