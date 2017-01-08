package com.example.dell.aryashitlist;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by kriti on 05-01-2017.
 */

public class FragmentMethod extends Fragment {

    TextView fragmentTextView;
    ImageView fragmentImageView;
    RelativeLayout fragmentRelativeLayout;
    String name;
    String status;
    String description;
    int img_location;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout,container,false);
        Bundle bundle = getArguments();
        name = bundle.getString("name");
        status = bundle.getString("status");
        description = bundle.getString("description");
        img_location = bundle.getInt("img_location");
        getIDs(view);
        setEvents();
        return view;
    }

    private void setEvents() {
        fragmentTextView.setText(description);
        fragmentImageView.setImageResource(img_location);
        if(status.equals("dead")){
            fragmentRelativeLayout.setBackgroundColor(Color.RED);
        }
        else if(status.equals("alive")){
            fragmentRelativeLayout.setBackgroundColor(Color.GREEN);
        }
    }

    private void getIDs(View view){
        fragmentTextView = (TextView)view.findViewById(R.id.fragmentTextView);
        fragmentImageView = (ImageView)view.findViewById(R.id.fragmentImageView);
        fragmentRelativeLayout = (RelativeLayout)view.findViewById(R.id.fragmentRlativeLayout);
    }
}
