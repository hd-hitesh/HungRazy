package com.example.hungrazy;

public class ExampleItem {
    private String imageUrl;
    private String name;
    private String locality;


    public ExampleItem(String imageUrl, String text1, String text2) {
        this.imageUrl = imageUrl;
        name = text1;
        locality = text2;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getLocality() {
        return locality;
    }
}