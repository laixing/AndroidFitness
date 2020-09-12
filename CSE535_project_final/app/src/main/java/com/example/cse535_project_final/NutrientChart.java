package com.example.cse535_project_final;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class NutrientChart extends AppCompatActivity {
    public static String  TABLE_NAME = "AppData";//new omkar
    public static float BMR = 0, desiredcalories = 0;//new omkar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrient_chart);

        WebView webView = (WebView)findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.getSettings().setSupportZoom(true);
        webView.loadUrl("https://fdc.nal.usda.gov/fdc-app.html#/");

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {//new omkar
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), MainActivity2.class);//new omkar
        startActivity(intent);//new omkar
        finish();
    }

}
