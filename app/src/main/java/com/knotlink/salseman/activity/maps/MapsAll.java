package com.knotlink.salseman.activity.maps;

import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.knotlink.salseman.R;
import com.knotlink.salseman.model.dash.route.ModelMapAll;
import com.knotlink.salseman.storage.SharedPrefManager;
import com.knotlink.salseman.utils.Constant;

import java.util.List;

public class MapsAll extends FragmentActivity implements OnMapReadyCallback {

    private SharedPrefManager tPrefManager;
    private String strUserId;
    private GoogleMap tMap;
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

        int i;
        for (i = 0; i <tModels.size()-1; i++) {
            if (tModels.get(i).getLoginLatitude().equalsIgnoreCase("") || tModels.get(i).getLoginLongitude().equalsIgnoreCase("")) {
                Toast.makeText(this, "Lat Long Error...", Toast.LENGTH_SHORT).show();
            } else {
                createMarker(Double.parseDouble(tModels.get(i).getLoginLatitude()), Double.parseDouble(tModels.get(i).getLoginLongitude()),
                        tModels.get(i).getShopName(), tModels.get(i).getType(), tModels.get(i).getTime(), googleMap,
                        tModels.get(i).getAreaStatus());

            }
        }
        if (tModels.get(i).getLoginLatitude().equalsIgnoreCase("")||tModels.get(i).getLoginLongitude().equalsIgnoreCase("")){
            Toast.makeText(this, "Lat Long Error...", Toast.LENGTH_SHORT).show();
        }else {
            LatLng lastVisitedShop = new LatLng(Double.parseDouble(tModels.get(i).getLoginLatitude()), Double.parseDouble(tModels.get(i).getLoginLongitude()));
        if (tModels.get(i).getAreaStatus().equalsIgnoreCase("0")) {
            tMap.addMarker(new MarkerOptions()
                    .position(lastVisitedShop)
                    .anchor(0.5f, 1.0f)
                    .title(tModels.get(i).getType()+" "+tModels.get(i).getTime())
                    .snippet(tModels.get(i).getShopName())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)))
                    .showInfoWindow();
        }else {
            tMap.addMarker(new MarkerOptions()
                    .position(lastVisitedShop)
                    .anchor(0.5f, 1.0f)
                    .title(tModels.get(i).getType()+" "+tModels.get(i).getTime())
                    .snippet(tModels.get(i).getShopName())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)))
                    .showInfoWindow();
        }

        tMap.moveCamera(CameraUpdateFactory.newLatLng(lastVisitedShop));
        tMap.animateCamera(CameraUpdateFactory.zoomTo(12));
        }

    }

    protected void createMarker(double latitude, double longitude, String title, String snippet, String strTime,
                                GoogleMap googleMap, String strAreaStatus) {
        if (strAreaStatus.equalsIgnoreCase("0")) {
            googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(latitude, longitude))
                    .anchor(0.5f, 1.0f)
                    .title(snippet + " " + strTime)
                    .snippet(title)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)))
                    .showInfoWindow();
        }else {
            Log.d(Constant.TAG,"Title :"+title);
            Log.d(Constant.TAG,"Snippet :"+snippet);
            googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(latitude, longitude))
                    .anchor(0.5f, 1.0f)
                    .title(snippet + " " + strTime)
                    .snippet(title)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)))
                    .showInfoWindow();
        }

    }

}
