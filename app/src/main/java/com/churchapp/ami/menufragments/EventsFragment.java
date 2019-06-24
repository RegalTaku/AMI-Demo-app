package com.churchapp.ami.menufragments;


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
public class EventsFragment extends Fragment {
    WebView eventsWebView;
    ProgressBar eventsProgressBar;
    SwipeRefreshLayout eventsSwipeRefresh;
    LinearLayout errorLayout;

    public EventsFragment() {
        // Required empty public constructor
    }


    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.events, container, false);
        eventsProgressBar = view.findViewById(R.id.eventsProgressBar);
        eventsWebView = view.findViewById(R.id.eventsWebView);
        eventsSwipeRefresh = view.findViewById(R.id.eventsSwipeRefresh);
        errorLayout = view.findViewById(R.id.errorLayout);

        eventsWebView.loadUrl("https://alleluiaministries.com/category/events/");
        eventsWebView.getSettings().setJavaScriptEnabled(true);
        eventsWebView.getSettings().setAllowFileAccess(true);
        eventsWebView.getSettings().setAppCacheEnabled(true);
        loadWebsite();

        eventsWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                errorLayout.setVisibility(View.GONE);
                eventsProgressBar.setVisibility(View.GONE);
                eventsSwipeRefresh.setRefreshing(false);
                eventsWebView.setVisibility(View.VISIBLE);
                super.onPageFinished(view, url);
            }
        });

        eventsWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                eventsProgressBar.setProgress(newProgress);
            }
        });

        eventsSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                eventsWebView.reload();
            }
        });
        return view;
    }

    private void loadWebsite() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            eventsWebView.loadUrl("https://alleluiaministries.com/category/events/");
        } else {
            eventsWebView.setVisibility(View.GONE);
            errorLayout.setVisibility(View.VISIBLE);
        }
    }
}
