package com.example.win81user.findhouse.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import com.example.win81user.findhouse.Map.MapsActivity;
import com.example.win81user.findhouse.Model.ItemModel;
import com.example.win81user.findhouse.Model.Property;
import com.example.win81user.findhouse.R;
import com.example.win81user.findhouse.Utility.ClickListener;
import com.example.win81user.findhouse.Utility.LoadingDialogFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
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
 * Created by Ratan on 7/29/2015.
 */
public class ShowDetailFragment extends Fragment implements Callback<ItemModel>,
        ClickListener,BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener,OnMapReadyCallback {
    private TextView description,txtdetail,tv_message,fakedata;
    private ImageView img;
    private ArrayList<Property> data;
    Retrofit retrofit;
    private   ItemModel itemModel;
    private Toolbar toolbar;
    private FrameLayout frameLayout;
    private SliderLayout mDemoSlider;
    String API = "http://192.168.25.2:8181/FindHouse/webservice/";
    private LoadingDialogFragment loadingDialogFragment;
    private static final String TAG_DIALOG_FRAGMENT = "dialog_fragment";
    private MapView mapView;
    private GoogleMap googleMap;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.detailprop_layout,container,false);
        initViews(view);
        startanimation(view);
        prepareservice();
//        mapinit();
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView = (MapView) view.findViewById(R.id.mapdetail);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);
    }
    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
        LatLng thailand = new LatLng(13.774642,100.581704);
        map.addMarker(new MarkerOptions().position(thailand).title("Marker in Sydney"));
//        map.moveCamera(CameraUpdateFactory.newLatLng(thailand));
//        map.moveCamera( CameraUpdateFactory.newLatLngZoom(new LatLng(13.698948,100.537306) , 6.0f));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(thailand,19.0f));

    }

    private void initViews(View view){
        frameLayout=(FrameLayout)view.findViewById(R.id.mapdetail);
        txtdetail = (TextView)view.findViewById(R.id.txtdetail);
        description = (TextView)view.findViewById(R.id.description);
        fakedata = (TextView)view.findViewById(R.id.fakedata);
//        img = (ImageView)view.findViewById(R.id.detailimg);
        toolbar = (Toolbar)view.findViewById(R.id.toolbardetail);
        toolbar.setTitle("Detail");
        toolbar.setNavigationIcon(R.drawable.ic_navigate_before_black_36dp);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        mDemoSlider = (SliderLayout)view.findViewById(R.id.slider);
        mDemoSlider.addOnPageChangeListener(this);

    }
    private void mapinit(){
        Fragment fragment2 = new MapsActivity();
        FragmentTransaction fragmentTransaction2 = getFragmentManager().beginTransaction();
        fragmentTransaction2.replace(R.id.mapdetail,fragment2);
        fragmentTransaction2.commit();
    }
    private void startanimation(View view){
        YoYo.with(Techniques.RubberBand)
                .duration(100)
                .playOn(view.findViewById(R.id.txtdetail));
        YoYo.with(Techniques.RubberBand)
                .duration(1000)
                .playOn(view.findViewById(R.id.description));

        YoYo.with(Techniques.Tada)
                .duration(2000)
                .playOn(view.findViewById(R.id.toolbardetail));
        YoYo.with(Techniques.FadeIn)
                .duration(3000)
                .playOn(view.findViewById(R.id.slider));
    }

    private void prepareservice(){
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
        Log.e("retrofit2","connected"+API);



        apiCall(retrofit);


    }

    private void apiCall(Retrofit retrofit) {
        MyApi myApi = retrofit.create(MyApi.class);
        Call<ItemModel> call = myApi.getShout();
        call.enqueue(this);
        Log.e("apiCall","Success Call");
    }
    @Override
    public void itemClicked(View view, int position) {

    }

    @Override
    public void onResponse(Call<ItemModel> call, Response<ItemModel> response) {

        itemModel = response.body();
        data = new ArrayList<>(Arrays.asList(itemModel.getProperty()));
        for (int i =0; i<data.size();i++){
            TextSliderView textSliderView = new TextSliderView(this.getContext());
            txtdetail.setText(data.get(i).getPropertyname());
            description.setText(data.get(i).getDescription()+"\n"+data.get(i).getPrice()+"\n"+data.get(i).getContact());
            String srtUrl = "";
            if(i == 0){
                srtUrl = data.get(0).getImage();
            }else if(i==1){
                srtUrl = data.get(0).getImage2();
            }else if(i==2){
                srtUrl = data.get(0).getImage3();
            }else if(i==3){
                srtUrl = data.get(0).getImage4();
            }else if(i==4){
                srtUrl = data.get(0).getImage5();
            }
            textSliderView
                    .description(data.get(0).getDescription())
                    .image(srtUrl)
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());

            mDemoSlider.addSlider(textSliderView);

           /* Glide.with(this).load(data.get(i).getImage3())
                    .crossFade()
                    .thumbnail(0.5f)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(img);*/

        }

        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);

        Log.d("Response","Response"+itemModel);

    }

    @Override
    public void onFailure(Call<ItemModel> call, Throwable t) {
        Toast.makeText(getActivity(),"Failed !",Toast.LENGTH_LONG).show();

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



}
