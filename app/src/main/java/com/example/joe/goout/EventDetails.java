package com.example.joe.goout;

import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class EventDetails extends AppCompatActivity implements OnMapReadyCallback{
    TextView textView;
    Button mButton;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        final Bundle bundle = getIntent().getExtras();
        textView = (TextView) findViewById(R.id.textView);
        textView.setText(bundle.getString("Description"));
        mButton = (Button) findViewById(R.id.shareBtn);
        mButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                shareEventContent();
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



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

    @Override
    public void onMapReady(GoogleMap googleMap) {

        if (googleMap != null)
        {

            mMap = googleMap;
            // Add a marker in Sydney, Australia, and move the camera.
            LatLng sydney = new LatLng(-34, 151);
            mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        }
    }
}
