package com.example.win81user.findhouse.Map;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.win81user.findhouse.R;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    Location mLocation;
    GoogleApiClient mGoogleApiClient;
    LatLng latlng;
    private LocationRequest mLocationRequest;
    /*  @Override
      protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_maps);
          setUpMapIfNeeded();
          Log.d("Oncreate", "Oncreate");
      }
  */
    public static MapsActivity newInstance() {
        MapsActivity fragment = new MapsActivity();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_maps, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d("OnResume", "OnResume");
    }


    private void setUpMap() {

        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    1);
            return;
        }
        mMap.setMyLocationEnabled(true);
    }

   /* private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            SupportMapFragment mapFragment = (SupportMapFragment) getActivity().getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);

            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }
*/


    @Override
    public void onMapReady(GoogleMap map) {
        setUpMap();
        LatLng thailand = new LatLng(13.774642,100.581704);
        map.addMarker(new MarkerOptions().position(thailand)
                .title("My Location")
                .snippet("My Location"));

        map.animateCamera(CameraUpdateFactory.newLatLngZoom(thailand,10.0f));
    }
    private void setUpMapIfNeeded() {

        if (mMap == null) {

            SupportMapFragment mapFragment = (SupportMapFragment) getActivity().getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);

            if (mMap != null) {
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                if(mLocation != null){
                    latlng = new LatLng(mLocation.getLatitude(),mLocation.getLongitude());
                    setUpMap();
                }
            }
        }
    }

    /*public void latlong() throws JSONException {

        JSONArray jsonArrayData = jsonData.getJSONArray("rows");

        for (int i = 0; i <  jsonArrayData.length(); i++) {

            JSONObject c = jsonArrayData.getJSONObject(i);

            JSONObject tmpObject = c.getJSONObject("value");
            String latString = tmpObject.getString("latitude");
            String longString = tmpObject.getString("longitude");

            double lat = Double.parseDouble(latString);
            double lng = Double.parseDouble(longString);

            System.out.println("lat: " + lat + " // long: " + lng);

            MarkerOptions place = new MarkerOptions().position(new LatLng(lat ,lng)).title("Hello Maps");
            googleMap.addMarker(place);

        }
    }*/
}
