package com.example.android_mandatory.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.android_mandatory.Model.MainViewModel;
import com.example.android_mandatory.Model.Symptom;
import com.example.android_mandatory.R;
import com.example.android_mandatory.databinding.FragmentSymptomsBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;


public class SymptomsFragment extends Fragment{

    public static final String EXTRA_MESSAGE_KEY_CLICKED_ITEM_ID = "com.example.android_mandatory.SymptomFragment.id";
    public static final String EXTRA_MESSAGE_KEY_ITEM_TEXT = "com.example.android_mandatory.SymptomFragment.text";

    private MainViewModel mainViewModel;
    private FragmentSymptomsBinding binding;
    private ArrayList<Symptom> symptomList;
    private SymptomAdapter symptomAdapter;

    // Create an interface inside this class. Below we make an anonymous implementation of this interface.
    /*
        It is necessary to make this interface because the setOnClickListener() that is used
        to register the event on the view (to be shown in table row), which is set inside the
        SymptomAdapter, requires an instance of type ItemClickListener and not OnItemClickListener.
     */
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }


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

        // Get data data and store it in symptomList
        symptomList = mainViewModel.getData().getValue();

        // Create instance of Adapter
        /*
           that will be used by the SymptomRecycleView to create ViewHolders (incl views)
           for each element shown on screen.
           It will also be used to bind data to the views in the viewHolders when a given view
           element is going to be shown on screen.
         */
        symptomAdapter = new SymptomAdapter(symptomList, itemClickListener);

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

                // This is not stricly nessasery as s and symptomList holds a reference to the same Arraylist<Symptom>
                symptomAdapter.setItems(s);

                // update symptomList to carry a reference to the same list in memory af the
                // given to the adapter/shown in view
                symptomList = s;

                // Notify the adapter that the data inside the mutableLiveDataWrapper has changed
                // and has to be updated.
                symptomAdapter.notifyDataSetChanged();

            }

        });

        // Register functionality for an oncklicevent on an button in symptom list in ui.
        FloatingActionButton buttonView = binding.floatingActionButton4;
        buttonView.setOnClickListener(buttonClickListener);


        // Create an ItemTouchHelper and attach it to the recyclerView
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(symptomRecyclerView);

        return root;

    }

    // Click listener for items in symptom table.
    /*
    AdapterView.OnItemClickListener is the name of an interface.
    This interface is implemented by making an anonymous implementation of the required onItemClick method.
 */
    private ItemClickListener itemClickListener = new ItemClickListener() {

        @Override
        public void onItemClick(View view, final int position) {

            Symptom clickedSymptom = symptomList.get(position);

            String symptomText = clickedSymptom.getName();
            String symptomId = clickedSymptom.getId();

            Intent intent = new Intent (getActivity(), EditSymptomActivity.class);

            intent.putExtra(EXTRA_MESSAGE_KEY_CLICKED_ITEM_ID, symptomId);
            intent.putExtra(EXTRA_MESSAGE_KEY_ITEM_TEXT, symptomText);

            editActivityLauncher.launch(intent);
        }
    };


    // Click listener for createButton in on SymptomFragment page.
    private View.OnClickListener buttonClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {

            String symptomText = "Symptom navn";
            String symptomId = "new activity";

            Intent intent = new Intent (getActivity(), EditSymptomActivity.class);

            intent.putExtra(EXTRA_MESSAGE_KEY_CLICKED_ITEM_ID, symptomId);
            intent.putExtra(EXTRA_MESSAGE_KEY_ITEM_TEXT, symptomText);

            editActivityLauncher.launch(intent);
        }
    };


    // Get an ActivityResultLauncher instance that will have an attribute with a callback
    // which will be run when a return intent is received from the edit view.
    private ActivityResultLauncher<Intent> editActivityLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {

                @Override
                public void onActivityResult(ActivityResult result) {

                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();

                        String id = intent.getStringExtra(EditSymptomActivity.EXTRA_MESSAGE_KEY_CLICKED_ITEM_ID);
                        String symptomName = intent.getStringExtra(EditSymptomActivity.EXTRA_MESSAGE_KEY_CLICKED_ITEM_TEXT);

                        if(id.equals("new activity")){
                            Symptom newSymptom = new Symptom();
                            newSymptom.setName(symptomName);
                            mainViewModel.createSymptom(newSymptom);
                            symptomList.add(newSymptom);
                            symptomAdapter.notifyDataSetChanged();
                        }else{
                            for (Symptom symptom : symptomList) {

                                String currentSymptomId = symptom.getId();
                                if(currentSymptomId!= null && currentSymptomId.equals(id)){
                                    symptom.setName(symptomName);
                                    mainViewModel.updateSymptom(symptom);
                                    symptomAdapter.notifyDataSetChanged();

                                }
                            }
                        }
                    }
                }
            });

    // Callback for when a swipe on a item in table is detected
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            int position = viewHolder.getAdapterPosition();

            if(ItemTouchHelper.LEFT == direction){
                Symptom symptom = symptomList.get(position);
                symptomList.remove(position);

                if(symptom.getId() != null){
                    mainViewModel.deleteDocument(symptom.getId());
                }

                symptomAdapter.notifyItemRemoved(position);
                symptomAdapter.notifyItemRangeChanged(position, symptomAdapter.getItemCount());
            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(getActivity(), R.color.design_default_color_error))
                    .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_forever_24)
                    .create()
                    .decorate();

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };


    // Run when the fragments lifecycle ends
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}


// Delete resource: https://www.youtube.com/watch?v=rcSNkSJ624U