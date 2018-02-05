package koolkat.remindify;

import android.app.Application;

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

        notes = Paper.book().read(KEY_NOTES, new ArrayList<Note>());
        reminders = Paper.book().read(KEY_REMINDERS, new ArrayList<Reminder>());

    }

    public ArrayList<Note> getNotes() {
        notes = Paper.book().read(KEY_NOTES, new ArrayList<Note>());
        return notes;
    }

    public ArrayList<Reminder> getReminders() {
        reminders = Paper.book().read(KEY_REMINDERS, new ArrayList<Reminder>());
        return reminders;
    }

    public void addReminder(Reminder reminder) {
        reminders.add(reminder);
        Paper.book().write(KEY_REMINDERS, reminders);
    }

    public void removeReminder(int position) {
        reminders.remove(position);
        Paper.book().write(KEY_REMINDERS, reminders);
    }

    public void addNote(Note note) {
        notes.add(note);
        Paper.book().write(KEY_NOTES, notes);
    }

    public void removeNote(int position) {
        notes.remove(position);
        Paper.book().write(KEY_NOTES, notes);
    }

    public void replaceNote(int position, Note note) {
        notes.set(position, note);
        Paper.book().write(KEY_NOTES, notes);
    }

    public boolean getDarkThemeStatus() {
        isNightModeOn = Paper.book().read(KEY_NIGHT_MODE, false);
        return isNightModeOn;
    }

    public void setDarkThemeStatus(boolean darkThemeStatus) {
        Paper.book().write(KEY_NIGHT_MODE, darkThemeStatus);
    }

    public static App getInstance() {
        return mInstance ;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}
