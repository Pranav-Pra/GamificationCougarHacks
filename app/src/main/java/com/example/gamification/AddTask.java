package com.example.gamification;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class AddTask extends AppCompatActivity {
    EditText addTaskNameET, addTaskPointsET;
    Spinner chooseEmployeeSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        chooseEmployeeSpinner = (Spinner) findViewById(R.id.chooseEmployeeSpinner);

        ArrayList<String> spinnerArray = new ArrayList<>();

        spinnerArray.addAll(FirebaseHelper.currentBoss.getEmployeeNames());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        chooseEmployeeSpinner.setAdapter(adapter);
    }

    public void goToHomeScreen(View v) {
        Intent hIntent = new Intent(AddTask.this, HomeScreen.class);
        startActivity(hIntent);
    }

    public void goToLeaderboard(View v) {
        Intent Intent = new Intent(AddTask.this, Leaderboard.class);
        startActivity(Intent);
    }

    public void addTask(View v) {
        addTaskNameET = findViewById(R.id.addTaskNameET);
        addTaskPointsET = findViewById(R.id.addTaskPointsET);

        String taskName = addTaskNameET.getText().toString();
        int taskPoints = Integer.parseInt(addTaskPointsET.getText().toString());

        int index = chooseEmployeeSpinner.getSelectedItemPosition();
        String userId = FirebaseHelper.currentBoss.getEmployees().get(index);

        Task t = new Task(taskPoints, taskName);

        MainActivity.firebaseHelper.addTask(userId, t);

        addTaskNameET.setText("");
        addTaskPointsET.setText("");

        Toast.makeText(getApplicationContext(), "Successfully added task", Toast.LENGTH_SHORT).show();
    }
}