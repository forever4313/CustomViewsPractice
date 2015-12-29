package com.k_beta.exercise.customviewspractice.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.k_beta.exercise.customviewspractice.R;
import com.k_beta.exercise.customviewspractice.widget.RiseNumberImplView;

/**
 * Created by dongkai on 2015/12/24.
 */
public class LoadingViewTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_view_activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }


}
