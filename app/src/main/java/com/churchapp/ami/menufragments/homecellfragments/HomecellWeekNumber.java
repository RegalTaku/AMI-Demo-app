package com.churchapp.ami.menufragments.homecellfragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
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
public class HomecellWeekNumber extends Fragment {
    WebView weekWebView;
    ProgressBar weekProgressBar;
    SwipeRefreshLayout weekSwipeRefresh;
    LinearLayout errorLayout;

    public HomecellWeekNumber() {
        // Required empty public constructor
    }


    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.homecell_week_number, container, false);
        weekWebView = view.findViewById(R.id.weekWebView);
        weekProgressBar = view.findViewById(R.id.weekProgressBar);
        weekSwipeRefresh = view.findViewById(R.id.weekSwipeRefresh);
        errorLayout = view.findViewById(R.id.errorLayout);

        weekWebView.loadUrl("https://alleluiaministries.com/week-46-of-52/");
        weekWebView.getSettings().setJavaScriptEnabled(true);
        weekWebView.getSettings().setAllowFileAccess(true);
        weekWebView.getSettings().setAppCacheEnabled(true);
        loadWebsite();

        weekWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                errorLayout.setVisibility(View.GONE);
                weekProgressBar.setVisibility(View.GONE);
                weekSwipeRefresh.setRefreshing(false);
                weekWebView.setVisibility(View.VISIBLE);
                super.onPageFinished(view, url);
            }
        });
        weekWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                weekProgressBar.setProgress(newProgress);
            }
        });

        weekSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                weekWebView.reload();
            }
        });
        return view;
    }

    private void loadWebsite() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            weekWebView.loadUrl("https://alleluiaministries.com/week-46-of-52/");
        } else {
            weekWebView.setVisibility(View.GONE);
            errorLayout.setVisibility(View.VISIBLE);
        }
    }



}
