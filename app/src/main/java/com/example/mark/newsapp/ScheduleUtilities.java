package com.example.mark.newsapp;


import android.content.Context;
import android.support.annotation.NonNull;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

/**
 * Created by Mark on 2017/7/28.
 */
// Use Firebase’s JobDispatcher, can update new news every minute.
public class ScheduleUtilities {
    private static boolean sInitialized;
    private static final int PERIODICITY = 60;
    private static final int TOLERANCE_INTERVAL = 60;

    synchronized public static void scheduleRefresh(@NonNull final Context context){
        //check if already scheduling if not scheduling
        if(sInitialized) return;
//use firebase dispatch and google play to schedule
        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);


        Job constraintRefreshJob = dispatcher.newJobBuilder()
                .setService(NewsJob.class)
                .setTag("news_job_tag")
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(PERIODICITY,
                        PERIODICITY + TOLERANCE_INTERVAL))
                .setReplaceCurrent(true)
                .build();
        dispatcher.schedule(constraintRefreshJob);
        sInitialized = true;
    }
}

