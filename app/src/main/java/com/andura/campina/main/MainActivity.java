package com.andura.campina.main;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.andura.campina.R;
import com.andura.campina.image.Image;
import com.andura.campina.image.ImageAdapter;
import com.andura.campina.repository.ImagemRepository;
import com.github.lzyzsd.circleprogress.ArcProgress;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amaro on 29/04/17.
 */

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    ImageAdapter adapter;
    ImagemRepository repository;
    ArcProgress arc;

    ImageView mainImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity2);



        repository = ImagemRepository.getInstance();

        adapter = new ImageAdapter(this,repository.getImages());

        mainImage = (ImageView) findViewById(R.id.expandedImage);
        arc = (ArcProgress) findViewById(R.id.arc_progress);

        Picasso
                .with(this)
                .load(repository.getImages().get(0).getUrl())
                .placeholder(R.drawable.progress_animation)
                .error(android.R.drawable.stat_notify_error)
                .fit() // will explain later
                .into(mainImage);

        TextView myTextView=(TextView)findViewById(R.id.city);
        Typeface typeFace= Typeface.createFromAsset(getAssets(),"fonts/Queen of Camelot.otf");
        myTextView.setTypeface(typeFace);

        arc.setBottomText("Vegetation");
        arc.setProgress(76);
        //arc.setUnfinishedStrokeColor(android.R.color.background_light);
        //arc.setTextColor(android.R.color.white);

       mRecyclerView = (RecyclerView) findViewById(R.id.images);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
       mRecyclerView.setLayoutManager(mLayoutManager);
        //mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));

        //mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 30, true));
        mRecyclerView.setAdapter(adapter);
    }

    private List<Image> getImages(){

        List<Image> result = new ArrayList<Image>();

        Image im1 = new Image("2010","https://earthengine.googleapis.com/api/thumb?thumbid=3acd8ae148de56d514440b39aae734db&token=4a12f712ce8182c0a457611a23d54a51");
        Image im2 = new Image("2011","https://earthengine.googleapis.com/api/thumb?thumbid=3acd8ae148de56d514440b39aae734db&token=4a12f712ce8182c0a457611a23d54a51");
        Image im3 = new Image("2014","https://earthengine.googleapis.com/api/thumb?thumbid=3acd8ae148de56d514440b39aae734db&token=4a12f712ce8182c0a457611a23d54a51");

        result.add(im1);
        result.add(im2);
        result.add(im3);

        return result;
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        //repository.clean();
    }




}
