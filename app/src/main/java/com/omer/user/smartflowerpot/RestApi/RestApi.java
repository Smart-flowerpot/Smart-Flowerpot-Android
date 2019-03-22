package com.omer.user.smartflowerpot.RestApi;

import com.omer.user.smartflowerpot.Models.AllachievementsItem;
import com.omer.user.smartflowerpot.Models.Plant;
import com.omer.user.smartflowerpot.Models.Response;
import com.omer.user.smartflowerpot.Models.Result;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RestApi {

    @GET("get_plant.php")
    Call<Plant> getPlant(@Query("id") int plant_id);

    @FormUrlEncoded
    @POST("update_water_engine.php")
    Call<Response> updateWaterStatus(@Field("plant_id") int plant_id, @Field("water") int value);

    @GET("plantdata.json")
    Call<Result> getConstantPlantData();

    @GET("send_notification.php")
    Call<Response> sendNotification(@Query("message") String message);

    @GET("achievements.json")
    Call<AllachievementsItem> getAchievements();
}

