package com.k_beta.exercise.customviewspractice;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.k_beta.exercise.customviewspractice.activities.CircleLetterViewActivity;
import com.k_beta.exercise.customviewspractice.activities.RiseNumberTestActivity;
import com.k_beta.exercise.customviewspractice.widget.interf.RiseNumberEndListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private MainListAdapter adapter ;
    private ArrayList<String> data = new ArrayList<>();
    private ArrayList<String> targetList = new ArrayList<>();
    private MyUiCallBack callBack = new MyUiCallBack();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        layoutManager = new LinearLayoutManager(this);
        recyclerView = (RecyclerView) findViewById(R.id.list_view);
        recyclerView.setLayoutManager(layoutManager);
        initListDatas();
        adapter = new MainListAdapter(data);
        adapter.setCallBack(callBack);
        recyclerView.setAdapter(adapter);
    }

    private void initListDatas() {
        data.add("① CircleLetterView");
        data.add("② RiseNumberView");
        targetList.add(CircleLetterViewActivity.class.getName());
        targetList.add(RiseNumberTestActivity.class.getName());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class MyUiCallBack implements MainListAdapter.OnRecycleViewItemClickListener {

        @Override
        public void OnViewItemClicked(int position) {
            Intent it = new Intent();
            it.setClassName(MainActivity.this,targetList.get(position));
            startActivity(it);
        }
    }
}
