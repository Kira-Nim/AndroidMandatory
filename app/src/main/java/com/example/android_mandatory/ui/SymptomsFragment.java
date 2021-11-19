package com.example.android_mandatory.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.android_mandatory.databinding.FragmentSymptomsBinding;

public class SymptomsFragment extends Fragment {

    private MainViewModel mainViewModel;
    private FragmentSymptomsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        // Get instance of ViewModel so that the data for the view can be accessible.
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        // Get SymptomFragment's View hierarchy
        binding = FragmentSymptomsBinding.inflate(inflater, container, false);

        // Get root-view
        View root = binding.getRoot();

        // Get other view in view-hierarchy
        TextView textView = binding.textSymptom;

        // set observer
        mainViewModel.getData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
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