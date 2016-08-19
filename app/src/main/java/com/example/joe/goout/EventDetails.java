package com.example.joe.goout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class EventDetails extends AppCompatActivity {
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        textView = (TextView) findViewById(R.id.textView);
        Bundle bundle = getIntent().getExtras();
        textView.setText(bundle.getString("Description"));
    }
}
