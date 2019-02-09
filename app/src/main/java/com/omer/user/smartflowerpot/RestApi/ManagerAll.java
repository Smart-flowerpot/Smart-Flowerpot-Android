package com.omer.user.smartflowerpot.RestApi;

import com.omer.user.smartflowerpot.Models.Plant;

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


}
