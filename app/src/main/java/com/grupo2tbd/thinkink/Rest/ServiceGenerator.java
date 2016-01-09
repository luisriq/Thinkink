package com.grupo2tbd.thinkink.Rest;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by luis on 12/20/15.
 */
public class ServiceGenerator {

    public static final String API_BASE_URL = "http://192.168.0.9:8080/Think-INK/rest/";

    private static OkHttpClient httpClient = new OkHttpClient();
    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.interceptors().add(interceptor);

        Retrofit retrofit = builder.client(httpClient).build();
        return retrofit.create(serviceClass);
    }
}