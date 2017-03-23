package com.example.win81user.findhouse.API;

import com.example.win81user.findhouse.Model.ItemModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Win81 User on 9/3/2560.
 */

public interface NearbyApi {
    @GET("getnearbysearch.php")
    Call<ItemModel> getNearbyPlaces(@Query("location") String location, @Query("radius") int radius);
}
