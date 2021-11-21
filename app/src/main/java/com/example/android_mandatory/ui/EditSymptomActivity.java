package com.example.android_mandatory.ui;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android_mandatory.MainActivity;
import com.example.android_mandatory.R; // Hvis R driller så husk lige denne her :D
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EditSymptomActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE_KEY_CLICKED_ITEM_ID = "com.example.android_mandatory.EditSymptomActivity.id";
    public static final String EXTRA_MESSAGE_KEY_CLICKED_ITEM_TEXT = "com.example.android_mandatory.EditSymptomActivity.text";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_symptom);

        // Get the Intent that started this activity and extract the two Strings; id and Symptom name
        Intent intent = getIntent();
        String id = intent.getStringExtra(SymptomsFragment.EXTRA_MESSAGE_KEY_CLICKED_ITEM_ID);
        String symptomName = intent.getStringExtra(SymptomsFragment.EXTRA_MESSAGE_KEY_ITEM_TEXT);

        // Capture the layout's TextView and set the symptomName String as its text
        TextView textView = findViewById(R.id.textView);
        textView.setText(symptomName);

        // set onclick listener on button
        Button saveButton = findViewById(R.id.button);
        saveButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view){

                String editedText = textView.getText().toString();

                // Create new intent to be send back to SymptomFragment when save button is pressed
                Intent backToMainIntent = new Intent(EditSymptomActivity.this, MainActivity.class);
                backToMainIntent.putExtra(EXTRA_MESSAGE_KEY_CLICKED_ITEM_ID, id);
                backToMainIntent.putExtra(EXTRA_MESSAGE_KEY_CLICKED_ITEM_TEXT, editedText);

                setResult(RESULT_OK, backToMainIntent);
                finish();
            }
        });

    }
}