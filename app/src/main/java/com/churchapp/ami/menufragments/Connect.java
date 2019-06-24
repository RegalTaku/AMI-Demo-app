package com.churchapp.ami.menufragments;


import android.os.Bundle;
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
import com.churchapp.ami.menufragments.connectfragments.AlPartnership;
import com.churchapp.ami.menufragments.connectfragments.IvpBookingEnquiry;
import com.churchapp.ami.menufragments.connectfragments.PrayerRequest;

/**
 * A simple {@link Fragment} subclass.
 */
public class Connect extends Fragment {
    TabLayout tabLayout;
    ViewPager viewPager;

    public Connect() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.connect, container, false);
        viewPager = view.findViewById(R.id.connectViewPager);
        tabLayout = view.findViewById(R.id.connectTabLayout);
        MyConnectAdapter adapter = new MyConnectAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

    class MyConnectAdapter extends FragmentStatePagerAdapter{

        public MyConnectAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            if(position == 0){
                fragment = new AlPartnership();
            }
            if(position == 1){
                fragment = new IvpBookingEnquiry();
            }
            if(position == 2){
                fragment = new PrayerRequest();
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
                name = "AI PARTNERSHIP";
            }
            if(position == 1){
                name = "IVP ENQUIRY";
            }
            if(position == 2){
                name = "PRAYER REQUEST";
            }
            return name;
        }
    }
}
