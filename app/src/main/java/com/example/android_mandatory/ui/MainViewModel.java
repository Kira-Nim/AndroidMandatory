package com.example.android_mandatory.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

// This class is used as a repository class / gateway to model.
public class MainViewModel extends ViewModel {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    // This MutableLiveData wrapper class makes it possible to observe data and react to changes to it.
    /*
        It will be possible to set "observers", that are connected to this instance, other places,
        which will be notified when changes to this instance occurs.
     */
    private MutableLiveData<String> mutableLiveDataWrapper = new MutableLiveData<>();

    public MainViewModel() {
        // Here the data to be observed is set.
        mutableLiveDataWrapper.setValue("This is home fragment");

        System.out.println("LOOK HERE!");
        System.out.println("Sæt noget ind her");







        // Add an Listener on the collection in db
        db.collection("symptoms")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {

                            myList.clear();
                            documentVTOList.clear();

                            for (QueryDocumentSnapshot document : task.getResult()) {

                                // Get each note from collection and add it to the list which will be shown in the view
                                String currentNote = document.getData().get("text").toString();
                                String currentId = document.getId();

                                // Create instance of DocumentVTO to store document id and text field value in.
                                // Add instance to documentVTOList
                                DocumentVTO currentDocumentVTO = new DocumentVTO();
                                currentDocumentVTO.setTextNote(currentNote);
                                currentDocumentVTO.setDocumentID(currentId);
                                documentVTOList.add(currentDocumentVTO);

                                // Add document text field value to myList
                                myList.add(currentNote);

                                // Print status for getting data in console
                                System.out.println("Succes getting collection data");
                            }

                            // Reload data.
                            itemsAdapter.notifyDataSetChanged();

                        } else {
                            // Print status for getting data in console
                            System.out.println("Error getting documents: " + task.getException());
                        }
                    }
                });

        for(String currentElement: myList){
            System.out.println("currentElement");
        }









    }

    // The return value is of type "LiveData" which is superclass to the class MutableLiveData we use above.
    // The return value is of this type because, this way we can encapsulate the date (restricted the instance to get not set methods).
    // Liskovs substitutions principle used for encapsulation - Clever little pattern.
    public LiveData<String> getData() {
        return mutableLiveDataWrapper;
    }



}