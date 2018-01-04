package koolkat.notiminder;

import android.graphics.Color;

/**
 * Created by Naray on 30-12-2017.
 */

public class Reminder {

    private String content;
    private boolean isPersistent;

    public Reminder() {

    }

    public Reminder(String content, boolean isPersistent) {
        this.content = content;
        this.isPersistent = isPersistent;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isPersistent() {
        return isPersistent;
    }

    public void setPersistent(boolean persistent) {
        isPersistent = persistent;
    }

}
