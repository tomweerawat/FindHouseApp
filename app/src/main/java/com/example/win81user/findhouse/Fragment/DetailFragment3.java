package com.example.win81user.findhouse.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.win81user.findhouse.API.MyApi;
import com.example.win81user.findhouse.Map.RecieveLatLng;
import com.example.win81user.findhouse.Model.ItemModel;
import com.example.win81user.findhouse.Model.Property;
import com.example.win81user.findhouse.R;
import com.example.win81user.findhouse.Utility.ClickListener;
import com.example.win81user.findhouse.Utility.LoadingDialogFragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
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
 * Created by Win81 User on 13/1/2560.
 */

public class DetailFragment3 extends Fragment implements Callback<ItemModel>,
        ClickListener,BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener{
    private TextView description, txtdetail, tv_message, fakedata,rent,price,contactname;
    private ImageView img;
    private ArrayList<Property> data;
    Retrofit retrofit;
    private ItemModel itemModel;
    private Toolbar toolbar;
    private FrameLayout frameLayout;
    private SliderLayout mDemoSlider;
    /*String API = "http://192.168.25.2:8181/FindHouse/webservice/";*/
    String API = "http://www.tnfindhouse.com/service/";
    private LoadingDialogFragment loadingDialogFragment;
    private static final String TAG_DIALOG_FRAGMENT = "dialog_fragment";
    private MapView mapView;
    private GoogleMap googleMap;
    LatLng latLng;
    Marker mMarker;
    private GoogleMap mMap;
    private String srtUrl ;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.testdetail, container, false);
        initViews(view);
        startanimation(view);
        prepareservice();
        return view;
    }


    private void initViews(View view) {
        txtdetail = (TextView) view.findViewById(R.id.propname);
        price = (TextView) view.findViewById(R.id.price);
        description = (TextView) view.findViewById(R.id.detail);
        contactname = (TextView) view.findViewById(R.id.contactname);
        rent = (TextView) view.findViewById(R.id.rent);
        toolbar = (Toolbar) view.findViewById(R.id.toolbardd);
        toolbar.setNavigationIcon(R.drawable.ic_navigate_before_black_36dp);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        mDemoSlider = (SliderLayout) view.findViewById(R.id.slider1);
        mDemoSlider.addOnPageChangeListener(this);

    }

    private void startanimation(View view) {
        YoYo.with(Techniques.RubberBand)
                .duration(100)
                .playOn(view.findViewById(R.id.propname));
        YoYo.with(Techniques.RubberBand)
                .duration(1000)
                .playOn(view.findViewById(R.id.detail));

        YoYo.with(Techniques.Tada)
                .duration(2000)
                .playOn(view.findViewById(R.id.toolbardd));
        YoYo.with(Techniques.FadeIn)
                .duration(3000)
                .playOn(view.findViewById(R.id.slider1));
        YoYo.with(Techniques.FadeIn)
                .duration(3000)
                .playOn(view.findViewById(R.id.rent));
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
    public void itemClicked(View view, int position) {

    }

    @Override
    public void onResponse(Call<ItemModel> call, Response<ItemModel> response) {

        itemModel = response.body();
        data = new ArrayList<>(Arrays.asList(itemModel.getProperty()));
        for (int i = 0; i < data.size(); i++) {
            TextSliderView textSliderView = new TextSliderView(this.getContext());
            txtdetail.setText(data.get(3).getPropertyname());
            rent.setText(data.get(3).getStatus());
            description.setText(data.get(3).getDescription() + "\n" + data.get(3).getContact());
            price.setText(data.get(3).getPrice());
//            contactname.setText(data.get(3).getContact()+"\n"+data.get(3).getLat()+"\t"+","+data.get(3).getLongtitude());
            contactname.setText(data.get(3).getLocation());
            contactname.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Double lat = data.get(3).getLat();
                    Double lng = data.get(3).getLongtitude();

                    Intent intent = new Intent(getActivity(), RecieveLatLng.class);
                    intent.putExtra("lat",lat);
                    intent.putExtra("lng",lng);
                    startActivity(intent);

                 /*   Fragment someFragment = new TestMap();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.GG, someFragment ); // give your fragment container id in first parameter
                    transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                    setUpMap();
                    transaction.commit();*/

                }
            });

            if (i == 0) {
                srtUrl = data.get(3).getImage();
            } else if (i == 1) {
                srtUrl = data.get(3).getImage2();
            } else if (i == 2) {
                srtUrl = data.get(3).getImage3();
            } else if (i == 3) {
                srtUrl = data.get(3).getImage4();
            } else if (i == 4) {
                srtUrl = data.get(3).getImage5();
            }
            textSliderView
//                    .description(data.get(0).getDescription())
                    .image(srtUrl)
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);


            textSliderView.bundle(new Bundle());

            mDemoSlider.addSlider(textSliderView);



        }

        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);

        Log.d("Response", "Response" + itemModel);

    }

    @Override
    public void onFailure(Call<ItemModel> call, Throwable t) {
        Toast.makeText(getActivity(), "Failed !", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        mDemoSlider.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
    private void setUpMap() {
        data = new ArrayList<>(Arrays.asList(itemModel.getProperty()));

        try{

            data = new ArrayList<>(Arrays.asList(itemModel.getProperty()));
            Double lat = data.get(0).getLat();
            Double lng = data.get(0).getLongtitude();
            latLng = new LatLng(lat, lng);
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            Log.e("lat","lng"+latLng);
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker));
            mMarker = mMap
                    .addMarker(markerOptions
                            .title(data.get(0).getContact())
                            .snippet(data.get(0).getContact()));
        }
        catch(Exception e){
            Log.e("this error", "ok"+e);
        }
    }


}
