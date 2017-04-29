package com.andura.campina.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.andura.campina.R;
import com.andura.campina.image.Image;
import com.andura.campina.image.ImageAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amaro on 29/04/17.
 */

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    ImageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        adapter = new ImageAdapter(this,getImages());

        mRecyclerView = (RecyclerView) findViewById(R.id.images);
        //RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this,2);
       //RecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));

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



}
