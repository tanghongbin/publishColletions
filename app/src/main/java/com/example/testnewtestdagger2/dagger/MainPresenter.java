package com.example.testnewtestdagger2.dagger;

import android.util.Log;
import com.example.testnewtestdagger2.MainActivity;

import javax.inject.Inject;

public class MainPresenter {

    private MainModel mainModel;
    private MainActivity mainView;


    @Inject
    public MainPresenter(MainModel mainModel, MainActivity mainView) {
        this.mainModel = mainModel;
        this.mainView = mainView;
    }


    public void requestEmail(){
        Log.i("TAG","打印   requestEmail-------------》 "+ mainModel.get());
    }
}
