package com.udacity.gradle.builditbigger;

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
import android.widget.Toast;

import com.example.JokeProvider;
import com.example.jokeview.*;
import com.example.jokeview.BuildConfig;

import java.io.IOException;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    static EndPointsAsyncTask task =null;
    static String AsyncTaskResponse = "";
    ProgressBar p ;
    Button b ;

    public MainActivityFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(com.udacity.gradle.builditbigger.R.layout.fragment_main, container, false);
        Button jokeButton = (Button) root.findViewById(R.id.jokeButton);
        jokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
        public void onClick(View v) {
                p.setVisibility(View.VISIBLE);
                initiateAction();


            }
        });
        p = (ProgressBar) root.findViewById(R.id.progressBar1);
        p.setVisibility(View.GONE);
        b = (Button) root.findViewById(R.id.jokeButton);
        return root;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        if(task!= null && task.getStatus()== AsyncTask.Status.FINISHED)
        {
            Intent message = new Intent(getActivity(), JokeViewActivity.class);
            message.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            message.putExtra("JOKE", AsyncTaskResponse);
            b.setEnabled(true);
            p.setVisibility(View.GONE);
            startActivity(message);

        }
        super.onViewCreated(view, savedInstanceState);
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

    void initiateAction() {
        if (task == null || task.getStatus() == AsyncTask.Status.FINISHED) {
            task = new EndPointsAsyncTask(getActivity());
            task.callback = new EndPointsAsyncTask.myCallback() {
                @Override
                public void callbackFunction(String str) {
                    AsyncTaskResponse = str;
                    if (getActivity() != null) {
                        Intent message = new Intent(getActivity(), JokeViewActivity.class);
                        message.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        message.putExtra("JOKE", str);
                        b.setEnabled(true);
                        p.setVisibility(View.GONE);
                        startActivity(message);
                        task = null;
                    }
                }
            };

            task.execute(new Pair<Context, String>(getActivity(), "siddharth"));
            b.setEnabled(false);

        } else {
            if ((task.getStatus() == AsyncTask.Status.RUNNING) || (task.getStatus() == AsyncTask.Status.PENDING))
                return;

        }
    }












}
