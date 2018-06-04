/*
 * This project is part of Android Developer Nanodegree Scholarship Program by
 * Udacity and Google
 * Created by Samuela Anastasi
 */
package com.example.android.bakingrecipes.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingrecipes.R;
import com.example.android.bakingrecipes.data.Ingredient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientsAdapter extends
        RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder> {

    private List<Ingredient> ingredientsList;

    IngredientsAdapter(List<Ingredient> ingredientList) {
        this.ingredientsList = ingredientList;
    }

    @NonNull
    @Override
    public IngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ingredient_list_item, parent, false);
        return new IngredientsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsViewHolder holder, int position) {
        holder.bindViews(ingredientsList.get(position));
    }

    @Override
    public int getItemCount() {
        return ingredientsList.size();
    }

    class IngredientsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_quantity)
        TextView ingredientQty;
        @BindView(R.id.tv_measureUnit)
        TextView ingredientUnit;
        @BindView(R.id.tv_ingredientDescr)
        TextView ingredientDescr;

        IngredientsViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        private void bindViews(Ingredient ingredient) {
            ingredientQty.setText(String.valueOf(ingredient.getQuantity()));
            ingredientUnit.setText(ingredient.getMeasure());
            ingredientDescr.setText(ingredient.getIngredient());
        }
    }
}
