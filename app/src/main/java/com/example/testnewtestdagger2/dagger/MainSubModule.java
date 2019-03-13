package com.example.testnewtestdagger2.dagger;


import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class MainSubModule {

    private MainView mainView;

    @Provides
    @ActivityScope
    public static TestBean provideBean(){
        return new TestBean("吃好喝好");
    }

    @Binds
    public abstract HelloBeanInterface bind(HelloBean helloBean);
//

}
