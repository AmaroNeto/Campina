package com.andura.campina.timemachine;

import com.andura.campina.image.Image;

/**
 * Created by amaro on 29/04/17.
 */

public class TimeMachine {

    private String place;
    private String imageUrl;
    private long lat;
    private long lng;

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getImage() {
        return imageUrl;
    }

    public void setImage(String image) {
        this.imageUrl = image;
    }

    public long getLat() {
        return lat;
    }

    public void setLat(long lat) {
        this.lat = lat;
    }

    public long getLng() {
        return lng;
    }

    public void setLng(long lng) {
        this.lng = lng;
    }
}
