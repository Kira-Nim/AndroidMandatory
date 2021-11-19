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

import com.example.android_mandatory.databinding.FragmentRegistrationBinding;

public class RegistrationFragment extends Fragment {

    // Used for getting access to db date which we will want to set an observer on.
    private MainViewModel mainViewModel;

    // Used to get the view for the RegistrationFragment
    private FragmentRegistrationBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        // Get an instance of mainViewModel in a way that ensures that this same instance,
        // with the same reference, can be retrieved again later.
        // It is used because we want to make sure that it is the same model we are working on
        // through out the system.
        /*
        My MainViewModel extends ViewModel which is a class in Java.
        ViewModelProvider is also a class in Android. Its purpose is to deliver a ViewModel instance

        The ViewModelProvider constructor takes one argument which is an "owner" a class.
        The "owner is used to identify what exact instance (reference) of the returned instance.
        The "owner" can be seen as an identifyer. It doesn't really matter what class it is
        the important thing is that the same instance of it is available where ever the
        specific instance of MainViewModel has to be available.

        (Minder lidt om den rolle som PersistenceContext har i JPA med Hibernate...)

        It means that if you write the exact same line of code twice, then the same instance with
        the same reference in memory would be returned.
         */
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);


        // Get an instance of FragmentRegistrationBinding.
        // This will be used to get the view for the RegistrationFragment.
        /*
            The line below means "get an instance of FragmentRegistrationBinding,
            by deserializing the xml file that FragmentRegistrationBinding is connected to, using a "LayoutInflater".
            The inflater instance is of type "LayoutInflater". Is is passed as an argument above.

            binding is an instance of FragmentRegistrationBinding. It contains the deserialized xml from "fragment_registration.xml"
         */
        binding = FragmentRegistrationBinding.inflate(inflater, container, false);

        // get view for the RegistrationFragment
        // called root because it is going to be root-view of RegistrationFragment.
        View root = binding.getRoot();

        // ."textRegistration" because this is is id of the textView in fragment_registration.xml file
        // (android:id="@+id/text_registration")
        final TextView textView = binding.textRegistration;

        /* Set observer on the db data
            getViewLifecycleOwner() is a method on Fragment which this class extends from.
            It returns an instance of LifecycleOwner which is a class that is used to keep track of where a given View is in its life-cycle.
            In this case the LifecycleOwner instance that is returned will be used to keep track of the rootView of this class.
            -
            The LifecycleOwner instance is parsed as param to the observer because it is needed to
            keep track of when the observation should stop (if the Fragment is garbage collected ex.)
         */
        mainViewModel.getData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}