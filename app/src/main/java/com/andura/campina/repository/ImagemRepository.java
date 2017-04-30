package com.andura.campina.repository;

import android.content.Context;

import com.andura.campina.image.Image;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amaro on 29/04/17.
 */

public class ImagemRepository {

    List<Image> list;
    String city;
    private static ImagemRepository instance;

    private ImagemRepository(){
        list= new ArrayList<Image>();

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

}
