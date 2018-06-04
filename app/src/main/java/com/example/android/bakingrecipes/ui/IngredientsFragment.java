/*
 * This project is part of Android Developer Nanodegree Scholarship Program by
 * Udacity and Google
 * Created by Samuela Anastasi
 */
package com.example.android.bakingrecipes.ui;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingrecipes.R;
import com.example.android.bakingrecipes.data.Ingredient;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Ingredients {@link Fragment} subclass.
 * Use the {@link IngredientsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IngredientsFragment extends Fragment {
    // Fragment initialization parameter
    private static final String ARG_INGREDIENTS_LIST = "ingredientsList";

    private ArrayList<Ingredient> ingredientsList;

    @BindView(R.id.rv_ingredients_list)
    RecyclerView ingredientsRecycler;

    public IngredientsFragment() {
        // Required empty public constructor
    }

    /**
     * Factory method to create a new instance of
     * this fragment using the provided parameter.
     *
     * @param ingredientsList parameter.
     * @return A new instance of fragment IngredientsFragment.
     */
    public static IngredientsFragment newInstance(ArrayList<Ingredient> ingredientsList) {
        IngredientsFragment fragment = new IngredientsFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_INGREDIENTS_LIST, ingredientsList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState!= null) {
            ingredientsList = savedInstanceState.getParcelableArrayList(ARG_INGREDIENTS_LIST);
        } else {
            if (getArguments() != null) {
                ingredientsList = getArguments().getParcelableArrayList(ARG_INGREDIENTS_LIST);
            }
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ingredients, container, false);
        ButterKnife.bind(this, view);

        LinearLayoutManager ingredientsManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        ingredientsRecycler.setLayoutManager(ingredientsManager);
        ingredientsRecycler.setHasFixedSize(true);
        IngredientsAdapter ingredientsAdapter = new IngredientsAdapter(ingredientsList);
        ingredientsRecycler.setAdapter(ingredientsAdapter);
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(ARG_INGREDIENTS_LIST, ingredientsList);
    }
}
