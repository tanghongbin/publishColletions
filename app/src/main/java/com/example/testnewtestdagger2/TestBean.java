package com.example.testnewtestdagger2;

import java.util.List;

public class TestBean {

    private String url;
    private List<String> image;

    public TestBean(String url, List<String> image) {
        this.url = url;
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getImage() {
        return image;
    }

    public void setImage(List<String> image) {
        this.image = image;
    }
}
