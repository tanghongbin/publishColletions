package com.example.testnewtestdagger2.dagger;

public class TestBean {
    public String name;

    public TestBean(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
