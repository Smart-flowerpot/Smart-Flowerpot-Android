package com.omer.user.smartflowerpot.RestApi;

public class BaseManager {

    protected RestApi getRestApiClient(){
        RestApiClient restApiClient = new RestApiClient(BaseUrl.Data_url);
        return  restApiClient.getRestApi();
    }
}
