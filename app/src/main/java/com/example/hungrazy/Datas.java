package com.example.hungrazy;

public class Datas {

    private String name;
    private String locality;
    private String city;
    private float service;
    private float food;
    private float ambiance;
    private String imageid;
    private String imageUrl;
    private double lattitude;
    private double longitude;
    private int numOfReview;
    private float avgReview;
    private String otherSuggestion;
    private String type;
    private float price;

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Datas() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public float getService() {
        return service;
    }

    public void setService(float service) {
        this.service = service;
    }

    public float getFood() {
        return food;
    }

    public void setFood(float food) {
        this.food = food;
    }

    public float getAmbiance() {
        return ambiance;
    }

    public void setAmbiance(float ambiance) {
        this.ambiance = ambiance;
    }

    public String getImageid() {
        return imageid;
    }

    public void setImageid(String imageid) {
        this.imageid = imageid;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getLattitude() {
        return lattitude;
    }

    public void setLattitude(double lattitude) {
        this.lattitude = lattitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getNumOfReview() {
        return numOfReview;
    }

    public void setNumOfReview(int numOfReview) {
        this.numOfReview = numOfReview;
    }

    public float getAvgReview() {
        return avgReview;
    }

    public void setAvgReview(float avgReview) {
        this.avgReview = avgReview;
    }

    public String getOtherSuggestion() {
        return otherSuggestion;
    }

    public void setOtherSuggestion(String otherSuggestion) {
        this.otherSuggestion = otherSuggestion;
    }
}
