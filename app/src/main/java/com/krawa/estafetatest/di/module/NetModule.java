package com.krawa.estafetatest.di.module;

import android.content.Context;
import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.krawa.estafetatest.di.scope.PerApplication;
import com.krawa.estafetatest.network.AuthInterceptor;
import com.krawa.estafetatest.network.OfflineCacheInterceptor;
import com.krawa.estafetatest.network.RestAPI;

import java.io.File;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetModule {

    private final static String BASE_URL = "http://amt.estafeta.org/api/";
    private final static String CREDENTIALS = "admin@9F346DDB-8FF8-4F42-8221-6E03D6491756:1";
    private final static String BASIC_AUTH = "Basic " + Base64.encodeToString(CREDENTIALS.getBytes(), Base64.NO_WRAP);

    @Provides
    @PerApplication
    OkHttpClient provideOkHttpClient(Cache cache, AuthInterceptor authInterceptor, OfflineCacheInterceptor offlineCacheInterceptor){
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        clientBuilder.addInterceptor(logging);
        clientBuilder.addInterceptor(authInterceptor);
        clientBuilder.addInterceptor(offlineCacheInterceptor);
        clientBuilder.cache(cache);
        return clientBuilder.build();
    }

    @Provides
    @PerApplication
    AuthInterceptor provideAuthInterceptor() {
        return new AuthInterceptor(BASIC_AUTH);
    }

    @Provides
    @PerApplication
    OfflineCacheInterceptor provideOfflineCacheInterceptor(Context context) {
        return new OfflineCacheInterceptor(context);
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
