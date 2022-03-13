package com.example.gamification;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AddTask extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
    }
    public void goToHomeScreen(View v) {
        Intent hIntent = new Intent(AddTask.this, HomeScreen.class);
        startActivity(hIntent);

    }
    public void goToLeaderboard(View v) {
        Intent Intent = new Intent(AddTask.this, Leaderboard.class);
        startActivity(Intent);
    }
}