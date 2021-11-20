package com.example.android_mandatory.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_mandatory.Model.MainViewModel;
import com.example.android_mandatory.Model.Symptom;
import com.example.android_mandatory.databinding.FragmentSymptomsBinding;

import java.util.ArrayList;
import java.util.List;

public class SymptomsFragment extends Fragment {

    private MainViewModel mainViewModel;
    private FragmentSymptomsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        // Get instance of ViewModel so that the data for the view can be accessible.
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        // this instance will give access to the view hierarchy of this SymptomsFragment
        /* User the FragmentSymptomsBinding instance (class which Cradle created for us)
           which gives us access to the hierarchy of a given xml file.
           to get access to all views in SymptomFragment's View hierarchy.
        */
        binding = FragmentSymptomsBinding.inflate(inflater, container, false);

        // Get root-view
        View root = binding.getRoot();

        // Get recyclerView.
        /*
            Because this is defined in our xml file it can be accessed though the binder instance.

            Get viewHolders for each element seen on screen:
                The recyclerView will be used by the layout manager (below) to create enough viewHolder
                instances to fill on whole screen (pooling pattern is used aka recycling elements).
                The recyclerView will call a method on an instance of SymptomAdapter to do this.

            Reuse viewHolders when scrolling through elements - bind new data to viewHolders:
                The RecyclerView will also be used by the layout manager to requests binding of data
                to given viewHolders everytime a new element is to be shown in the ui.
                It binds the viewHolders to their data, by calling "onBindViewHolder" on the adapter.
                The recyclerView will call a method on an instance of SymptomAdapter to do this.
         */
        RecyclerView symptomRecyclerView = binding.symptomRecyclerView;

        // Temp data
        List<String> myList = new ArrayList<String>();
        myList.add("Hello");
        myList.add("world");
        myList.add("From");
        myList.add("Martin");
        myList.add("And");
        myList.add("Kira");


        // Create instance of Adapter
        /*
           that will be used by the SymptomRecycleView to create ViewHolders (incl views)
           for each element shown on screen.
           It will also be used to bind data to the views in the viewHolders when a given view
           element is going to be shown on screen.
         */
        SymptomAdapter symptomAdapter = new SymptomAdapter(myList);

        // Set an adapter on symptomRecyclerView
        /*
            The adapter will be used to create viewHolder elements corresponding to what there is room for on screen.
            The Adapter will be used by the recyclerview to associates data with the ViewHolder views.
         */
        symptomRecyclerView.setAdapter(symptomAdapter);

        // Create a LinearLayoutManager
        /*
            The items in your RecyclerView are arranged by the linearLayoutManager.
            linearLayoutManager hold information about how the table layout in the ui will be.
            It  handles the most common layout situations.
            -> arranges the items in a one-dimensional list.


            FragmentActivity parentActivity = getActivity();
            Method on Fragment (witch this class extends from).
            Will return an reference to the instance of a parent activity of which
            this fragment instance is child - Returns an instance of a FragmentActivity
         */
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

        // Set layout manager on the symptomRecyclerView
        symptomRecyclerView.setLayoutManager(linearLayoutManager);

        // set observer
        mainViewModel.getData().observe(getViewLifecycleOwner(), new Observer<ArrayList<Symptom>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Symptom> s) {

                // What happens when db model data changes here!
            }
        });
        return root;
    }

    // Run when the fragments lifecycle ends
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}