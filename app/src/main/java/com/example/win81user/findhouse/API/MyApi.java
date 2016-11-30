package com.example.win81user.findhouse.API;


import com.example.win81user.findhouse.Model.ItemModel;

import retrofit2.Call;
import retrofit2.http.GET;



public interface MyApi {
    @GET("examplemakewebsercie.php")
    Call<ItemModel> getShout();
}
