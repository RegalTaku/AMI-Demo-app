package com.churchapp.ami.menufragments;


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
public class Contact extends Fragment {
    WebView contactWebView;
    ProgressBar contactProgressBar;
    SwipeRefreshLayout contactSwipeRefresh;
    LinearLayout errorLayout;

    public Contact() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.contact, container, false);
        contactWebView = view.findViewById(R.id.contactWebView);
        contactProgressBar = view.findViewById(R.id.contactProgressBar);
        contactSwipeRefresh = view.findViewById(R.id.contactSwipeRefresh);
        errorLayout = view.findViewById(R.id.errorLayout);

        contactWebView.loadUrl("https://alleluiaministries.com/contact/");
        contactWebView.getSettings().setJavaScriptEnabled(true);
        contactWebView.getSettings().setAllowFileAccess(true);
        contactWebView.getSettings().setAppCacheEnabled(true);
        loadWebsite();

        contactWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                errorLayout.setVisibility(View.GONE);
                contactProgressBar.setVisibility(View.GONE);
                contactSwipeRefresh.setRefreshing(false);
                contactWebView.setVisibility(View.VISIBLE);
                super.onPageFinished(view, url);
            }
        });

        contactWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                contactProgressBar.setProgress(newProgress);
            }
        });

        contactSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                contactWebView.reload();
            }
        });

        return view;
    }

    private void loadWebsite(){
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            contactWebView.loadUrl("https://alleluiaministries.com/contact/");
        } else {
            contactWebView.setVisibility(View.GONE);
            errorLayout.setVisibility(View.VISIBLE);
        }
    }

}
