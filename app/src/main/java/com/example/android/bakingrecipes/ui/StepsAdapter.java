/*
 * This project is part of Android Developer Nanodegree Scholarship Program by
 * Udacity and Google
 * Created by Samuela Anastasi
 */
package com.example.android.bakingrecipes.ui;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingrecipes.R;
import com.example.android.bakingrecipes.data.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsViewHolder> {
    private List<Step> stepsList;
    private OnStepClickListener stepClickListener;

    private static int positionSelected;
    private static SparseBooleanArray selectedItemsArray;

    StepsAdapter(List<Step> stepsList, OnStepClickListener stepClickListener) {
        this.stepsList = stepsList;
        this.stepClickListener = stepClickListener;
        selectedItemsArray = new SparseBooleanArray();
    }

    public interface OnStepClickListener {
        void onStepClick(Step step, int position);
    }

    @NonNull
    @Override
    public StepsAdapter.StepsViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.step_list_item, parent, false);
        return new StepsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepsAdapter.StepsViewHolder holder, int position) {
        Step step = stepsList.get(position);
        holder.bindViews(step);
        holder.stepBackground.setSelected(selectedItemsArray.get(position, false));
    }

    @Override
    public int getItemCount() {
        return stepsList.size();
    }

    public class StepsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_stepNumber)
        TextView stepId;
        @BindView(R.id.tv_stepDescr)
        TextView stepDescription;
        @BindView(R.id.cl_step_item_root)
        ConstraintLayout stepBackground;

        StepsViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        private void bindViews(Step step) {
            stepId.setText(String.valueOf(step.getId()));
            stepDescription.setText(step.getShortDescription());
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            if (selectedItemsArray.get(adapterPosition, false)) {
                selectedItemsArray.delete(adapterPosition);
                stepBackground.setSelected(false);
            } else {
                selectedItemsArray.put(positionSelected, false);
                selectedItemsArray.put(adapterPosition, true);
                stepBackground.setSelected(true);
            }
            Step step = stepsList.get(getAdapterPosition());
            stepClickListener.onStepClick(step, adapterPosition);
        }
    }

    void selected(int position) {
        positionSelected = position;
        notifyDataSetChanged();
    }
}
