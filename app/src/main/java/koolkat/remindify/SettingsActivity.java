package koolkat.remindify;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String APP_URL = "https://play.google.com/store/apps/details?id=koolkat.remindify";

    private RelativeLayout changeThemeLayout;
    private Switch changeThemeSwitch;
    private boolean isNightModeOn = false;
    private LinearLayout aboutAppLayout, contactLayout, shareLayout, rateAppLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (App.getInstance().getDarkThemeStatus()) {
            Log.i("SettingsActivity", "Dark Theme Activated");
            setTheme(R.style.NoteThemeDark);
            isNightModeOn = true;
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        changeThemeLayout = findViewById(R.id.change_theme_layout);
        changeThemeLayout.setOnClickListener(this);
        changeThemeSwitch = findViewById(R.id.dark_theme_switch);

        aboutAppLayout = findViewById(R.id.about_the_app_layout);
        aboutAppLayout.setOnClickListener(this);
        contactLayout = findViewById(R.id.contact_layout);
        contactLayout.setOnClickListener(this);
        shareLayout = findViewById(R.id.share_app_layout);
        shareLayout.setOnClickListener(this);
        rateAppLayout = findViewById(R.id.rate_app_layout);
        rateAppLayout.setOnClickListener(this);

        if(isNightModeOn)
            changeThemeSwitch.setChecked(true);

        changeThemeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                App.getInstance().setDarkThemeStatus(b);
                recreate();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    @Override
    public void onClick(View view) {
        if(view == changeThemeLayout) {
            changeThemeSwitch.setChecked(!changeThemeSwitch.isChecked());
        } else if(view == aboutAppLayout) {
            startActivity(new Intent(SettingsActivity.this, AboutActivity.class));
        } else if(view == contactLayout) {
            contactThroughEmail();
        } else if(view == shareLayout) {
            shareApp();
        } else if(view == rateAppLayout) {
            openWebsite(APP_URL);
        }
    }

    private void contactThroughEmail() {
        String uriText =
                "mailto:narayanasuri08@gmail.com" +
                        "?subject=" + Uri.encode("Notefy - Feedback") +
                        "&body=" + Uri.encode("");

        Uri uri = Uri.parse(uriText);

        Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
        sendIntent.setData(uri);
        startActivity(Intent.createChooser(sendIntent, "Send email"));
    }

    private void shareApp() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,
                "Hey check out 'Notefy' at: " + APP_URL);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    private void openWebsite(String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

}
