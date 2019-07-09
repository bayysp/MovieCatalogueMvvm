package com.example.asus.subthreemvvm.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.asus.subthreemvvm.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvshowFragment extends Fragment {


    public TvshowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("TvshowFragment","OnCreateView");
        return inflater.inflate(R.layout.fragment_tvshow, container, false);
    }

}
