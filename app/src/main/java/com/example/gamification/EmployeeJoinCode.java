package com.example.gamification;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
        String joinCode = employeeJoinCodeET.getText().toString();

        // TODO: Create a running list of all codes that have been generated
        MainActivity.firebaseHelper.getCodes(new FirebaseHelper.CodesCallback() {
            @Override
            public void onCallback(ArrayList<String> codes) {
                boolean found = false;
                for(String code : codes) {
                    if(joinCode.equals(code)) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        found = true;
                    }
                }
                if(!found) {
                    Toast.makeText(getApplicationContext(), "Join code does not exist", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}