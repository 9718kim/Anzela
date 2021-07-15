package com.anzela.myapplication1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class DetailActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    ImageView backButton;
    EditText writeCommend;
    View commentButton;
    ImageView commentImg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);

        backButton = findViewById(R.id.backpressed);
        writeCommend = findViewById(R.id.writecommend);
        commentButton = findViewById(R.id.commentBtn);
        commentImg = findViewById(R.id.commentimg);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        writeCommend.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean bFocus) {
                commentButton.setBackgroundDrawable(ContextCompat.getDrawable(DetailActivity.this, R.drawable.circle_aqua));
                commentImg.setBackgroundDrawable(ContextCompat.getDrawable(DetailActivity.this, R.drawable.ic_comment_white));
                if( !bFocus){
                    commentButton.setBackgroundDrawable(ContextCompat.getDrawable(DetailActivity.this, R.drawable.circlr_black_two));
                    commentImg.setBackgroundDrawable(ContextCompat.getDrawable(DetailActivity.this, R.drawable.ic_comment_mint));
                }
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        LatLng SEOUL = new LatLng(37.56, 126.97);

        LatLng startPoitn = new LatLng(37.49492908756368, 126.83953848543452);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(startPoitn);
        markerOptions.title("출발지");
        markerOptions.snippet("언라이크");
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_start_marker));
        mMap.addMarker(markerOptions);

        LatLng endPoint = new LatLng(37.498313171135756, 126.85429685581971);
        MarkerOptions markerOptions2 = new MarkerOptions();
        markerOptions2.position(endPoint);
        markerOptions2.title("도착지");
        markerOptions2.snippet("경인중학교");
        markerOptions2.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_end_marker));
        mMap.addMarker(markerOptions2);

        LatLng camera = new LatLng(37.4976211293, 126.846917671);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(camera, 14));
    }
}