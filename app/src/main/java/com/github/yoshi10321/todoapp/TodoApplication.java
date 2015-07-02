package com.github.yoshi10321.todoapp;


import com.activeandroid.app.Application;
import com.facebook.stetho.Stetho;

/**
 * Created by mitsui yoshito on 2015/07/01.
 */
public class TodoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(
                                Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(
                                Stetho.defaultInspectorModulesProvider(this))
                        .build());
    }
}
