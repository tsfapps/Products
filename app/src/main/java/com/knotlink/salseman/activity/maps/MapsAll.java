package com.knotlink.salseman.activity.maps;

import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.knotlink.salseman.R;
import com.knotlink.salseman.model.dash.route.ModelMapAll;
import com.knotlink.salseman.storage.SharedPrefManager;
import com.knotlink.salseman.utils.Constant;
import com.koushikdutta.ion.Ion;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MapsAll extends FragmentActivity implements OnMapReadyCallback {

    private SharedPrefManager tPrefManager;
    private String strUserId;
    private GoogleMap tMap;
    private int i;
    private List<ModelMapAll> tModels;
    private Bitmap bmImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_all);
        tPrefManager = new SharedPrefManager(getApplicationContext());
        strUserId = tPrefManager.getUserId();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        initActivity();
    }

    private void initActivity(){
        tModels = (List<ModelMapAll>)getIntent().getSerializableExtra(Constant.MODEL_INTENT);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        tMap = googleMap;
        tMap.getUiSettings().setZoomControlsEnabled(true);
        tMap.getUiSettings().setZoomGesturesEnabled(true);
        tMap.getUiSettings().setCompassEnabled(true);

        for(i = 0 ; i < tModels.size()-1 ; i++) {
            try {
                bmImg = Ion.with(this)
                        .load(tModels.get(i).getUrl()).asBitmap().get();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
            createMarker(Double.parseDouble(tModels.get(i).getLoginLatitude()), Double.parseDouble(tModels.get(i).getLoginLongitude()),
                    tModels.get(i).getShopName(), tModels.get(i).getType(), googleMap, tModels.get(i).getAreaStatus(), bmImg);
        }
        LatLng lastVisitedShop = new LatLng(Double.parseDouble(tModels.get(i).getLoginLatitude()), Double.parseDouble(tModels.get(i).getLoginLongitude()));
//        if (tModels.get(i).getAreaStatus().equalsIgnoreCase("0")) {
            tMap.addMarker(new MarkerOptions()
                    .position(lastVisitedShop)
                    .title(tModels.get(i).getShopName())
                    .snippet(tModels.get(i).getType())
                    .icon(BitmapDescriptorFactory.fromBitmap(bmImg)))
                    .showInfoWindow();
//        }else{
//            tMap.addMarker(new MarkerOptions().position(lastVisitedShop).title(tModels.get(i).getShopName()).snippet(tModels.get(i).getTime()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
//        }
        tMap.moveCamera(CameraUpdateFactory.newLatLng(lastVisitedShop));
        tMap.animateCamera(CameraUpdateFactory.zoomTo(12));
    }

    protected Marker createMarker(double latitude, double longitude, String title, String snippet,
                                  GoogleMap googleMap, String strAreaStatus, Bitmap bmImg) {
//        if (strAreaStatus.equalsIgnoreCase("0")){
            return googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(latitude, longitude))
                    .anchor(0.5f, 1.0f)
                    .title(title)
                    .snippet(snippet)
                    .icon(BitmapDescriptorFactory.fromBitmap(bmImg)));

    }

}
