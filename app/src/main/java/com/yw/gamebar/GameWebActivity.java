package com.yw.gamebar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Created by yw on 16/1/15.
 */
public class GameWebActivity extends FragmentActivity{

    private WebView play_web;
    private String  play_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏


        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        play_web = (WebView)findViewById(R.id.play_web);

        play_url = getIntent().getStringExtra("play_url");

        initDatas();
    }


    @SuppressLint("SetJavaScriptEnabled")
    private void initDatas() {

        WebSettings webSettings = play_web.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//

        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setUseWideViewPort(true);//

        play_web.setInitialScale(10);//
        play_web.loadUrl(play_url);

    }
}
