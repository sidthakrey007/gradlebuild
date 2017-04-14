package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Pair;
import android.widget.Toast;

import com.example.jokeview.JokeViewActivity;
import com.example.udacity.gradleproject.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

/**
 * Created by sthakrey on 4/12/2017.
 */
public class EndPointsAsyncTask extends AsyncTask<Pair<Context, String>, Void, String> {
    private MyApi myApiService = null;
    private Context context;

   public static interface myCallback{
       void callbackFunction(String str);
   }

   public myCallback callback;

    public EndPointsAsyncTask(Context context) {
        this.context = context;
    }



    @Override
    protected String doInBackground(Pair<Context, String>... params) {
        if (myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl(context.getString(R.string.backend_url))
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);

                        }
                    });
            // end options for devappserver

            myApiService = builder.build();

        }

        context = params[0].first;
        String name = params[0].second;

        try {
            return myApiService.sayHi(context.getString(R.string.method_param)).execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        Toast.makeText(context, context.getString(R.string.Toast_message), Toast.LENGTH_SHORT).show();
        callback.callbackFunction(result);

    }
}