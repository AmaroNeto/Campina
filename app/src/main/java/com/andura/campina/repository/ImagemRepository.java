package com.andura.campina.repository;

import android.content.Context;

import com.andura.campina.image.Image;
import com.andura.campina.model.GDP;
import com.andura.campina.model.Vegetation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amaro on 29/04/17.
 */

public class ImagemRepository {

    List<Image> list;
    List<Vegetation> vegetations;
    List<GDP> gdps;
    String city;
    String wikipedia;
    private static ImagemRepository instance;

    private ImagemRepository(){
        list= new ArrayList<Image>();
        vegetations = new ArrayList<Vegetation>();
        gdps = new ArrayList<GDP>();

    }

    public static synchronized ImagemRepository getInstance(){

        if(instance != null){
            return instance;
        }

        instance = new ImagemRepository();
        return instance;
    }

    public void save(Image image){
        list.add(image);
    }

    public void clean(){
        list.clear();
        vegetations.clear();
        gdps.clear();
    }

    public List<Image> getImages(){

        return list;
    }

    public Image getImage(int position){

        return list.get(position);
    }

    public void setCity(String city){
        this.city = city;
    }

    public String getCity(){
        return this.city;
    }

    public String getWikipedia() {
        return wikipedia;
    }

    public void setWikipedia(String wikipedia) {
        this.wikipedia = wikipedia;
    }

    public void save(GDP gdp){
        gdps.add(gdp);
    }

    public void save(Vegetation vegetation){
        vegetations.add(vegetation);
    }

    public Vegetation getVegetation(int position){
        return vegetations.get(position);
    }

    public GDP getGDP(int position){
        return gdps.get(position);
    }

    public GDP findGDPByYear(String year){
        for(GDP gdp : gdps){
            if(gdp.getYear().equals(year)){
                return gdp;
            }
        }

        return null;
    }

    public Vegetation findVegetationByYear(String year){
        for(Vegetation gdp : vegetations){
            if(gdp.getYear().equals(year)){
                return gdp;
            }
        }

        return null;
    }
}
