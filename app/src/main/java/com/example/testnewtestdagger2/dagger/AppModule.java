package com.example.testnewtestdagger2.dagger;


import com.example.testnewtestdagger2.MainActivity;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

import javax.inject.Singleton;

@Module
public abstract class AppModule {


    @ActivityScope
    @ContributesAndroidInjector(modules =MainSubModule.class)
    public abstract MainActivity contribute();


    @Singleton
    @Provides
    public static HomeTest provideTest(){
        return new HomeTest();
    }

}
