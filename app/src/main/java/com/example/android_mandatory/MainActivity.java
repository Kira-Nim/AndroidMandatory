package com.example.android_mandatory;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

// This package is created by Gradle
import com.example.android_mandatory.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // The line below means "get an instance of ActivityMainBinder, by deserializing the xml file that ActivityMainBinding is connected to, using a "LayoutInflater (getLayoutInflater())"
        // binding is an instance of ActivityMainBinder contains the deserialized xml from "activity_main.xml"
        // The deserialized xml file will be used below, to create a view.
        /*
        ActivityMainBinding
        --> is a class which is available because Gradle creates (NB not not imports but actually creates..) a package called "databinding".
        --> ActivityMainBinding will automatically be connected to the xml file with the same name when it is created.
             --> When "MainActivity" is created we get a UI xml-file to go with this called "activity_main".
                 Because the UI xml-file is called "activity_main, the  binding class, available when "databinding" is created by Gradle,
                 will be called "ActivityMainBinding"
        --> The "ActivityMainBinding" class enables you to deserializing the xml file and thereby gain access to the UI.

        inflate ()
        --> is a term for deserialisation (deflate is serialization) used when deserializing ui xml files.
        */
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        // ContentView is the root view connected to the given Activity.
        // There might be some child views (without "controllers"/Activities/Fragments) connected to the view we want.
        // There for we need to get the rootView.
        setContentView(binding.getRoot());

        // Create an instance of the view referred to above as root view for this activity.
        BottomNavigationView navView = findViewById(R.id.nav_view);

        // Create configuration obj for tab bar. Create a Builder instance which constructor is passed info of view destinations for tab bar buttons.
        // The constructor of the Builder instance will create an instance of "AppBarConfiguration"
        // The appBarConfiguration instance
        /*
            "AppBarConfiguration" is an buildin Android class.
            It is a class that contains configuration for a appbar element.

            "AppBarConfiguration.Builder" here we have pubic class inside a class ... (dette er nyt)
            NB "Builder" is NOT an object in an attribute on "AppBarConfiguration".

            When you say call a constructor on "AppBarConfiguration.Builder" with the "new" keyword
            then only the "Builder" class is instantiated. Did you want an "AppBarConfiguration" instance
            then you should have used only "new AppBarConfiguration()"

            The Builder class takes some view-id's as params.
            In this case the ID's of the two views that is to me shown when the tabbar buttons are clicked.
        */
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_registration, R.id.navigation_symptoms)
                .build();

        // Creates a navigation controller that can be used by the OS to setup the ability for one
        // or more given fragment views to replace each other in a given view.
        /*
            "NavController" and "Navigation" and "NavigationUI" are classes available in Android
             Navigation is primarily used to get access to a NavigationController instance.

             In activity_main.xml there is a fragment which has an id called: "@+id/nav_host_fragment_activity_main".
             ( android:id="@+id/nav_host_fragment_activity_main")
             Because it starts with @+id/ the fragment can be identified and because it is
             identified as a nav_host_fragment it can be used when creating an NavigationController.

             The "Navigation" class can be used to retrieve an NavigationController instance by passing
             an Activity and specifying what id in this activities xml files to look for.

             The fragment which is used to create the NavController instance must have the name of a path to a specific place in the Android OS
             --> android:name="androidx.navigation.fragment.NavHostFragment"
         */
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);

        // The information about what views should be allowed to be shown in the main activity is set up.
        /*
            These params contains info about which fragment xml files that should be allowed to replace
            a fragment in this classes xml-file and thereby shown in this activities view.
         */
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        /* Set the navBar element to be a part of the root view of this activity.
            "binding.navView" is a child view in the root view of this Activity.
         */
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

}