package com.example.testnewtestdagger2.dagger;


import com.example.testnewtestdagger2.BaseApplication;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

import javax.inject.Singleton;

@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        AppModule.class
})
public interface AppComponent {

    void inject(BaseApplication application);

}
