package com.example.win81user.findhouse.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.win81user.findhouse.API.MyApi;
import com.example.win81user.findhouse.Model.ItemModel;
import com.example.win81user.findhouse.Model.Property;
import com.example.win81user.findhouse.R;
import com.example.win81user.findhouse.Utility.ClickListener;
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
public class ShowDetailFragment extends Fragment implements Callback<ItemModel>,ClickListener  {
    private TextView description,txtdetail,tv_message;
    private ImageView img;
    private ArrayList<Property> data;
    Retrofit retrofit;
    private   ItemModel itemModel;
    private Toolbar toolbar;
    String API = "http://192.168.25.2:8181/FindHouse/webservice/";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.detailprop_layout,container,false);
        initViews(view);
        prepareservice();

        return view;
    }


    private void initViews(View view){

        txtdetail = (TextView)view.findViewById(R.id.txtdetail);
        description = (TextView)view.findViewById(R.id.description);
        img = (ImageView)view.findViewById(R.id.detailimg);
        toolbar = (Toolbar)view.findViewById(R.id.toolbardetail);
        toolbar.setTitle("Detail");
        toolbar.setNavigationIcon(R.drawable.ic_navigate_before_black_36dp);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });


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
            txtdetail.setText(data.get(i).getPropertyname());
            description.setText(data.get(i).getDescription()+"\n"+data.get(i).getPrice()+"\n"+data.get(i).getContact());

            Glide.with(this).load(data.get(i).getImage3())
                    .crossFade()
                    .thumbnail(0.5f)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(img);

        }

        Log.d("Response","Response"+itemModel);

    }

    @Override
    public void onFailure(Call<ItemModel> call, Throwable t) {
        Toast.makeText(getActivity(),"Failed !",Toast.LENGTH_LONG).show();

    }
}
