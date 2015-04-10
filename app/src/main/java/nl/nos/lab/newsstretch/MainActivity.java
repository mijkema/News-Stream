/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package nl.nos.lab.newsstretch;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.ScaleAnimation;
import android.widget.ProgressBar;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.github.johnpersano.supertoasts.SuperToast;
import com.github.johnpersano.supertoasts.util.Style;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.ActivityRecognition;

import java.util.Calendar;

/**
 * Sample application that demonstrates the use of
 * ActivityRecognitionClient}. It registers for activity detection updates
 * at a rate of 20 seconds, logs them to a file, and displays the detected
 * activities with their associated confidence levels.
 * <p>
 * An IntentService receives activity detection updates in the background
 * so that detection can continue even if the Activity is not visible.
 */
public class MainActivity extends ActionBarActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final long DETECTION_LENGTH = 3000;
    public static String HARD_NEWS = "hard_news",
    VIDEO = "video",
    TELETEKST = "teletekst",
    AUDIO = "audio";

    private static final String TOAST_TEXT = "Jouw artikelen zijn aangepast aan de werkomgeving";

    public enum newsCategories {
        auto_trein(HARD_NEWS),
        thuis(VIDEO),
        kantoor(TELETEKST),
        fiets(AUDIO),
        lopend(TELETEKST),
        ochtend(TELETEKST),
        avond(VIDEO);

        private final String newsType;

        newsCategories(String newsType) {
            this.newsType = newsType;
        }

        public String getNewsType() {
            return newsType;
        }
    }

    private static final String TAG = "MainActivity";
    private GoogleApiClient mGoogleClient;

    private ProgressBar pb;
    private FloatingActionButton actionButton;

    private PendingIntent callbackIntent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the main layout
        setContentView(R.layout.activity_main);
        pb = (ProgressBar) findViewById(R.id.progress_bar);
        actionButton = (FloatingActionButton) findViewById(R.id.action_button);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.action_bar);
        actionButton.post(new Runnable() {
            @Override
            public void run() {
                float middle = actionButton.getMeasuredHeight() / 2;
                ScaleAnimation scale = new ScaleAnimation(1, 3, 1, 3, middle, middle);
                scale.setFillAfter(true);
                scale.setDuration(0);
                actionButton.setAnimation(scale);
            }
        });

        mGoogleClient = new GoogleApiClient.Builder(this).addApi(ActivityRecognition.API).
                addConnectionCallbacks(this).addOnConnectionFailedListener(this).build();
        mGoogleClient.connect();
        pb.postDelayed(new Runnable() {
            @Override
            public void run() {
                pb.setVisibility(View.GONE);
                newsCategories category = getCurrentCategory(CustomApplication.lastActivity);
                startContextSpecificActivity(category);
                SuperToast superToast =
                        SuperToast.create(MainActivity.this, TOAST_TEXT,
                                SuperToast.Duration.LONG, Style.getStyle(Style.RED));
                superToast.setIcon(R.drawable.icon_activity_office_small, SuperToast.IconPosition.LEFT);
                superToast.show();

            }
        }, DETECTION_LENGTH);
    }

    private void startContextSpecificActivity(newsCategories category) {

        //TODO: base this on category.
        startActivity(new Intent(this, TeletekstActivity.class));
        finish();
    }

    private newsCategories getCurrentCategory(String lastActivity) {
        switch (lastActivity) {
            case "unknown": {
                Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                if (hour < 8) {
                    return newsCategories.ochtend;
                } else if (hour >= 8 && hour < 9) {
                    return newsCategories.auto_trein;
                } else if (hour >= 9 && hour < 18) {
                    return newsCategories.kantoor;
                } else if (hour >= 18 && hour < 22) {
                    return newsCategories.thuis;
                } else if (hour >= 22) {
                    return newsCategories.avond;
                }
                break;
            }
            case "in_vehicle":
                return newsCategories.auto_trein;
            case "on_bicycle":
                return newsCategories.fiets;
            case "walking":
                return newsCategories.lopend;
            case "still": {
                Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                if (hour >= 9 && hour < 18) {
                    return newsCategories.kantoor;
                } else {
                    return newsCategories.thuis;
                }
            }
        }
        return null;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "destroy");
        if (callbackIntent != null) {
            ActivityRecognition.ActivityRecognitionApi.removeActivityUpdates(mGoogleClient, callbackIntent);
            callbackIntent = null;
        }
        mGoogleClient.disconnect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Intent intent = new Intent(this, ActivityRecognitionService.class);
        callbackIntent = PendingIntent.getService(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        PendingResult<Status> result = ActivityRecognition.ActivityRecognitionApi
                .requestActivityUpdates(
                        mGoogleClient,         // your connected GoogleApiClient
                        0, // how often you want callbacks
                        callbackIntent);         // the PendingIntent which will receive updated activities
        result.setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(Status status) {
                if (status.isSuccess()) {
                }
            }
        });
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e(TAG, "Connection failed");
    }
}
