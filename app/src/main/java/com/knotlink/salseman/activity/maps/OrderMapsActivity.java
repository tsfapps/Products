package com.knotlink.salseman.activity.maps;

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

public class OrderMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap tMap;
    private String strShopName;
    private String srAreaStatus;
    private String strAddress;
    private String strDate;
    private double strLong;
    private double strLat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        strLat = Double.parseDouble(getIntent().getStringExtra(Constant.FIRST_LAT));
        strLong = Double.parseDouble(getIntent().getStringExtra(Constant.FIRST_LONG));
        strShopName = getIntent().getStringExtra(Constant.SHOP_NAME);
        strAddress = getIntent().getStringExtra(Constant.START_ADDRESS);
        strDate = getIntent().getStringExtra(Constant.START_DATE);
        srAreaStatus = getIntent().getStringExtra(Constant.AREA_STATUS);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        tMap = googleMap;
        tMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        tMap.getUiSettings().setZoomControlsEnabled(true);
        tMap.getUiSettings().setZoomGesturesEnabled(true);
        tMap.getUiSettings().setCompassEnabled(true);
        LatLng tLogin = new LatLng(strLat, strLong);
        if (srAreaStatus.equalsIgnoreCase("0")) {
            tMap.addMarker(new MarkerOptions().position(tLogin).title(strShopName).snippet(strAddress).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        }else {
            tMap.addMarker(new MarkerOptions().position(tLogin).title(strShopName).snippet(strDate).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        }
        tMap.moveCamera(CameraUpdateFactory.newLatLng(tLogin));
        tMap.animateCamera(CameraUpdateFactory.zoomTo(12));

    }
}
