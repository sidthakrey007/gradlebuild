package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.os.AsyncTask;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.MediumTest;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.intent.IntentMonitorRegistry;
import android.util.Log;
import android.util.Pair;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by sthakrey on 4/12/2017.
 */

@RunWith(AndroidJUnit4.class)
@MediumTest
public class TestAsyncTask{

    Context context = null;

    @Before
    public void setUp()
    {
        context = InstrumentationRegistry.getContext();
    }

    @Test
    public void testAsyncTask()
    {
      try {
            EndPointsAsyncTask task = new EndPointsAsyncTask(context) {
                @Override
                protected void onPostExecute(String result) {
                  }
            };
        task.execute(new Pair<Context, String>(context, context.getString(R.string.method_param)));
        String  result = task.get(5, TimeUnit.SECONDS);
        Assert.assertNotNull(result);
        Assert.assertTrue(task.getStatus().equals(AsyncTask.Status.FINISHED));
        Assert.assertTrue(result.length() != 0 ) ;

        }
        catch (InterruptedException e) {

            e.printStackTrace();
        }
        catch (ExecutionException e) {
            e.printStackTrace();
        }
        catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

}
