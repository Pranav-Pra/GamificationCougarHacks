package com.example.gamification;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ViewTask extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);
        MainActivity.firebaseHelper.getTasks(new FirebaseHelper.TasksCallback() {
            @Override
            public void onCallback(ArrayList<Task> tasks) {
                createListView();
            }
        });
    }
    public void goToHomeScreen(View v) {
        Intent hIntent = new Intent(getApplicationContext(), HomeScreen.class);
        startActivity(hIntent);

    }
    public void goToLeaderboard(View v) {
        Intent lIntent = new Intent(getApplicationContext(), Leaderboard.class);
        startActivity(lIntent);

    }

    public void createListView() {
        ListView lv = (ListView) findViewById(R.id.viewTasks);
        List<String> names = new ArrayList<>();
        for(Task t: MainActivity.firebaseHelper.getEmployeeTasks()) {
            names.add(t.getInstructions());
        }

        ArrayAdapter<Task> listAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, MainActivity.firebaseHelper.getEmployeeTasks());

        lv.setAdapter(listAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Task t = MainActivity.firebaseHelper.getEmployeeTasks().get(i);
                MainActivity.firebaseHelper.updatePoints(t.getPointValue(), t.getInstructions());
                Toast.makeText(getApplicationContext(), "Successfully completed task", Toast.LENGTH_SHORT).show();
            }
        });
    }
}