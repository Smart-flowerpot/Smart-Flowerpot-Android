package com.omer.user.smartflowerpot.RestApi;

import com.omer.user.smartflowerpot.Models.Plant;
import com.omer.user.smartflowerpot.Models.Response;
import com.omer.user.smartflowerpot.Models.Result;

import retrofit2.Call;

public class ManagerAll extends BaseManager {

    private static ManagerAll ourInstance = new ManagerAll();

    public static synchronized ManagerAll getInstance() {
        return ourInstance;
    }

    public Call<Plant> getPlant(int ID) {
        Call<Plant> call = getRestApiClient().getPlant(ID);
        return call;
    }

    public Call<Response> updateWaterStatus(int ID, int value) {
        Call<Response> call = getRestApiClient().updateWaterStatus(ID, value);
        return call;
    }

    public Call<Result> getConstantPlantData() {
        Call<Result> call = getRestApiClient().getConstantPlantData();
        return call;
    }

    public Call<Response> sendNotification(String message){
        Call<Response> call = getRestApiClient().sendNotification(message);
        return call;
    }
}
