package com.krawa.estafetatest.network;

import android.content.Context;

import com.krawa.estafetatest.utils.Utils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class OfflineCacheInterceptor implements Interceptor{

    private final static int MAX_STALE = 7;

    private final Context appContext;

    public OfflineCacheInterceptor (Context appContext){
        this.appContext = appContext;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        if (!Utils.isOnline(appContext)){
            CacheControl cacheControl = new CacheControl.Builder()
                    .maxStale(MAX_STALE, TimeUnit.DAYS)
                    .build();

            request = request.newBuilder()
                    .cacheControl(cacheControl)
                    .build();
        }

        return chain.proceed(request);
    }
}
