package com.krawa.estafetatest.network;

import android.util.Base64;

import com.krawa.estafetatest.App;
import com.krawa.estafetatest.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {

    private final static String BASE_URL = "http://amt2.estafeta.org/api/";
    private final static String CREDENTIALS = "admin@9F346DDB-8FF8-4F42-8221-6E03D6491756:1";
    private final static String BASIC_AUTH = "Basic " + Base64.encodeToString(CREDENTIALS.getBytes(), Base64.NO_WRAP);

    private static RestClient instance;
    private RestAPI restAPI;

    protected RestClient(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(provideOkHttpClient())
                .addConverterFactory(getConverter())
                .build();

        restAPI = retrofit.create(RestAPI.class);
    }

    public static RestAPI get() {
        if(instance == null) instance = new RestClient();
        return instance.restAPI;
    }

    private Converter.Factory getConverter() {
        return GsonConverterFactory.create();
    }

    private OkHttpClient provideOkHttpClient(){
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        clientBuilder.addInterceptor(provideAuthInterceptor());
        clientBuilder.addInterceptor(provideOfflineCacheInterceptor());
        clientBuilder.cache(provideCache());
        return clientBuilder.build();
    }

    private Interceptor provideAuthInterceptor() {
        return new Interceptor(){
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();

                Request.Builder requestBuilder = original.newBuilder()
                        .header("Authorization", BASIC_AUTH)
                        .header("Accept", "application/json")
                        .method(original.method(), original.body());

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        };
    }

    public static Interceptor provideOfflineCacheInterceptor () {
        return new Interceptor() {
            @Override
            public Response intercept (Chain chain) throws IOException {
                Request request = chain.request();

                if (!Utils.isOnline(App.getAppContext())){
                    CacheControl cacheControl = new CacheControl.Builder()
                            .maxStale(7, TimeUnit.DAYS)
                            .build();

                    request = request.newBuilder()
                            .cacheControl(cacheControl)
                            .build();
                }

                return chain.proceed(request);
            }
        };
    }

    private static Cache provideCache(){
        Cache cache = null;
        try {
            cache = new Cache(new File(App.getAppContext().getCacheDir(), "http-cache"),
                    10 * 1024 * 1024 ); // 10 MB
        }catch (Exception e){
            e.printStackTrace();
        }
        return cache;
    }

}
