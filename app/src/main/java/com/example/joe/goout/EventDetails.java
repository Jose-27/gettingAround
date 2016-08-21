package com.example.joe.goout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EventDetails extends AppCompatActivity {
    TextView textView;
    Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        final Bundle bundle = getIntent().getExtras();
        textView = (TextView) findViewById(R.id.textView);
        textView.setText(bundle.getString("Description"));
        mButton = (Button) findViewById(R.id.sharBtn);
        mButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                shareEventContent();
            }
        });
    }

    private void shareEventContent(){
        final Bundle bundle = getIntent().getExtras();
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
        String subject = bundle.getString("ParkName");
        String title = bundle.getString("Title");
        String link = bundle.getString("Link");

        share.putExtra(Intent.EXTRA_TITLE, title);
        share.putExtra(Intent.EXTRA_SUBJECT, subject);
        share.putExtra(Intent.EXTRA_TEXT, "Event: " + title + "\n" + link);

        startActivity(Intent.createChooser(share, "Share Event"));

    }




}
