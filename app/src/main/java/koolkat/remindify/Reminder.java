package koolkat.remindify;

/**
 * Created by Naray on 30-12-2017.
 */

public class Reminder {

    private int id;
    private String content;
    private boolean isPersistent;

    public Reminder() {

    }

    public Reminder(int id, String content, boolean isPersistent) {
        this.id = id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
