package com.krawa.estafetatest.di.component;

import com.krawa.estafetatest.MainActivity;
import com.krawa.estafetatest.di.module.AppModule;
import com.krawa.estafetatest.di.module.NetModule;
import com.krawa.estafetatest.di.scope.PerApplication;

import dagger.Component;

@Component(modules = {AppModule.class, NetModule.class})
@PerApplication
public interface AppComponent {
    void inject(MainActivity mainActivity);
}
