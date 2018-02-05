package koolkat.remindify;

/**
 * Created by Naray on 30-12-2017.
 */

public class Note {

    private String noteTitle, noteContent;
    private int redColor, blueColor, greenColor;

    public Note() {}

    public Note(String noteTitle, String noteContent) {
        this.noteTitle = noteTitle;
        this.noteContent = noteContent;
        this.redColor = 255;
        this.blueColor = 255;
        this.greenColor = 255;
    }

    public Note(String noteTitle, String noteContent, int redColor, int greenColor, int blueColor) {
        this.noteTitle = noteTitle;
        this.noteContent = noteContent;
        this.redColor = redColor;
        this.greenColor = greenColor;
        this.blueColor = blueColor;
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

    public int getRedColor() {
        return redColor;
    }

    public void setRedColor(int redColor) {
        this.redColor = redColor;
    }

    public int getBlueColor() {
        return blueColor;
    }

    public void setBlueColor(int blueColor) {
        this.blueColor = blueColor;
    }

    public int getGreenColor() {
        return greenColor;
    }

    public void setGreenColor(int greenColor) {
        this.greenColor = greenColor;
    }
}
