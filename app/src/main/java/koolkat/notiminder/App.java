package koolkat.notiminder;

import android.app.Application;
import android.content.SharedPreferences;

import java.util.ArrayList;

import io.paperdb.Paper;

/**
 * Created by Naray on 30-12-2017.
 */

public class App extends Application {

    // Singleton instance
    private static App mInstance = null;

    public static final String KEY_NOTES = "notes";
    public static final String KEY_REMINDERS = "reminders";
    public static final String KEY_NIGHT_MODE = "nightMode";

    private ArrayList<Note> notes;
    private ArrayList<Reminder> reminders;
    private boolean isNightModeOn = false;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;

        Paper.init(getApplicationContext());

        isNightModeOn = Paper.book().read(KEY_NIGHT_MODE, false);

        notes = Paper.book().read(KEY_NOTES, new ArrayList<Note>());
        reminders = Paper.book().read(KEY_REMINDERS, new ArrayList<Reminder>());


    }

    public static App getInstance() {
        return mInstance ;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}
