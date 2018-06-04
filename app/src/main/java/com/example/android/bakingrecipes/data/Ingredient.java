/*
 * This project is part of Android Developer Nanodegree Scholarship Program by
 * Udacity and Google
 * Created by Samuela Anastasi
 */
package com.example.android.bakingrecipes.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Ingredient implements Parcelable {

    private static final String TAG = Ingredient.class.getSimpleName();

    private float quantity;
    private String measure;
    private String ingredient;

    public Ingredient() {}

    protected Ingredient(Parcel in) {
        quantity = in.readFloat();
        measure = in.readString();
        ingredient = in.readString();
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    // getters
    public float getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    // setters
    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String toString() {
        return TAG + ": " + this.quantity + " " + this.measure + " " + this.ingredient;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeFloat(quantity);
        parcel.writeString(measure);
        parcel.writeString(ingredient);
    }

    // Testing purpose dummy object
    static Ingredient dummyObject() {
        Ingredient ingredient = new Ingredient();
        ingredient.setQuantity(2.f);
        ingredient.setMeasure("CUP");
        ingredient.setIngredient("Graham Cracker crumbs");
        return ingredient;
    }
}

