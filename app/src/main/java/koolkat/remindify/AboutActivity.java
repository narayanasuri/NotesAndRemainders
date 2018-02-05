package koolkat.remindify;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

public class AboutActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String PAPER_URL = "https://github.com/pilgr/Paper";
    public static final String ICONICS_URL = "https://github.com/mikepenz/Android-Iconics";
    public static final String COLOR_PICKER_URL = "https://github.com/Pes8/android-material-color-picker-dialog";
    public static final String GLIDE_URL = "https://github.com/bumptech/glide";
    public static final String EXPANDABLE_LAYOUT_URL = "https://github.com/cachapa/ExpandableLayout";

    public static final String TWITTER_URL = "https://twitter.com/n_suri96";
    public static final String LINKEDIN_URL = "https://www.linkedin.com/in/narayanasuri/";
    public static final String GITHUB_URL = "https://github.com/narayanasuri";

    private LinearLayout paperLayout, iconicsLayout, colorPickerLayout, glideLayout, expandableLayout;
    private ImageView twitterIv, githubIv, linkedinIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (App.getInstance().getDarkThemeStatus()) {
            Log.i("SettingsActivity", "Dark Theme Activated");
            setTheme(R.style.NoteThemeDark);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        paperLayout = findViewById(R.id.paper_library_layout);
        paperLayout.setOnClickListener(this);
        iconicsLayout = findViewById(R.id.iconics_library_layout);
        iconicsLayout.setOnClickListener(this);
        colorPickerLayout = findViewById(R.id.color_picker_library_layout);
        colorPickerLayout.setOnClickListener(this);
        glideLayout = findViewById(R.id.glide_library_layout);
        glideLayout.setOnClickListener(this);
        expandableLayout = findViewById(R.id.expandable_layout_library_layout);
        expandableLayout.setOnClickListener(this);

        IconicsDrawable twitterDrawable = new IconicsDrawable(this)
                .icon(MaterialDesignIconic.Icon.gmi_twitter)
                .color(getResources().getColor(R.color.lightGrey))
                .sizeDp(36)
                .paddingDp(4);

        twitterIv = findViewById(R.id.twitter_iv);
        twitterIv.setImageDrawable(twitterDrawable);
        twitterIv.setOnClickListener(this);

        IconicsDrawable githubDrawable = new IconicsDrawable(this)
                .icon(MaterialDesignIconic.Icon.gmi_github)
                .color(getResources().getColor(R.color.lightGrey))
                .sizeDp(36)
                .paddingDp(4);

        githubIv = findViewById(R.id.github_iv);
        githubIv.setImageDrawable(githubDrawable);
        githubIv.setOnClickListener(this);

        IconicsDrawable linkedinDrawable = new IconicsDrawable(this)
                .icon(MaterialDesignIconic.Icon.gmi_linkedin)
                .color(getResources().getColor(R.color.lightGrey))
                .sizeDp(36)
                .paddingDp(4);

        linkedinIv = findViewById(R.id.linkedin_iv);
        linkedinIv.setImageDrawable(linkedinDrawable);
        linkedinIv.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view == paperLayout) {
            openWebsite(PAPER_URL);
        }else if(view == iconicsLayout) {
            openWebsite(ICONICS_URL);
        } else if(view == colorPickerLayout) {
            openWebsite(COLOR_PICKER_URL);
        } else if(view == glideLayout) {
            openWebsite(GLIDE_URL);
        } else if(view == expandableLayout) {
            openWebsite(EXPANDABLE_LAYOUT_URL);
        } else if(view == githubIv) {
            openWebsite(GITHUB_URL);
        } else if(view == twitterIv) {
            openWebsite(TWITTER_URL);
        } else if(view == linkedinIv) {
            openWebsite(LINKEDIN_URL);
        }
    }

    private void openWebsite(String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

}
