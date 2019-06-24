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
public class PrayerRequest extends Fragment {
    WebView prayerWebView;
    ProgressBar prayerProgressBar;
    SwipeRefreshLayout prayerSwipeRefresh;
    LinearLayout errorLayout;

    public PrayerRequest() {
        // Required empty public constructor
    }


    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.prayer_request, container, false);
        prayerWebView = view.findViewById(R.id.prayerWebView);
        prayerProgressBar = view.findViewById(R.id.prayerProgressBar);
        prayerSwipeRefresh = view.findViewById(R.id.prayerSwipeRefresh);
        errorLayout = view.findViewById(R.id.errorLayout);

        prayerWebView.loadUrl("https://alleluiaministries.com/prayer-request/");
        prayerWebView.getSettings().setJavaScriptEnabled(true);
        prayerWebView.getSettings().setAllowFileAccess(true);
        prayerWebView.getSettings().setAppCacheEnabled(true);
        loadWebsite();

        prayerWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                errorLayout.setVisibility(View.GONE);
                prayerProgressBar.setVisibility(View.GONE);
                prayerSwipeRefresh.setRefreshing(false);
                prayerWebView.setVisibility(View.VISIBLE);
                super.onPageFinished(view, url);
            }
        });
        prayerWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                prayerProgressBar.setProgress(newProgress);
            }
        });

        prayerSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                prayerWebView.reload();
            }
        });
        return view;
    }

    private void loadWebsite() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            prayerWebView.loadUrl("https://alleluiaministries.com/prayer-request/");
        } else {
            prayerWebView.setVisibility(View.GONE);
            errorLayout.setVisibility(View.VISIBLE);
        }
    }

}
