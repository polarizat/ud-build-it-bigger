package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class MainActivityFree extends AppCompatActivity {

    private InterstitialAd mInterstitialAd;
    public static final String EXTRA_JOKE = "EXTRA_JOKE";
    ProgressBar progressBar;

    private String mJoke;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.loading_pb);

        if (BuildConfig.HAS_INTERSTITIAL_AD) initInterstitialAd();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view) {
        progressBar.setVisibility(View.VISIBLE);

        if (BuildConfig.HAS_INTERSTITIAL_AD)
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

        new EndpointsAsyncTask(joke -> {
            mJoke = joke;
            if (BuildConfig.HAS_INTERSTITIAL_AD) {
                mInterstitialAd.setAdListener(new AdListener() {
                    public void onAdLoaded() {
                        if (mInterstitialAd.isLoaded()) {
                            mInterstitialAd.show();
                        }
                    }
                    @Override
                    public void onAdClosed() {
                        launchActivity(mJoke);
                    }
                    @Override
                    public void onAdFailedToLoad(int i) {
                        super.onAdFailedToLoad(i);
                        launchActivity(mJoke);
                    }
                });
            } else {
                launchActivity(joke);
            }
        }).execute(this);
    }

    public void initInterstitialAd(){
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }

    public void launchActivity(String joke){
        Intent intent = new Intent(this, JokesActivity.class);
        intent.putExtra(EXTRA_JOKE, joke);
        startActivity(intent);
        progressBar.setVisibility(View.INVISIBLE);
    }

}
