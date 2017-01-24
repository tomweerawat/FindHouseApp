package com.example.win81user.findhouse;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.win81user.findhouse.API.MyApi;
import com.example.win81user.findhouse.Activity.MainActivity;
import com.example.win81user.findhouse.Adapter.FeedAdapter;
import com.example.win81user.findhouse.Model.ItemModel;
import com.example.win81user.findhouse.Model.Property;
import com.example.win81user.findhouse.Utility.ClickListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class ShowFeed extends Fragment implements Callback<ItemModel>,ClickListener, SearchView.OnQueryTextListener {

    public final static String ITEMS_COUNT_KEY = "PartThreeFragment$ItemsCount";
    Retrofit retrofit;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView recyclerView;
    private FeedAdapter dataAdapter;
    private ArrayList<Property> data;
    Context context;
    //http://192.168.25.2:8181/weerawat/ https://weerawatcomsci.github.io/feed/
    String API = "http://192.168.25.2:8181/FindHouse/webservice/";

    public static ShowFeed createInstance(int itemsCount) {
        ShowFeed partThreeFragment = new ShowFeed();
        Bundle bundle = new Bundle();
        bundle.putInt(ITEMS_COUNT_KEY, itemsCount);
        partThreeFragment.setArguments(bundle);
        return partThreeFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_show_feed, container, false);
        setupRecyclerView();
        staranimation(recyclerView);
//        recyclerView.setAdapter(dataAdapter);
        return recyclerView;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        setHasOptionsMenu(true);
        String[] locales = Locale.getISOCountries();
        data = new ArrayList<>();



        dataAdapter = new FeedAdapter(getContext(),data);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(dataAdapter);
    }

    private void staranimation(RecyclerView recyclerView){
        YoYo.with(Techniques.FadeInUp)
                .duration(3000)
                .playOn(recyclerView.findViewById(R.id.recyclerView));
    }
    private void setupRecyclerView() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Log.e("OkHttpClient","connected"+client);
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
        Log.e("apicall","connected"+retrofit);

    }

    private void apiCall(Retrofit retrofit) {
        MyApi myApi = retrofit.create(MyApi.class);
        Call<ItemModel> call = myApi.getShout();
        call.enqueue(this);
        Log.e("apiCall","Success Call");
    }

    @Override
    public void onResponse(Call<ItemModel> call, Response<ItemModel> response) {
        ItemModel itemModel = response.body();
        data = new ArrayList<>(Arrays.asList(itemModel.getProperty()));

        Log.e("data",itemModel.getProperty()+"");
        dataAdapter = new FeedAdapter(context,data);
        recyclerView.setAdapter(dataAdapter);
        dataAdapter.setClickListener(this);
    }

    @Override
    public void onFailure(Call<ItemModel> call, Throwable t) {
        Toast.makeText(getActivity(),"Failed !",Toast.LENGTH_LONG).show();
    }


    @Override
    public void itemClicked(View view, int position) {

        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.putExtra("ItemPosition", position);
        startActivity(intent);
       /* Bundle args = new Bundle();
        Fragment socialFragment = new TestFragment();
        args.putInt("ItemPosition",position);
        socialFragment.setArguments(args);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame,socialFragment);
        ft.commit();*/


        /* int[] num =new int[position];
          for(int i = 0 ;i<=num.length;i++){
             Log.d("GGGGGGGGGGGGGGgg","GGGGGG"+i);

          }*/
       /* switch (position){
            case 0:
                Toast.makeText(getContext(),"Click"+position,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                break;
            case 1:Toast.makeText(getContext(),"tom"+position,Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getActivity(), MainActivity.class);
                startActivity(i);
                break;
        }*/

    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);

        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);

        MenuItemCompat.setOnActionExpandListener(item,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        // Do something when collapsed


                        dataAdapter.setFilter(data);
                        return true; // Return true to collapse action view
                    }

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        // Do something when expanded
                        return true; // Return true to expand action view
                    }
                });
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final List<Property> filteredModelList = filter(data, newText);
//        dataAdapter.setFilter(filteredModelList);
        dataAdapter.getFilter().filter(newText);
        return false;
    }

    private List<Property> filter(ArrayList<Property> models, String query) {
        query = query.toLowerCase();


        final List<Property> filteredModelList = new ArrayList<>();
        for (Property model : models) {
            final String text = model.getPropertyname().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }
}