package com.fcouceiro.pepesticket;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.fcouceiro.pepesticket.communication.ApiService;
import com.fcouceiro.pepesticket.communication.AuthenticationInterceptor;
import com.fcouceiro.pepesticket.communication.models.Service;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RestTestActivity extends AppCompatActivity {

    private static final String BASE_URL = "http://6573a0d8.ngrok.io/market/rest/endpoints/";
    private static final String TAG = "RestTestActivity";

    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_test);

        // Check if user has an user identification already
        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String userId = sharedPref.getString(getString(R.string.preference_user_id_key), null);

        // Create REST service
        this.apiService = createApiService(userId);

        // Request user id if needed
        if(userId == null) {
            this.apiService.authenticate().enqueue(authenticationCallback);
        }
    }

    private ApiService createApiService(String userId) {
        // Set authentication interceptor
        AuthenticationInterceptor.getInstance().setUserId(userId);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(AuthenticationInterceptor.getInstance());

        // Create the service
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(ApiService.class);
    }

    private Callback<String> authenticationCallback = new Callback<String>() {
        @Override
        public void onResponse(Call<String> call, Response<String> response) {
            if(!response.isSuccessful()){
                Toast.makeText(getBaseContext(), getString(R.string.authentication_failed), Toast.LENGTH_LONG).show();
                return;
            }

            Log.d(TAG, "User id: " + response.body());
        }

        @Override
        public void onFailure(Call<String> call, Throwable t) {
            Toast.makeText(getBaseContext(), getString(R.string.authentication_failed), Toast.LENGTH_LONG).show();
        }
    };
}
