package com.churchapp.ami.menufragments.homefragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.churchapp.ami.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Statement extends Fragment {


    public Statement() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.statement, container, false);
    }

}
