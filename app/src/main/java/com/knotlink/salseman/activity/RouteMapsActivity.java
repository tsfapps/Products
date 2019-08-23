package com.knotlink.salseman.activity;

import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.knotlink.salseman.R;
import com.knotlink.salseman.model.report.ModelReportOrderMap;
import com.knotlink.salseman.utils.Constant;
import com.knotlink.salseman.utils.ImageConverter;

import java.util.List;

public class RouteMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap tMap;
    private double doubleLat;
    private double doubleLong;
    private String strLat;
    private String strLong;
    private String strAddress;
    private List<ModelReportOrderMap> tModels;
    private Bitmap markerImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        initActivity();
    }

    private void initActivity(){
        tModels = (List<ModelReportOrderMap>)getIntent().getSerializableExtra(Constant.MODEL_INTENT);
        markerImg = ImageConverter.getBitmapFromVectorDrawable(getApplicationContext(), R.drawable.ic_map_red);


    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        tMap = googleMap;
        tMap.getUiSettings().setZoomControlsEnabled(true);
        tMap.getUiSettings().setZoomGesturesEnabled(true);
        tMap.getUiSettings().setCompassEnabled(true);
        tMap.animateCamera(CameraUpdateFactory.zoomTo(12));
        for(int i = 0 ; i < tModels.size() ; i++) {

            createMarker(Double.parseDouble(tModels.get(i).getLatitude()), Double.parseDouble(tModels.get(i).getLongitude()),
                    tModels.get(i).getShopName(), tModels.get(i).getTime(), googleMap);
        }
    }

    protected Marker createMarker(double latitude, double longitude, String title, String snippet, GoogleMap googleMap) {

        return googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .anchor(0.5f, 0.5f)
                .title(title)
                .snippet(snippet));
    }
}
