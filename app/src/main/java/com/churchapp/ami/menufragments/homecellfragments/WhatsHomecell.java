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
public class WhatsHomecell extends Fragment {
    WebView whatsHomecellWebView;
    ProgressBar whatsHomecellProgressBar;
    SwipeRefreshLayout whatsHomecellSwipeRefresh;
    LinearLayout errorLayout;

    public WhatsHomecell() {
        // Required empty public constructor
    }


    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.whats_homecell, container, false);
        whatsHomecellWebView = view.findViewById(R.id.whatsHomecellWebView);
        whatsHomecellProgressBar = view.findViewById(R.id.whatsHomecellProgressBar);
        whatsHomecellSwipeRefresh = view.findViewById(R.id.whatsHomecellSwipeRefresh);
        errorLayout = view.findViewById(R.id.errorLayout);

        whatsHomecellWebView.loadUrl("https://alleluiaministries.com/all-you-need-to-know-about-homecells/");
        whatsHomecellWebView.getSettings().setJavaScriptEnabled(true);
        whatsHomecellWebView.getSettings().setAllowFileAccess(true);
        whatsHomecellWebView.getSettings().setAppCacheEnabled(true);
        loadWebsite();

        whatsHomecellWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                errorLayout.setVisibility(View.GONE);
                whatsHomecellProgressBar.setVisibility(View.GONE);
                whatsHomecellSwipeRefresh.setRefreshing(false);
                whatsHomecellWebView.setVisibility(View.VISIBLE);
                super.onPageFinished(view, url);
            }
        });
        whatsHomecellWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                whatsHomecellProgressBar.setProgress(newProgress);
            }
        });

        whatsHomecellSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                whatsHomecellWebView.reload();
            }
        });
        return view;
    }

    private void loadWebsite() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            whatsHomecellWebView.loadUrl("https://alleluiaministries.com/all-you-need-to-know-about-homecells/");
        } else {
            whatsHomecellWebView.setVisibility(View.GONE);
            errorLayout.setVisibility(View.VISIBLE);
        }
    }
}
