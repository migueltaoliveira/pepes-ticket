package com.fcouceiro.pepesticket.communication;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by franciscocouceiro on 03/06/17.
 */

public class API {

    private static final String BASE_URL = "https://cc9578d7.ngrok.io/market/rest/endpoints/";

    private static ApiService apiService;

    public static ApiService getService() {
        return apiService;
    }

    public static void initialize(String userId) {
        // Set authentication interceptor
        AuthenticationInterceptor.getInstance().setUserId(userId);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(AuthenticationInterceptor.getInstance());

        // Create the service
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClient.build())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }
}
