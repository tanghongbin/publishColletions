package com.example.testnewtestdagger2.dagger;

import javax.inject.Inject;

public class MainModel {

    @Inject
    public MainModel() {
    }

    public String get(){
        return "hello";
    }
}
