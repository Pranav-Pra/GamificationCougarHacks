package com.example.gamification;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

public class EmployeeJoinCode extends AppCompatActivity {
    EditText employeeJoinCodeET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_join_code);
    }

    public void employeeJoinCode(View v) {
        employeeJoinCodeET = findViewById(R.id.employeeJoinCodeET);

        // TODO: Create a running list of all codes that have been generated
        MainActivity.firebaseHelper.getCodes(new FirebaseHelper.CodesCallback() {
            @Override
            public void onCallback(ArrayList<String> codes) {
                String output = "";
                for(int i = 0; i < codes.size(); i++) {
                    output += (i + 1) + ": " + codes.get(i) + "\n";
                }
                Log.i("LFRA", output);
            }
        });
        //TODO:
        // Check if the code is valid
        // Add an updateCode function and update the code field
        // Go to the home screen after successful
    }
}