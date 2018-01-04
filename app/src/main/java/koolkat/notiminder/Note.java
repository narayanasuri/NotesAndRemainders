package koolkat.notiminder;

import android.graphics.Color;

/**
 * Created by Naray on 30-12-2017.
 */

public class Note {

    private String noteTitle, noteContent;
    private Color color;

    public Note() {}

    public Note(String noteTitle, String noteContent, Color color) {
        this.noteTitle = noteTitle;
        this.noteContent = noteContent;
        this.color = color;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
