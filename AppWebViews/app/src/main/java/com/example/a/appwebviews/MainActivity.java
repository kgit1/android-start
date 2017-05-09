package com.example.a.appwebviews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WebView webView = (WebView) findViewById(R.id.webView);

        webView.getSettings().setJavaScriptEnabled(true);

        //to stop app from jumping to device's default browser instead of opening own webView
        webView.setWebViewClient(new WebViewClient());

        //webView.loadUrl("http://www.google.com");

        //to put own html data on webView
        webView.loadData("<html><body><header>HEADER</header><h1>This is some web site</h1><p>Hi there!</p</body></html>", "text/html", "UTF-8");

    }
}
