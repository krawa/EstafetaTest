package com.krawa.estafetatest;

import android.app.Application;
import android.content.Context;

import com.krawa.estafetatest.di.component.AppComponent;
import com.krawa.estafetatest.di.component.DaggerAppComponent;
import com.krawa.estafetatest.di.module.AppModule;
import com.krawa.estafetatest.di.module.NetModule;

public class App extends Application{

    private static Context mContext;

    private static AppComponent appComponent;

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        appComponent = buildComponent();
    }

    private AppComponent buildComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule())
                .build();
    }

    @Deprecated
    public static Context getAppContext() {
        return mContext;
    }

}
