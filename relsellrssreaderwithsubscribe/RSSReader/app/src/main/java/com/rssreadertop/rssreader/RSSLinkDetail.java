/*
 * Copyright (c) 2017. Relsell Global
 */

package com.rssreadertop.rssreader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.rssreadertop.R;

public class RSSLinkDetail extends AppCompatActivity {

    WebView webView;
    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rsslink_detail);

        String link = getIntent().getStringExtra("link");

        webView = (WebView)findViewById(R.id.webview);
        pb = (ProgressBar)findViewById(R.id.pb);
        webView.loadUrl(link);

        webView.setWebViewClient(new MyBrowser());
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

    }

    private class MyBrowser extends WebViewClient {

        public MyBrowser() {
            pb.setVisibility(View.VISIBLE);
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            return false;
        }



        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            pb.setVisibility(View.GONE);


        }
    }


}
