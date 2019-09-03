package com.knotlink.salseman.activity;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.knotlink.salseman.R;
import com.knotlink.salseman.utils.Constant;
public class ReportMapActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap tMap;
    private String strLoginAddress;
    private String strLogoutAddress;
    private double strLoginLat;
    private double strLoginLong;
    private double strLogoutLat;
    private double strLogoutLong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        strLoginLat = Double.parseDouble(getIntent().getStringExtra(Constant.FIRST_LAT));
        strLoginLong = Double.parseDouble(getIntent().getStringExtra(Constant.FIRST_LONG));
        strLogoutLat = Double.parseDouble(getIntent().getStringExtra(Constant.SECOND_LAT));
        strLogoutLong = Double.parseDouble(getIntent().getStringExtra(Constant.SECOND_LONG));
        strLoginAddress = getIntent().getStringExtra(Constant.START_ADDRESS);
        strLogoutAddress = getIntent().getStringExtra(Constant.END_ADDRESS);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        tMap = googleMap;
        tMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        tMap.getUiSettings().setZoomControlsEnabled(true);
        tMap.getUiSettings().setZoomGesturesEnabled(true);
        tMap.getUiSettings().setCompassEnabled(true);
        LatLng tLogin = new LatLng(strLoginLat, strLoginLong);
        tMap.addMarker(new MarkerOptions().position(tLogin).title(strLoginAddress).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        tMap.moveCamera(CameraUpdateFactory.newLatLng(tLogin));
        tMap.animateCamera(CameraUpdateFactory.zoomTo(12));
        LatLng tLogout = new LatLng(strLogoutLat, strLogoutLong);
        tMap.addMarker(new MarkerOptions().position(tLogout).title(strLogoutAddress).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
    }
}
