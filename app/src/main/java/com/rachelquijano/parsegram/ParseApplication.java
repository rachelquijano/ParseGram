package com.rachelquijano.parsegram;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Register your parse models
        ParseObject.registerSubclass(Post.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("8OmEW5CsWXg08sMRLbgoyxw7LzpBfyninX21WGCx")
                .clientKey("je2zdpwHSXiaRIy8zTK61fMFifEurGeS2iWic24B")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
