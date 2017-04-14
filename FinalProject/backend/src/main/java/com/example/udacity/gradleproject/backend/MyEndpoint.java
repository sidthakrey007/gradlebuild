/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.example.udacity.gradleproject.backend;

import com.example.JokeProvider;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import javax.inject.Named;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "myApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.gradleproject.udacity.example.com",
                ownerName = "backend.gradleproject.udacity.example.com",
                packagePath = ""
        )
)
public class MyEndpoint {

    public MyEndpoint() {
    }

    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = "sayHi")
    public Joke sayHi(@Named("name") String name) {
        Joke myjoke = new Joke();

        myjoke.setData(JokeProvider.tellJoke());

        return myjoke;

    }










}
