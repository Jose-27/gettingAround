package com.example.joe.goout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.joe.goout.NetworkDetector.NetworkDetector;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    NetworkDetector networkDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        networkDetector = new NetworkDetector(this);

        if (networkDetector.isConnected()){
            setContentView(R.layout.activity_main);
            recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
            apiReader apiReader = new apiReader(this,recyclerView);
            apiReader.execute();
        }else{
            Toast.makeText(MainActivity.this,"NOT CONNECTED", Toast.LENGTH_SHORT).show();
        }
    }
}
