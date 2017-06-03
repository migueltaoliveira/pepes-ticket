package com.fcouceiro.pepesticket.communication;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by franciscocouceiro on 03/06/17.
 */

public class AuthenticationInterceptor implements Interceptor{
    private static final AuthenticationInterceptor ourInstance = new AuthenticationInterceptor();

    public static AuthenticationInterceptor getInstance() {
        return ourInstance;
    }

    private static String USER_ID_QUERY_PARAM = "userId";
    private String userId;

    private AuthenticationInterceptor(){
        this.userId = null;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        // Don't inject if userId isn't available
        if(this.userId == null) {
            return chain.proceed(chain.request());
        }

        // Inject otherwise
        Request original = chain.request();
        HttpUrl originalHttpUrl = original.url();

        HttpUrl url = originalHttpUrl.newBuilder()
                .addQueryParameter(USER_ID_QUERY_PARAM, this.userId)
                .build();

        Request.Builder requestBuilder = original.newBuilder()
                .url(url);

        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}
