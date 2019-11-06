package com.knotlink.salseman.activity.maps;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.knotlink.salseman.R;
import com.knotlink.salseman.model.report.ModelTimeReport;
import com.knotlink.salseman.utils.Constant;

import java.util.Objects;

public class ReportTimeMap extends FragmentActivity implements OnMapReadyCallback {
    private String strShopName;
    private String strTime;
    private String strActivity;
    private double strLat;
    private double strLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
        ModelTimeReport tModel = (ModelTimeReport) getIntent().getSerializableExtra(Constant.MODEL_INTENT);


        strLat = Double.parseDouble(Objects.requireNonNull(tModel.getLatitude()));
        strLong = Double.parseDouble(Objects.requireNonNull(tModel.getLongitude()));
        strShopName = tModel.getShop();
        strActivity = tModel.getActivity();
        strTime = tModel.getTime();
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(Constant.TAG, "Shop Name :"+strShopName);
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setZoomGesturesEnabled(true);
        googleMap.getUiSettings().setCompassEnabled(true);
        LatLng tLatLong = new LatLng(strLat, strLong);
        googleMap.addMarker(new MarkerOptions()
                .position(tLatLong)
                .anchor(0.5f, 1.0f)
                .title(strActivity+" "+strTime)
                .snippet(strShopName)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)))
                .showInfoWindow();
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(tLatLong));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(12));

    }
}
