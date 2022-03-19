package com.symbian.library.usermanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        getSupportActionBar().hide();
        Window window = getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.defaultColor));
    }
}