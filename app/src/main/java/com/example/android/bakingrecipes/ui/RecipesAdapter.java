/*
 * This project is part of Android Developer Nanodegree Scholarship Program by
 * Udacity and Google
 * Created by Samuela Anastasi
 */
package com.example.android.bakingrecipes.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingrecipes.R;
import com.example.android.bakingrecipes.data.Recipe;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.android.bakingrecipes.utilities.Constants.ERROR_SHAPE;
import static com.example.android.bakingrecipes.utilities.Constants.PLACEHOLDER_SHAPE;
import static com.example.android.bakingrecipes.utilities.Utils.getDrawableRes;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipesViewHolder> {
    private List<Recipe> recipeList;
    private OnRecipeClickListener recipeClickListener;

    RecipesAdapter(List<Recipe> recipeList, OnRecipeClickListener recipeClickListener) {
        this.recipeList = recipeList;
        this.recipeClickListener = recipeClickListener;
    }

    public interface OnRecipeClickListener {
        void onRecipeClick(Recipe recipe);
    }

    @NonNull
    @Override
    public RecipesAdapter.RecipesViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_list_item, parent, false);
        return new RecipesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipesAdapter.RecipesViewHolder holder, int position) {
        holder.bindViews(recipeList.get(position));
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public class RecipesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_servingsQty)
        TextView servingsQty;
        @BindView(R.id.tv_stepsQty)
        TextView stepsQty;
        @BindView(R.id.tv_ingredientsQty)
        TextView ingredientsQty;
        @BindView(R.id.tv_recipeName)
        TextView recipeNameView;
        @BindView(R.id.iv_recipeImage)
        ImageView recipeImage;
        int imgRes;

        RecipesViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        private void bindViews(Recipe currentRecipe) {
            servingsQty.setText(String.valueOf(currentRecipe.getServings()));
            stepsQty.setText(String.valueOf(currentRecipe.getSteps().size()));
            ingredientsQty.setText(String.valueOf(currentRecipe.getIngredients().size()));
            String recipeName = currentRecipe.getName();
            recipeNameView.setText(recipeName);
            String urlImage = currentRecipe.getImage();
            if (!(TextUtils.isEmpty(urlImage))) {
                Picasso.get()
                        .load(urlImage)
                        .placeholder(PLACEHOLDER_SHAPE)
                        .error(ERROR_SHAPE)
                        .into(recipeImage);
            } else {
                imgRes = getDrawableRes(recipeName);
                Picasso.get()
                        .load(imgRes)
                        .placeholder(PLACEHOLDER_SHAPE)
                        .error(ERROR_SHAPE)
                        .into(recipeImage);
            }
        }

        @Override
        public void onClick(View view) {
            Recipe recipe = recipeList.get(getAdapterPosition());
            recipeClickListener.onRecipeClick(recipe);
        }
    }
}
