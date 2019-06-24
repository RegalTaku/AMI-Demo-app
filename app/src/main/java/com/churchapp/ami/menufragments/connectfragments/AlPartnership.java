package com.churchapp.ami.menufragments.connectfragments;


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
public class AlPartnership extends Fragment {
    WebView partnershipWebView;
    ProgressBar partnershipProgressBar;
    SwipeRefreshLayout partnershipSwipeRefresh;
    LinearLayout errorLayout;

    public AlPartnership() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.al_partnership, container, false);
        partnershipWebView = view.findViewById(R.id.partnershipWebView);
        partnershipProgressBar = view.findViewById(R.id.partnershipProgressBar);
        partnershipSwipeRefresh = view.findViewById(R.id.partnershipSwipeRefresh);
        errorLayout = view.findViewById(R.id.errorLayout);

        partnershipWebView.loadUrl("https://alleluiaministries.com/al-partnership/");
        partnershipWebView.getSettings().setJavaScriptEnabled(true);
        partnershipWebView.getSettings().setAllowFileAccess(true);
        partnershipWebView.getSettings().setAppCacheEnabled(true);
        loadWebsite();

        partnershipWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                errorLayout.setVisibility(View.GONE);
                partnershipProgressBar.setVisibility(View.GONE);
                partnershipSwipeRefresh.setRefreshing(false);
                partnershipWebView.setVisibility(View.VISIBLE);
                super.onPageFinished(view, url);
            }
        });
        partnershipWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                partnershipProgressBar.setProgress(newProgress);
            }
        });

        partnershipSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                partnershipWebView.reload();
            }
        });
        return view;
    }

    private void loadWebsite() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            partnershipWebView.loadUrl("https://alleluiaministries.com/al-partnership/");
        } else {
            partnershipWebView.setVisibility(View.GONE);
            errorLayout.setVisibility(View.VISIBLE);
        }
    }

}
