package com.churchapp.ami.menufragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.churchapp.ami.R;
import com.churchapp.ami.menufragments.homecellfragments.HomecellTeachingGuide;
import com.churchapp.ami.menufragments.homecellfragments.HomecellWeekNumber;
import com.churchapp.ami.menufragments.homecellfragments.WhatsHomecell;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeCells extends Fragment {
    Fragment fragment = null;
    TabLayout tabLayout;
    ViewPager viewPager;

    public HomeCells() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.home_cells, container, false);
        viewPager = view.findViewById(R.id.homeCellViewPager);
        tabLayout = view.findViewById(R.id.homeCellTabLayout);
        MyHomeCellAdapter adapter = new MyHomeCellAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

    class MyHomeCellAdapter extends FragmentStatePagerAdapter{
        public MyHomeCellAdapter(FragmentManager fragmentManager){
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {

            if(position == 0){
               fragment = new WhatsHomecell();
            }
            if(position == 1){
                fragment = new HomecellTeachingGuide();
            }
            if(position == 2){
                fragment = new HomecellWeekNumber();
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            String name = null;
            if(position == 0){
                name = "HOMECELL DEFINITION";
            }
            if(position == 1){
                name = "TEACHING GUIDE";
            }
            if(position == 2){
                name = "WEEK NUMBER";
            }
            return name;
        }
    }
}
