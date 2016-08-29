package com.krawa.estafetatest.di.module;

import android.content.Context;
import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.krawa.estafetatest.App;
import com.krawa.estafetatest.di.scope.PerApplication;
import com.krawa.estafetatest.network.RestAPI;
import com.krawa.estafetatest.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetModule {

    private final static String BASE_URL = "http://amt2.estafeta.org/api/";
    private final static String CREDENTIALS = "admin@9F346DDB-8FF8-4F42-8221-6E03D6491756:1";
    private final static String BASIC_AUTH = "Basic " + Base64.encodeToString(CREDENTIALS.getBytes(), Base64.NO_WRAP);

    @Provides
    @PerApplication
    OkHttpClient provideOkHttpClient(Cache cache){
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        clientBuilder.addInterceptor(logging);
        clientBuilder.addInterceptor(getAuthInterceptor());
        clientBuilder.addInterceptor(getOfflineCacheInterceptor());
        clientBuilder.cache(cache);
        return clientBuilder.build();
    }

    private Interceptor getAuthInterceptor() {
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

    private Interceptor getOfflineCacheInterceptor () {
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

    @Provides
    @PerApplication
    Cache provideCache(Context context){
        Cache cache = null;
        try {
            cache = new Cache(new File(context.getCacheDir(), "http-cache"),
                    10 * 1024 * 1024 ); // 10 MB
        }catch (Exception e){
            e.printStackTrace();
        }
        return cache;
    }

    @Provides
    @PerApplication
    Gson provideGson() {
        return new GsonBuilder().create();
    }

    @Provides
    @PerApplication
    Retrofit provideRetrofit(OkHttpClient okHttpClient, Gson gson) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .build();
    }

    @Provides
    @PerApplication
    RestAPI provideRestAPI(Retrofit retrofit){
        return retrofit.create(RestAPI.class);
    }
}
