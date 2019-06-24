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
import com.churchapp.ami.menufragments.homefragments.Pastors;
import com.churchapp.ami.menufragments.homefragments.Statement;
import com.churchapp.ami.menufragments.homefragments.Vision;
import com.churchapp.ami.menufragments.homefragments.Welcome;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    Fragment fragment = null;
    TabLayout tabLayout;
    ViewPager viewPager;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        viewPager = view.findViewById(R.id.homeViewPager);
        tabLayout = view.findViewById(R.id.homeTabLayout);
        MyAdapter adapter = new MyAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

    class MyAdapter extends FragmentStatePagerAdapter{

        public MyAdapter(FragmentManager fragmentManager){
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            if(position == 0){
                fragment = new Welcome();
            }
            if(position == 1){
                fragment = new Vision();
            }
            if(position == 2){
                fragment = new Statement();
            }
            if(position == 3){
                fragment = new Pastors();
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            String name = null;
            if(position == 0){
                name = "WELCOME TO AMI";
            }
            if(position == 1){
                name = "OUR VISION AND MISSION";
            }
            if(position == 2){
                name = "STATEMENT OF FAITH";
            }
            if(position == 3){
                name = "PASTORS";
            }
            return name;
        }
    }
}
