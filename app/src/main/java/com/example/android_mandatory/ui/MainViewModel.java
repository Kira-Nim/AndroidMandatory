package com.example.android_mandatory.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

// This class is used as a repository class / gateway to model.
public class MainViewModel extends ViewModel {

    // This MutableLiveData wrapper class makes it possible to observe data and react to changes to it.
    /*
        It will be possible to set "observers", that are connected to this instance, other places,
        which will be notified when changes to this instance occurs.
     */
    private MutableLiveData<String> mutableLiveDataWrapper = new MutableLiveData<>();

    public MainViewModel() {
        // Here the data to be observed is set.
        mutableLiveDataWrapper.setValue("This is home fragment");
    }

    // The return value is of type "LiveData" which is superclass to the class MutableLiveData we use above.
    // The return value is of this type because, this way we can encapsulate the date (restricted the instance to get not set methods).
    // Liskovs substitutions principle used for encapsulation - Clever little pattern.
    public LiveData<String> getData() {
        return mutableLiveDataWrapper;
    }
}