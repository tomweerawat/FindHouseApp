package com.example.win81user.findhouse.API;


import com.example.win81user.findhouse.Model.ItemModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface MyApi {
    @GET("index.php")
    Call<ItemModel> getShout();

    @GET("index.php")
    Call<ItemModel> getFilter(@Query("price") String price);
}
