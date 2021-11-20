package com.example.android_mandatory.ui;

import androidx.appcompat.app.AppCompatActivity;
import com.example.android_mandatory.R; // Hvis R driller så husk lige denne her :D
import android.os.Bundle;

public class EditSymptomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_symptom);
    }
}