package com.churchapp.ami.menufragments.homefragments;


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
public class Pastors extends Fragment {
    WebView pastorsWebView;
    ProgressBar progressBar;
    SwipeRefreshLayout pastorsSwipeRefresh;
    LinearLayout errorLayout;

    public Pastors() {
        // Required empty public constructor
    }


    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.pastors, container, false);
        pastorsWebView = view.findViewById(R.id.pastorsWebView);
        progressBar = view.findViewById(R.id.pastorsProgressBar);
        pastorsSwipeRefresh = view.findViewById(R.id.pastorsSwipeRefresh);
        errorLayout = view.findViewById(R.id.errorLayout);

        pastorsWebView.loadUrl("https://alleluiaministries.com/our-pastors/");
        pastorsWebView.getSettings().setJavaScriptEnabled(true);
        pastorsWebView.getSettings().setAllowFileAccess(true);
        pastorsWebView.getSettings().setAppCacheEnabled(true);
        loadWebsite();

        pastorsWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                errorLayout.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                pastorsSwipeRefresh.setRefreshing(false);
                pastorsWebView.setVisibility(View.VISIBLE);
                super.onPageFinished(view, url);
            }
        });
        pastorsWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBar.setProgress(newProgress);
            }
        });

        pastorsSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pastorsWebView.reload();
            }
        });
        return view;
    }

    private void loadWebsite() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            pastorsWebView.loadUrl("https://alleluiaministries.com/our-pastors/");
        } else {
            pastorsWebView.setVisibility(View.GONE);
            errorLayout.setVisibility(View.VISIBLE);
        }
    }

}
