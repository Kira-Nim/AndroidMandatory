package com.example.android_mandatory.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class SymptomAdapter extends RecyclerView.Adapter<SymptomAdapter.ViewHolder>  {

    private String[] localDataSet;


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView textView;

        // Constructor takes view
        public ViewHolder(View view) {
            super(view);

            textView = (TextView) view.findViewById(R.id.symptomRowTextView);
        }

        // Normal getter
        public TextView getTextView() {
            return textView;
        }
    }

    // Constructor for our (outer) class that takes the model data to be shown as param.
    public SymptomAdapter(String[] dataSet) {

        // Data from db to be shown in table
        localDataSet = dataSet;
    }

    // Create new ViewHolders incl. views (as many as can be shown on screen) - invoked by the layout manager
    // The viewGroup param is in this case an instance of our RecyclerView for the SymptomFragment.
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.symptom_row_item, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (in a viewHolder) - invoked by the layout manager
    // called everytime a viewHolder is reused and there for needs new data
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getTextView().setText(localDataSet[position]);
    }

    // Return the size of your dataset (how many items in table)  - invoked by the layout manager
    @Override
    public int getItemCount() {
        return localDataSet.length;
    }

}
