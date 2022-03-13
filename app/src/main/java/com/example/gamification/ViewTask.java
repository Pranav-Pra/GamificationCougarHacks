package com.example.gamification;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class ViewTask extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);
    }
    public void goToHomeScreen(View v) {
        setContentView(R.layout.activity_home_screen);
    }
    public void goToLeaderboard(View v) {
        setContentView(R.layout.activity_leaderboard);
    }
}