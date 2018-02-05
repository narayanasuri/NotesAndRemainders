package koolkat.remindify;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;
import com.pes.androidmaterialcolorpickerdialog.ColorPicker;
import com.pes.androidmaterialcolorpickerdialog.ColorPickerCallback;

public class NoteActivity extends AppCompatActivity {

    ActionBar actionBar;
    private EditText titleEditText, noteContentEditText;
    private String noteTitle = "", noteContent = "";

    //Result Code is 1 if the note is saved and 0 if discarded
    private int resultCode = 1;
    private int position;
    private int redColor = 213;
    private int greenColor = 20;
    private int blueColor = 87;

    private boolean noteChanged = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (App.getInstance().getDarkThemeStatus()) {
            Log.i("NoteActivity", "Dark Theme Activated");
            setTheme(R.style.NoteThemeDark);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        noteTitle = intent.getStringExtra("title");
        noteContent = intent.getStringExtra("content");
        position = intent.getIntExtra("position", -1);
        int r = intent.getIntExtra("red", redColor);
        int g = intent.getIntExtra("green", greenColor);
        int b = intent.getIntExtra("blue", blueColor);

        redColor = r;
        greenColor = g;
        blueColor = b;

        titleEditText = findViewById(R.id.note_title_edit_text);
        noteContentEditText = findViewById(R.id.note_content_edit_text);


//        String noteTitle = getIntent().getExtras().getString("noteTitle");
//        String noteContent = getIntent().getExtras().getString("noteContent");

        if (noteTitle != null && !noteTitle.isEmpty()) {
            titleEditText.setText(noteTitle);
        }
        if (noteContent != null && !noteContent.isEmpty()) {
            noteContentEditText.setText(noteContent);
        }
    }

    public void removeNote(int position) {
        Log.i("NoteActivity", "Note position : " + position);
        App.getInstance().removeNote(position);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.note_menu, menu);

        IconicsDrawable iconicsDrawable = new IconicsDrawable(this)
                .icon(MaterialDesignIconic.Icon.gmi_copy)
                .color(getResources().getColor(R.color.material_black))
                .sizeDp(24)
                .paddingDp(4);

        menu.getItem(0).setIcon(iconicsDrawable);

        IconicsDrawable iconicsDrawable1 = new IconicsDrawable(this)
                .icon(MaterialDesignIconic.Icon.gmi_palette)
                .color(getResources().getColor(R.color.material_black))
                .sizeDp(24)
                .paddingDp(4);

        menu.getItem(1).setIcon(iconicsDrawable1);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
            case R.id.note_action_copy:
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText(noteTitle, noteContent);
                if (clipboard != null) {
                    clipboard.setPrimaryClip(clip);
                }
                Toast.makeText(NoteActivity.this, "Text Copied", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.note_action_change_color:
                showColorDialog();
                return true;
            case R.id.note_action_discard:
                noteChanged = false;
                finish();
                return true;
            case R.id.note_action_delete:
                if (position != -1)
                    removeNote(position);
                noteChanged = false;
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void finish() {
        if (noteChanged) {
            noteTitle = titleEditText.getText().toString();
            noteContent = noteContentEditText.getText().toString();
            Log.i("NoteActivity", noteTitle);
            Log.i("NoteActivity", noteContent);
            Intent intent = getIntent();
            intent.putExtra("TITLE", noteTitle);
            intent.putExtra("CONTENT", noteContent);
            intent.putExtra("RED", redColor);
            intent.putExtra("GREEN", greenColor);
            intent.putExtra("BLUE", blueColor);
            if (position != -1)
                intent.putExtra("POSITION", position);
            setResult(Activity.RESULT_OK, intent);
        } else {
            setResult(Activity.RESULT_CANCELED);
        }
        super.finish();
    }

    private void showColorDialog() {
        final ColorPicker cp = new ColorPicker(NoteActivity.this, redColor, greenColor, blueColor);

        cp.show();

    /* Set a new Listener called when user click "select" */
        cp.setCallback(new ColorPickerCallback() {
            @Override
            public void onColorChosen(@ColorInt int color) {
                // Do whatever you want
                // Examples
                redColor = Color.red(color);
                greenColor = Color.green(color);
                blueColor = Color.blue(color);
                Log.d("Red", Integer.toString(Color.red(color)));
                Log.d("Green", Integer.toString(Color.green(color)));
                Log.d("Blue", Integer.toString(Color.blue(color)));

                Log.d("Pure Hex", Integer.toHexString(color));
                Log.d("#Hex no alpha", String.format("#%06X", (0xFFFFFF & color)));
                Log.d("#Hex with alpha", String.format("#%08X", (0xFFFFFFFF & color)));
                cp.dismiss();
            }
        });
    }
}