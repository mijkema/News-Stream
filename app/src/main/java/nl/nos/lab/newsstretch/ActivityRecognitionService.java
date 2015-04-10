package nl.nos.lab.newsstretch;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

/**
 * @author Matthijs IJkema (09-04-15).
 */
public class ActivityRecognitionService extends IntentService {
    private static final String TAG = "ACT_REC_SERVICE";
    public static final String SAVED_ACTIVITY = "saved_activity";

    public ActivityRecognitionService() {
        super("ARS");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.e(TAG, "Received intent");

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        if(ActivityRecognitionResult.hasResult(intent)) {
            ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);
            DetectedActivity activity = result.getMostProbableActivity();
            if (activity.getConfidence() < 60) {
                CustomApplication.lastActivity = "unknown";
            } else {
                CustomApplication.lastActivity = getActivityName(activity.getType());
                sharedPrefs.edit().putString(SAVED_ACTIVITY, getActivityName(activity.getType())).apply();
            }
        }else {
            Log.d("Saquib", "Intent had no data returned");
        }
    }

    private String getActivityName(int type) {
        switch (type)
        {
            case DetectedActivity.IN_VEHICLE: // auto, trein
                return "in_vehicle";
            case DetectedActivity.ON_BICYCLE: // fietsend
                return "on_bicycle";
            case DetectedActivity.WALKING: // lopend
                return "walking";
            case DetectedActivity.STILL: // thuis, kantoor
                return "still";
            case DetectedActivity.UNKNOWN:
            default:
                return "unknown";
        }
    }
}
