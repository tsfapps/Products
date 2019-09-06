package com.knotlink.salseman.activity.maps;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
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

import java.util.List;

public class RouteMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap tMap;
    private int i;
    private List<ModelReportOrderMap> tModels;

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

    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        tMap = googleMap;
        tMap.getUiSettings().setZoomControlsEnabled(true);
        tMap.getUiSettings().setZoomGesturesEnabled(true);
        tMap.getUiSettings().setCompassEnabled(true);

        for(i = 0 ; i < tModels.size()-1 ; i++) {

            createMarker(Double.parseDouble(tModels.get(i).getLatitude()), Double.parseDouble(tModels.get(i).getLongitude()),
                    tModels.get(i).getOrgName(), tModels.get(i).getTime(), googleMap, tModels.get(i).getAreaStatus());
        }
        LatLng lastVisitedShop = new LatLng(Double.parseDouble(tModels.get(i).getLatitude()), Double.parseDouble(tModels.get(i).getLongitude()));
       if (tModels.get(i).getAreaStatus().equalsIgnoreCase("0")) {
            tMap.addMarker(new MarkerOptions().position(lastVisitedShop).title(tModels.get(i).getOrgName()).snippet(tModels.get(i).getTime()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        }else{
           tMap.addMarker(new MarkerOptions().position(lastVisitedShop).title(tModels.get(i).getOrgName()).snippet(tModels.get(i).getTime()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));

       }
        tMap.moveCamera(CameraUpdateFactory.newLatLng(lastVisitedShop));
        tMap.animateCamera(CameraUpdateFactory.zoomTo(11));

    }

    protected Marker createMarker(double latitude, double longitude, String title, String snippet, GoogleMap googleMap, String strAreaStatus) {
        if (strAreaStatus.equalsIgnoreCase("0")){
            return googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(latitude, longitude))
                    .anchor(0.5f, 0.5f)
                    .title(title)
                    .snippet(snippet)
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        }else {
            return googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(latitude, longitude))
                    .anchor(0.5f, 0.5f)
                    .title(title)
                    .snippet(snippet)
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        }

    }
}
