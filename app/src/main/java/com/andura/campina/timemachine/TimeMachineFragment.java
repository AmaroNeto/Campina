package com.andura.campina.timemachine;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andura.campina.R;
import com.andura.campina.image.Image;
import com.andura.campina.model.GDP;
import com.andura.campina.model.Vegetation;
import com.andura.campina.repository.ImagemRepository;
import com.andura.campina.util.AppVar;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amaro on 29/04/17.
 */

public class TimeMachineFragment extends Fragment {

    public static final String TAG_FRAGMENT = "Tag_TIME_MACHINE";
    int position;
    Image image;
    ImagemRepository repository;
    ImageView mainImage;



    public static TimeMachineFragment newInstance(int position) {
        TimeMachineFragment fragmentFirst = new TimeMachineFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        position = getArguments().getInt("position", 0);

        Log.d(AppVar.DEBUG,"POSITION "+position);

        repository = ImagemRepository.getInstance();
        image = repository.getImage(position);
        //image = getImages().get(position);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.timemachine_fragment, container, false);
        layout.setTag(TAG_FRAGMENT);

        Toolbar toolbar = (Toolbar) layout.findViewById(R.id.toolbar);
        TextView title = (TextView) layout.findViewById(R.id.toolbar_title);
        ImageView icon = (ImageView) layout.findViewById(R.id.image_icon);
        icon.setVisibility(View.GONE);
        //title.setTextSize(20);
        //title.setText("Recife");

        ( (AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ( (AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        ( (AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ( (AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mainImage = (ImageView) layout.findViewById(R.id.expandedImage);

        Picasso
                .with(getActivity())
                //.load(image.getUrl())
                .load(image.getUrl())
                .placeholder(R.drawable.progress_animation)
                .error(android.R.drawable.stat_notify_error)
                //.memoryPolicy(MemoryPolicy.NO_CACHE )
                //.networkPolicy(NetworkPolicy.NO_CACHE)
                .fit() // will explain later
                .into(mainImage);

        TextView city =(TextView) layout.findViewById(R.id.city);
        city.setText("Recife, "+image.getTitle());

        TextView pib_tag =(TextView) layout.findViewById(R.id.pib_tag);
        TextView pib =(TextView) layout.findViewById(R.id.pib);

        GDP gdp = repository.findGDPByYear(image.getTitle());
        pib.setText(gdp.getGdp()+"");


        TextView veg =(TextView) layout.findViewById(R.id.vegetation);
        TextView veg_tag =(TextView) layout.findViewById(R.id.vegetation_tag);

        Vegetation v = repository.findVegetationByYear(image.getTitle());

        int value = (int) (((v.getVegetation()+2)/12) * 100);

        veg.setText(value+"%");

        TextView info =(TextView) layout.findViewById(R.id.info);
        info.setText(repository.getWikipedia());


        Typeface typeFace= Typeface.createFromAsset(getActivity().getAssets(),"fonts/Queen of Camelot.otf");
        city.setTypeface(typeFace);
        pib_tag.setTypeface(typeFace);
        pib.setTypeface(typeFace);

        veg.setTypeface(typeFace);
        veg_tag.setTypeface(typeFace);
        //info.setTypeface(typeFace);




        return layout;
    }

    private List<Image> getImages(){

        List<Image> result = new ArrayList<Image>();

        //Image im1 = new Image("2017","https://static.pexels.com/photos/2422/sky-earth-galaxy-universe.jpg");
        Image im2 = new Image("2016","https://static.pexels.com/photos/2422/sky-earth-galaxy-universe.jpg");
        Image im3 = new Image("2015","https://static.pexels.com/photos/2422/sky-earth-galaxy-universe.jpg");
        Image im4 = new Image("2014","https://static.pexels.com/photos/2422/sky-earth-galaxy-universe.jpg");


        //result.add(im1);
        result.add(im2);
        result.add(im3);
        result.add(im4);

        return result;
    }

}
