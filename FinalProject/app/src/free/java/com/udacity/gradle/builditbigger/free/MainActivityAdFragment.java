package com.udacity.gradle.builditbigger.free;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.jokeview.JokeViewActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.udacity.gradle.builditbigger.EndPointsAsyncTask;
import com.udacity.gradle.builditbigger.R;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityAdFragment extends Fragment {

    static EndPointsAsyncTask task =null;
    static String AsyncTaskResponse = "";
    InterstitialAd mInterstitialAd ;
    ProgressBar p ;
    Button b ;

    public MainActivityAdFragment() {
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mInterstitialAd = new InterstitialAd(getActivity());
        mInterstitialAd.setAdUnitId(getString(R.string.banner_ad_unit_id));

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                p.setVisibility(View.VISIBLE);
                initiateAction();
            }
        });

        requestNewInterstitial();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        Button jokeButton = (Button) root.findViewById(R.id.jokeButton);

        jokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    p.setVisibility(View.VISIBLE);
                    initiateAction();
                }
            }
        });

        AdView mAdView = (AdView) root.findViewById(R.id.adView);
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
        p = (ProgressBar) root.findViewById(R.id.progressBar1);
        p.setVisibility(View.GONE);
        b = (Button) root.findViewById(R.id.jokeButton);
        return root;
    }


    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("")
                .build();

        mInterstitialAd.loadAd(adRequest);
    }


    @Override
    public void onResume() {

        if(task!=null && (task.getStatus()== AsyncTask.Status.RUNNING || task.getStatus()== AsyncTask.Status.PENDING))
        {
            p.setVisibility(View.VISIBLE);
            b.setEnabled(false);
        }


        super.onResume();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        if(task!= null && task.getStatus()== AsyncTask.Status.FINISHED)
        {
            Intent message = new Intent(getActivity(), JokeViewActivity.class);
            message.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

            message.putExtra(getString(R.string.intent_key), AsyncTaskResponse);
            b.setEnabled(true);
            p.setVisibility(View.GONE);
            startActivity(message);

        }
        super.onViewCreated(view, savedInstanceState);
    }

    void initiateAction()
    {
        if(task == null || task.getStatus()== AsyncTask.Status.FINISHED) {
            task = new EndPointsAsyncTask(getActivity());
            task.callback = new EndPointsAsyncTask.myCallback() {
                @Override
                public void callbackFunction(String str) {
                    AsyncTaskResponse  = str;
                    if (getActivity() != null) {
                        Intent message = new Intent(getActivity(), JokeViewActivity.class);
                        message.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        message.putExtra(getString(R.string.intent_key), str);
                        b.setEnabled(true);
                        p.setVisibility(View.GONE);
                        startActivity(message);
                        task = null;
                    }
                }
            };

            task.execute(new Pair<Context, String>(getActivity(), getString(R.string.method_param)));
            b.setEnabled(false);

        }
        else
        {
            if((task.getStatus() == AsyncTask.Status.RUNNING)  || (task.getStatus() == AsyncTask.Status.PENDING))
                return ;

        }

    }









}
