package com.example.maqalidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.measurement.module.Analytics;
import com.google.firebase.analytics.FirebaseAnalytics;

public class HomeActivity extends AppCompatActivity {

    WebView wb;
    GoogleAnalytics analytics;
    Tracker tracker;
    String id;

    private FirebaseAnalytics mFirebaseAnalytics;

    private class HelloWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        FirebaseAnalytics.getInstance(this).getAppInstanceId();


        wb = (WebView) findViewById(R.id.wv);
        wb.getSettings().setJavaScriptEnabled(true);
        wb.getSettings().setLoadWithOverviewMode(true);
        wb.getSettings().setUseWideViewPort(true);
        wb.getSettings().setBuiltInZoomControls(true);
        wb.getSettings().setPluginState(WebSettings.PluginState.ON);
        wb.setWebViewClient(new HelloWebViewClient());
        wb.loadUrl("https://www.maqalid.com/home");

        onWebviewClicked();
         doTracking();


    }

    private void onWebviewClicked() {

        wb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle params = new Bundle();
                params.putString("id", id);
                mFirebaseAnalytics.logEvent("share_id", params);


            }
        });

    }


    //For Google Analytics ...

    private void doTracking() {

        analytics = GoogleAnalytics.getInstance(HomeActivity.this);
        analytics.setLocalDispatchPeriod(1800);
        tracker = analytics.newTracker("UA-172862204-1"); // Replace with actual tracker/property Id
        tracker.enableExceptionReporting(true);
        tracker.enableAdvertisingIdCollection(true);
        tracker.enableAutoActivityTracking(true);
        tracker.setScreenName("HomeActivity");
        tracker.send(new HitBuilders.EventBuilder().build());

    }
}