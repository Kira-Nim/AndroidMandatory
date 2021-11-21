package com.example.android_mandatory.Model;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// This class is used as a repository class / gateway to model.
public class MainViewModel extends ViewModel {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    // List containing data for symptoms collection in db
    private final ArrayList<Symptom> symptomList = new ArrayList<Symptom>();

    // This MutableLiveData wrapper class makes it possible to observe data and react to changes to it.
    /*
        It will be possible to set "observers", that are connected to this instance, other places,
        which will be notified when changes to this instance occurs.
     */
    private MutableLiveData<ArrayList<Symptom>> mutableLiveDataWrapper = new MutableLiveData<>(symptomList);


    // Constructor:
    /*
        Set listener on db collection "symptoms"
        Get the collection from db and store it in an ArrayList of Symptom obj.
        Wrap the arraylist in a MutableLiveData wrapper.
     */
    public MainViewModel() {

        // Wrap the symptomList in the mutableLiveData Wrapper created above.
        mutableLiveDataWrapper.setValue(symptomList);

        // Add an Listener on the collection in db
        db.collection("symptoms")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {

                            symptomList.clear();

                            for (QueryDocumentSnapshot document : task.getResult()) {

                                // Create a Symptom obj for each document in collection
                                Symptom symptom = new Symptom();

                                // Get data from field "name" in current symptom document and document id
                                // Store values in symptom instance attributes
                                String currentName = document.getData().get("name").toString();
                                String currentId = document.getId();
                                symptom.setName(currentName);
                                symptom.setId(currentId);

                                // Get data from field "registration" in current symptom document.
                                List<Map<String, Object>> dbRegistrationMapList = (List<Map<String, Object>>)document.get("registrations");

                                // Iterate through list of maps and create an registration instance for each map in list
                                for(Map<String, Object> regMap:dbRegistrationMapList){

                                    long intensity = (long) regMap.get("intensity");

                                    Timestamp tempDate = (Timestamp)regMap.get("date");
                                    Date date = tempDate.toDate();

                                    // Create instance of Registration and set attribute values
                                    Registration registration = new Registration();
                                    registration.setRegistrationDate(date);
                                    registration.setIntensity(intensity);

                                    // Add new registration to registrations list on current symptom
                                    symptom.getRegistrations().add(registration);
                                }

                                // Add symptom to list of all symptoms in collection list
                                symptomList.add(symptom);

                                // Print status for getting data in console
                                System.out.println("Succes getting collection data");
                            }

                            // Wrap the symptomList in the mutableLiveData Wrapper created above.
                            mutableLiveDataWrapper.setValue(symptomList);

                        } else {
                            // Print status for getting data in console
                            System.out.println("Error getting documents: " + task.getException());
                        }
                    }
                });
    }

    // Make the arraylist wrapped in the mutableLiveData accessible through a get method.
    /*
        The return value is of type "LiveData" which is superclass to the class MutableLiveData we use above.
        The return value is of this type because, this way we can encapsulate the date (restricted the instance to get not set methods).
        Liskovs substitutions principle used for encapsulation - Clever little pattern.
     */
    public LiveData<ArrayList<Symptom>> getData() {
        return mutableLiveDataWrapper;
    }

    public void deleteDocument(String docId){
        db.collection("symptoms").document(docId).delete();
    }

    public void updateSymptom(Symptom symptom){

         db.collection("symptoms").document(symptom.getId()).update("name",symptom.getName());
    }

    public void createSymptom(Symptom symptom){

        Map<String, Object> data = new HashMap<>();
        data.put("name", symptom.getName());

        db.collection("symptoms").add(data);
        
    }

}