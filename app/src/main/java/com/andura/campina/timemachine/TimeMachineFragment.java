package com.andura.campina.timemachine;

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
import com.andura.campina.repository.ImagemRepository;
import com.andura.campina.util.AppVar;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.timemachine_fragment, container, false);
        layout.setTag(TAG_FRAGMENT);

        Toolbar toolbar = (Toolbar) layout.findViewById(R.id.toolbar);
        TextView title = (TextView) layout.findViewById(R.id.toolbar_title);
        ImageView icon = (ImageView) layout.findViewById(R.id.image_icon);
        icon.setVisibility(View.GONE);
        title.setTextSize(20);
        title.setText("Recife");

        ( (AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ( (AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        ( (AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ( (AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mainImage = (ImageView) layout.findViewById(R.id.expandedImage);

        Picasso
                .with(getActivity())
                .load(image.getUrl())
                .placeholder(R.drawable.progress_animation)
                .error(android.R.drawable.stat_notify_error)
                //.memoryPolicy(MemoryPolicy.NO_CACHE )
                //.networkPolicy(NetworkPolicy.NO_CACHE)
                .fit() // will explain later
                .into(mainImage);



        return layout;
    }

}
