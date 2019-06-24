package com.churchapp.ami.menufragments.connectfragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.churchapp.ami.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class IvpBookingEnquiry extends Fragment {
    WebView ivpWebView;
    ProgressBar ivpProgressBar;
    SwipeRefreshLayout ivpSwipeRefresh;
    LinearLayout errorLayout;

    public IvpBookingEnquiry() {
        // Required empty public constructor
    }


    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.ivp_booking_enquiry, container, false);
        ivpWebView = view.findViewById(R.id.ivpWebView);
        ivpProgressBar = view.findViewById(R.id.ivpProgressBar);
        ivpSwipeRefresh = view.findViewById(R.id.ivpSwipeRefresh);
        errorLayout = view.findViewById(R.id.errorLayout);

        ivpWebView.loadUrl("https://alleluiaministries.com/ivp-booking-enquiry/");
        ivpWebView.getSettings().setJavaScriptEnabled(true);
        ivpWebView.getSettings().setAllowFileAccess(true);
        ivpWebView.getSettings().setAppCacheEnabled(true);
        loadWebsite();

        ivpWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                errorLayout.setVisibility(View.GONE);
                ivpProgressBar.setVisibility(View.GONE);
                ivpSwipeRefresh.setRefreshing(false);
                ivpWebView.setVisibility(View.VISIBLE);
                super.onPageFinished(view, url);
            }
        });
        ivpWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                ivpProgressBar.setProgress(newProgress);
            }
        });

        ivpSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ivpWebView.reload();
            }
        });
        return view;
    }

    private void loadWebsite() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            ivpWebView.loadUrl("https://alleluiaministries.com/ivp-booking-enquiry/");
        } else {
            ivpWebView.setVisibility(View.GONE);
            errorLayout.setVisibility(View.VISIBLE);
        }
    }

}
