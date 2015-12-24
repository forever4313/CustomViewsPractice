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
public class RiseNumberTestActivity extends AppCompatActivity {

    RiseNumberImplView txt1,txt2,txt3,txt4,txt5;
    Button buttonStart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rise_number_activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        txt1 = (RiseNumberImplView) findViewById(R.id.number1);
        txt2 = (RiseNumberImplView) findViewById(R.id.number2);
        txt3 = (RiseNumberImplView) findViewById(R.id.number3);
        txt4 = (RiseNumberImplView) findViewById(R.id.number4);
        txt5 = (RiseNumberImplView) findViewById(R.id.number5);
        buttonStart = (Button) findViewById(R.id.btn);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runAll();
            }


        });
    }
    private void runAll() {

        txt1.setNumbers(30,1000).setDuration(3000).start();
        txt2.setNumbers(10.0f,100.0f).setDuration(3000).start();
        txt3.setNumbers(200.0f,30000.0f).setDuration(3000).start();
        txt4.setNumbers(30000.0f,100000.0f).setDuration(3000).start();
        txt5.setNumbers(1,987654321.0,true).setDuration(3000).start();
    }

}
