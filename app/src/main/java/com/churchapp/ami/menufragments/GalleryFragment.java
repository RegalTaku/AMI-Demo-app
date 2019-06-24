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
public class GalleryFragment extends Fragment {
    WebView galleryWebView;
    ProgressBar galleryProgressBar;
    SwipeRefreshLayout gallerySwipeRefresh;
    LinearLayout errorLayout;

    public GalleryFragment() {
        // Required empty public constructor
    }


    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        galleryWebView = view.findViewById(R.id.galleryWebView);
        galleryProgressBar = view.findViewById(R.id.galleryProgressBar);
        gallerySwipeRefresh = view.findViewById(R.id.gallerySwipeRefresh);
        errorLayout = view.findViewById(R.id.errorLayout);

        galleryWebView.loadUrl("https://alleluiaministries.com/gallery/");
        galleryWebView.getSettings().setJavaScriptEnabled(true);
        galleryWebView.getSettings().setAllowFileAccess(true);
        galleryWebView.getSettings().setAppCacheEnabled(true);
        loadWebsite();

        galleryWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                errorLayout.setVisibility(View.GONE);
                galleryProgressBar.setVisibility(View.GONE);
                gallerySwipeRefresh.setRefreshing(false);
                galleryWebView.setVisibility(View.VISIBLE);
                super.onPageFinished(view, url);
            }
        });

        galleryWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                galleryProgressBar.setProgress(newProgress);
            }
        });

        gallerySwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                galleryWebView.reload();
            }
        });
        return view;
    }

    private void loadWebsite() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            galleryWebView.loadUrl("https://alleluiaministries.com/gallery/");
        } else {
            galleryWebView.setVisibility(View.GONE);
            errorLayout.setVisibility(View.VISIBLE);
        }
    }
}
