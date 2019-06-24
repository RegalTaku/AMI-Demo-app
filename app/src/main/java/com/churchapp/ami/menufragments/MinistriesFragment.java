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
public class MinistriesFragment extends Fragment {
    WebView ministriesWebView;
    ProgressBar ministriesProgressBar;
    SwipeRefreshLayout ministriesSwipeRefresh;
    LinearLayout errorLayout;

    public MinistriesFragment() {
        // Required empty public constructor
    }


    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ministries, container, false);
        ministriesWebView = view.findViewById(R.id.ministriesWebView);
        ministriesProgressBar = view.findViewById(R.id.ministriesProgressBar);
        ministriesSwipeRefresh = view.findViewById(R.id.ministriesSwipeRefresh);
        errorLayout = view.findViewById(R.id.errorLayout);

        ministriesWebView.loadUrl("https://alleluiaministries.com/ministries/");
        ministriesWebView.getSettings().setJavaScriptEnabled(true);
        ministriesWebView.getSettings().setAllowFileAccess(true);
        ministriesWebView.getSettings().setAppCacheEnabled(true);
        loadWebsite();

        ministriesWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                errorLayout.setVisibility(View.GONE);
                ministriesProgressBar.setVisibility(View.GONE);
                ministriesSwipeRefresh.setRefreshing(false);
                ministriesWebView.setVisibility(View.VISIBLE);
                super.onPageFinished(view, url);
            }
        });
        ministriesWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                ministriesProgressBar.setProgress(newProgress);
            }
        });

        ministriesSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ministriesWebView.reload();
            }
        });

        return view;
    }

    private void loadWebsite() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            ministriesWebView.loadUrl("https://alleluiaministries.com/ministries/");
        } else {
            ministriesWebView.setVisibility(View.GONE);
            errorLayout.setVisibility(View.VISIBLE);
        }
    }

}
