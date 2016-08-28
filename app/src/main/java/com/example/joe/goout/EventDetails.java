package com.example.joe.goout;

import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.FloatProperty;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageButton;
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

import java.util.HashMap;

public class EventDetails extends AppCompatActivity implements OnMapReadyCallback{
    TextView textView;
    ImageButton shareBtn, backBtn;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        final Bundle bundle = getIntent().getExtras();
        textView = (TextView) findViewById(R.id.textView);
        textView.setText(bundle.getString("Description"));
        shareBtn = (ImageButton) findViewById(R.id.shareBtn);
        shareBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                shareEventContent();
            }
        });
        backBtn = (ImageButton) findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                onBackPressed();
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
        String link = bundle.getString("Link");;

        share.putExtra(Intent.EXTRA_TITLE, title);
        share.putExtra(Intent.EXTRA_SUBJECT, subject);
        share.putExtra(Intent.EXTRA_TEXT, "Event: " + title + "\n" + link);

        startActivity(Intent.createChooser(share, "Share Event"));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        addMarkers();

        final View mapView = getSupportFragmentManager().findFragmentById(R.id.map).getView();
        mMap.getUiSettings().setMapToolbarEnabled(false);

        if (mapView.getViewTreeObserver().isAlive()){
            mapView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @SuppressWarnings("deprecation") // We use the new method when supported
                @SuppressLint("NewApi") // We check which build version we are using.
                @Override
                public void onGlobalLayout() {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                        mapView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    } else {
                        mapView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /**
     * Add Markers to the map.
     */
    private void addMarkers(){

        //TO DO Remove nasty code
        final Bundle bundle = getIntent().getExtras();
        String coordinates = bundle.getString("Coordinates");
        String title = bundle.getString("Title");
        String[] mapString = coordinates.split(",");
        /**
         * makes sure that is a valid float number
         */
        try {
            float latitude = Float.parseFloat(mapString[0]);
            float longitude = Float.parseFloat(mapString[1]);
            LatLng latLng = new LatLng(latitude, longitude);
            mMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title(title));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17f));

        }catch (NumberFormatException e){
            /**
             * not a float
             * **/
        }

    }
}
