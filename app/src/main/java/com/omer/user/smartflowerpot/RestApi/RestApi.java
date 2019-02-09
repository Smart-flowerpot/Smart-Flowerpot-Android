package com.omer.user.smartflowerpot.RestApi;

import com.omer.user.smartflowerpot.Models.Plant;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestApi {

    @GET("get_plant.php")
    Call<Plant> getPlant(@Query("id") int plant_int);

}

