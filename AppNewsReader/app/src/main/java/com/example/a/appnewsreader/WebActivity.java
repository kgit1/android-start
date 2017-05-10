package com.example.a.appnewsreader;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        WebView webView = (WebView) findViewById(R.id.webView);

        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient());

       webView.loadUrl("https://devblogs.nvidia.com/parallelforall/inside-volta/");

       // Intent intent = getIntent();
        //webView.loadData(intent.getStringExtra("data"),"text/html","UTF-8");
    }
}
