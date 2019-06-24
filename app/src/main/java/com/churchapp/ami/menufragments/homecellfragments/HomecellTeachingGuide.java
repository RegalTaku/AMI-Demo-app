package com.churchapp.ami.menufragments.homecellfragments;


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
public class HomecellTeachingGuide extends Fragment {
    WebView teachingWebView;
    ProgressBar teachingProgressBar;
    SwipeRefreshLayout teachingSwipeRefresh;
    LinearLayout errorLayout;

    public HomecellTeachingGuide() {
        // Required empty public constructor
    }


    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.homecell_teaching_guide, container, false);
        teachingWebView = view.findViewById(R.id.teachingWebView);
        teachingProgressBar = view.findViewById(R.id.teachingProgressBar);
        teachingSwipeRefresh = view.findViewById(R.id.teachingSwipeRefresh);
        errorLayout = view.findViewById(R.id.errorLayout);

        teachingWebView.loadUrl("https://alleluiaministries.com/homecell-teaching-guide-week-10-of-52/");
        teachingWebView.getSettings().setJavaScriptEnabled(true);
        teachingWebView.getSettings().setAllowFileAccess(true);
        teachingWebView.getSettings().setAppCacheEnabled(true);
        loadWebsite();

        teachingWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                errorLayout.setVisibility(View.GONE);
                teachingProgressBar.setVisibility(View.GONE);
                teachingSwipeRefresh.setRefreshing(false);
                teachingWebView.setVisibility(View.VISIBLE);
                super.onPageFinished(view, url);
            }
        });
        teachingWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                teachingProgressBar.setProgress(newProgress);
            }
        });

        teachingSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                teachingWebView.reload();
            }
        });
        return view;
    }

    private void loadWebsite() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            teachingWebView.loadUrl("https://alleluiaministries.com/homecell-teaching-guide-week-10-of-52/");
        } else {
            teachingWebView.setVisibility(View.GONE);
            errorLayout.setVisibility(View.VISIBLE);
        }
    }


}
