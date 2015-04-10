package nl.nos.lab.newsstretch;

import android.app.Application;
import android.preference.PreferenceManager;

/**
 * @author Matthijs IJkema (09-04-15).
 */
public class CustomApplication extends Application {

    public static String lastActivity = "unknown";

    @Override
    public void onCreate() {
        super.onCreate();
        PreferenceManager.getDefaultSharedPreferences(this).getString(ActivityRecognitionService.SAVED_ACTIVITY, "unknown");
    }
}
