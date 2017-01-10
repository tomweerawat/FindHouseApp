package com.example.win81user.findhouse.Map;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;

import com.example.win81user.findhouse.API.MyApi;
import com.example.win81user.findhouse.Model.ItemModel;
import com.example.win81user.findhouse.Model.Property;
import com.example.win81user.findhouse.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Win81 User on 24/12/2559.
 */

public class TestMap extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, Callback<ItemModel>, ResultCallback<Status>,
        LocationListener {
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    Location mLocation;
    LatLng latlng;
    double latitude;
    double longitude;
    private int PROXIMITY_RADIUS = 10000;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LatLng latLng;
    LocationRequest mLocationRequest;
    private ItemModel itemModel;
    private ArrayList<Property> data;
    private static final String Geofence_id = "Geofence_id";
    Retrofit retrofit;
    String API = "http://192.168.25.2:8181/FindHouse/webservice/";
    private static final long GEO_DURATION = 60 * 60 * 1000;
    private static final String GEOFENCE_REQ_ID = "My Geofence";
    private static final float GEOFENCE_RADIUS = 500.0f; // in meters
    Geofence geofence;
    private Marker geoFenceMarker;
    private PendingIntent geoFencePendingIntent;
    private final int GEOFENCE_REQ_CODE = 0;
    ArrayList<Geofence> geofenceList = new ArrayList<Geofence>();


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
        initview(view);
        return view;
    }

    private void initview(View view) {
        Button btnRestaurant = (Button) view.findViewById(R.id.btnRestaurant);
        btnRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                build_retrofit_and_get_response("property");
                prepareservice();
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d("OnResume", "OnResume");
    }


    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }

    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    private boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(getContext());
        if (result != ConnectionResult.SUCCESS) {
            if (googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(getActivity(), result,
                        0).show();
            }
            return false;
        }
        return true;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("onLocationChanged", "entered");

        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }
        //Place current location marker
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");

        // Adding colour to the marker
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
//        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker));

        // Adding Marker to the Map
        mCurrLocationMarker = mMap.addMarker(markerOptions);

        //move map camera
        animateMarker(mCurrLocationMarker, location);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(14));

        Log.d("onLocationChanged", String.format("latitude:%.3f longitude:%.3f", latitude, longitude));

        Log.d("onLocationChanged", "Exit");

    }

    private void prepareservice() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(API)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        Log.e("retrofit2", "connected" + API);


        apiCall(retrofit);


    }

    private void apiCall(Retrofit retrofit) {
        MyApi myApi = retrofit.create(MyApi.class);
        Call<ItemModel> call = myApi.getShout();
        call.enqueue(this);
        Log.e("apiCall", "Success Call");
    }

    @Override
    public void onResponse(Call<ItemModel> call, Response<ItemModel> response) {
        itemModel = response.body();
        data = new ArrayList<>(Arrays.asList(itemModel.getProperty()));

        Log.d("KUYYYYYYYYY", "Kuy" + data);
        try {
            mMap.clear();
            for (int i = 0; i < data.size(); i++) {
                Double lat = data.get(i).getLat();
                Double lng = data.get(i).getLongtitude();
                MarkerOptions markerOptions = new MarkerOptions();
                latLng = new LatLng(lat, lng);
                markerOptions.position(latLng);
                Log.e("tomtom","tomtom"+latLng);
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker));
                geoFenceMarker = mMap
                        .addMarker(markerOptions
                        .title(data.get(i).getContact())
                        .snippet(data.get(i).getContact()));
                startGeofence();
            }

        }catch (Exception e){
            Log.d("onResponse", "There is an error");
            e.printStackTrace();
        }

    }
 /*   private void create(){
        data = new ArrayList<>(Arrays.asList(itemModel.getProperty()));
        for (int i = 0; i < data.size(); i++) {
            Geofence geofence = new Geofence.Builder()
                    .setRequestId(GEOFENCE_REQ_ID)
                    .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT)
                    .setCircularRegion(data.get(i).getLat(), data.get(i).getLongtitude(), GEOFENCE_RADIUS)
                    .setExpirationDuration(Geofence.NEVER_EXPIRE)
                    .build();
            geofenceList.add(geofence);
            Log.e("test","test"+geofence);
        }
    }*/
    private PendingIntent createGeofencePendingIntent() {
        Log.d("test", "createGeofencePendingIntent");
        if ( geoFencePendingIntent != null )
            return geoFencePendingIntent;

        Intent intent = new Intent( getActivity(), GeofenceTrasitionService.class);
        return PendingIntent.getService(
                getContext(), GEOFENCE_REQ_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT );
    }

    private void startGeofence() {
        Log.i("startGeofence", "startGeofence()");
        if( geoFenceMarker != null ) {
            Log.e("ggwp","ggwp"+geoFenceMarker);
            Geofence geofence = createGeofence(latLng, GEOFENCE_RADIUS );
//            create();
            GeofencingRequest geofenceRequest = createGeofenceRequest( geofence );
            addGeofence( geofenceRequest );
        } else {
            Log.e("startGeofence", "Geofence marker is null");
        }
    }

    // Create a Geofence Request
    private GeofencingRequest createGeofenceRequest( Geofence geofence ) {
        Log.d("createGeofenceRequest", "createGeofenceRequest");
        return new GeofencingRequest.Builder()
                .setInitialTrigger( GeofencingRequest.INITIAL_TRIGGER_ENTER )
                .addGeofence( geofence )
                .build();
    }
    //addgeofence
    private void addGeofence(GeofencingRequest request) {
        Log.d("addGeofence", "addGeofence");
        if (checkPermission())
            LocationServices.GeofencingApi.addGeofences(
                    mGoogleApiClient,
                    request,
                    createGeofencePendingIntent()
            ).setResultCallback((ResultCallback<? super Status>) this);
    }
    @Override
    public void onResult(@NonNull Status status) {
        Log.i("", "onResult: " + status);
        if ( status.isSuccess() ) {

            drawGeofence();
        } else {
            // inform about fail
        }


    }
    private Circle geoFenceLimits;
    private void drawGeofence() {
        Log.d("", "drawGeofence()");

        if ( geoFenceLimits != null )
            geoFenceLimits.remove();
        data = new ArrayList<>(Arrays.asList(itemModel.getProperty()));
        for (int i = 0; i < data.size(); i++) {
            Double lat = data.get(i).getLat();
            Double lng = data.get(i).getLongtitude();
            MarkerOptions markerOptions = new MarkerOptions();
            latLng = new LatLng(lat, lng);
            markerOptions.position(latLng);
            CircleOptions circleOptions = new CircleOptions()
                    .center(latLng)
                    .strokeColor(Color.argb(50, 70,70,70))
                    .fillColor( Color.argb(100, 150,150,150) )
                    .radius( GEOFENCE_RADIUS );
            geoFenceLimits = mMap.addCircle( circleOptions );

        }


    }

    // Create a Geofence
    private Geofence createGeofence( LatLng latLng, float radius ) {
        Log.d("createGeofence", "createGeofence"+latLng);
        return new Geofence.Builder()
                .setRequestId(GEOFENCE_REQ_ID)
                .setCircularRegion( latLng.latitude, latLng.longitude, radius)
                .setExpirationDuration( GEO_DURATION )
                .setTransitionTypes( Geofence.GEOFENCE_TRANSITION_ENTER
                        | Geofence.GEOFENCE_TRANSITION_EXIT )
                .build();
    }
    private boolean checkPermission() {
        Log.d("checkPermission", "checkPermission()");
        // Ask for permission if it wasn't granted yet
        return (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED );
    }


    @Override
    public void onFailure(Call<ItemModel> call, Throwable t) {

    }













/*
    private void build_retrofit_and_get_response(String type) {

//        String url = "https://maps.googleapis.com/maps/";
        String url = "http://192.168.25.2:8181/FindHouse/webservice/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitMaps service = retrofit.create(RetrofitMaps.class);

        Call<Example> call = service.getNearbyPlaces(type, latitude + "," + longitude, PROXIMITY_RADIUS);

        call.enqueue(new Callback<ItemModel>() {
            @Override
            public void onResponse(Call<ItemModel> call, Response<ItemModel> response) {
                itemModel = response.body();
                data = new ArrayList<>(Arrays.asList(itemModel.getProperty()));
                try {
                    mMap.clear();
                    // This loop will go through all the results and add marker on each location.
                    for (int i = 0; i < response.body().getResults().size(); i++) {
                        Double lat = response.body().getResults().get(i).getGeometry().getLocation().getLat();
                        Double lng = response.body().getResults().get(i).getGeometry().getLocation().getLng();
                        String placeName = response.body().getResults().get(i).getName();
                        String vicinity = response.body().getResults().get(i).getVicinity();
                        MarkerOptions markerOptions = new MarkerOptions();
                        LatLng latLng = new LatLng(lat, lng);
                        // Position of Marker on Map
                        markerOptions.position(latLng);
                        // Adding Title to the Marker
                        markerOptions.title(placeName + " : " + vicinity);
                        // Adding colour to the marker
                        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker));
//                        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
                        // Adding Marker to the Camera.
                        Marker m = mMap.addMarker(markerOptions);


                        // move map camera
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
                    }
                } catch (Exception e) {
                    Log.d("onResponse", "There is an error");
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                Log.d("onFailure", t.toString());
            }

        });

    }
*/
    public void animateMarker(final Marker marker, final Location location) {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        final LatLng startLatLng = marker.getPosition();
        final double startRotation = marker.getRotation();
        final long duration = 500;

        final Interpolator interpolator = new LinearInterpolator();

        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed
                        / duration);

                double lng = t * location.getLongitude() + (1 - t)
                        * startLatLng.longitude;
                double lat = t * location.getLatitude() + (1 - t)
                        * startLatLng.latitude;

                float rotation = (float) (t * location.getBearing() + (1 - t)
                        * startRotation);

                marker.setPosition(new LatLng(lat, lng));
                marker.setRotation(rotation);

                if (t < 1.0) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16);
                }
            }
        });
    }
}
