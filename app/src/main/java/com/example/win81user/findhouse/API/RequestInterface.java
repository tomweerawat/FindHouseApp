package com.example.win81user.findhouse.API;


import com.example.win81user.findhouse.Model.ServerRequest;
import com.example.win81user.findhouse.Model.ServerResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RequestInterface {

    @POST("FindHouse/webservice/user/")
    Call<ServerResponse> operation(@Body ServerRequest request);

}
