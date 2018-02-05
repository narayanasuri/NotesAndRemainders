package koolkat.remindify;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Toast;

public class LauncherActivity extends AppCompatActivity implements NoteFragment.OnNoteFragmentInteractionListener {

    public static final int NEW_NOTE_ACTIVITY_REQUEST_CODE = 3030;
    public static final int EDIT_NOTE_ACTIVITY_REQUEST_CODE = 3040;
    public static final int SETTINGS_ACTIVITY_REQUEST_CODE = 2090;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */

    private ViewPager mViewPager;
    private FloatingActionButton addItemFab;
    private int currentFragment = 0;
    private Toast toast;
    private ReminderFragment reminderFragment;
    private NoteFragment noteFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (App.getInstance().getDarkThemeStatus()) {
            Log.i("LauncherActivity", "Dark Theme Activated");
            setTheme(R.style.NotefyDarkTheme);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        addItemFab = findViewById(R.id.add_item_fab);
        addItemFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment currentFragment = mSectionsPagerAdapter.getCurrentFragment();
                if (currentFragment instanceof ReminderFragment) {
//                    addReminderDialog();
                    ReminderFragment fragment = (ReminderFragment) currentFragment;
                    fragment.addReminderDialog();
                } else if (currentFragment instanceof NoteFragment) {
                    Intent intent = new Intent(LauncherActivity.this, NoteActivity.class);
                    startActivityForResult(intent, NEW_NOTE_ACTIVITY_REQUEST_CODE);
//                    startActivityFromFragment(currentFragment,  ,NEW_NOTE_ACTIVITY_REQUEST_CODE);
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_launcher, menu);

        for(int i = 0; i < menu.size(); i++){
            Drawable drawable = menu.getItem(i).getIcon();
            if(drawable != null) {
                drawable.mutate();
                drawable.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_IN);
            }
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivityForResult(new Intent(LauncherActivity.this, SettingsActivity.class),
                    SETTINGS_ACTIVITY_REQUEST_CODE);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void openNote(Note note, int position) {
        Log.i("LauncherActivity", "Note position : " + position);
        Intent intent = new Intent(LauncherActivity.this, NoteActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("title", note.getNoteTitle());
        intent.putExtra("content", note.getNoteContent());
        intent.putExtra("red", note.getRedColor());
        intent.putExtra("green", note.getGreenColor());
        intent.putExtra("blue", note.getBlueColor());
        startActivityForResult(intent, EDIT_NOTE_ACTIVITY_REQUEST_CODE);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_reminder, container, false);
//            TextView textView = rootView.findViewById(R.id.section_label);
//            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private Fragment mCurrentFragment;

        public Fragment getCurrentFragment() {
            return mCurrentFragment;
        }

        //...
        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            if (getCurrentFragment() != object) {
                mCurrentFragment = ((Fragment) object);
            }
            super.setPrimaryItem(container, position, object);
        }

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    currentFragment = 0;
                    Log.i("LauncherActivity", "ReminderFragment");
                    reminderFragment = ReminderFragment.newInstance("", "");
                    return reminderFragment;
                case 1:
                    currentFragment = 1;
                    Log.i("LauncherActivity", "NoteFragment");
                    noteFragment = NoteFragment.newInstance("", "");
                    return noteFragment;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "REMINDERS";
                case 1:
                    return "NOTES";
            }
            return null;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        NoteFragment fragment = null;
        Fragment currentFragment = mSectionsPagerAdapter.getCurrentFragment();

        if(currentFragment instanceof NoteFragment) {
            fragment = (NoteFragment) currentFragment;
        }

        if(fragment == null)
            return;

        if (requestCode == NEW_NOTE_ACTIVITY_REQUEST_CODE) {
            if(resultCode == Activity.RESULT_OK){
                Log.i("LauncherActivity", "NoteActivity Result");
                String title = data.getStringExtra("TITLE");
                String content = data.getStringExtra("CONTENT");
                int redColor = data.getIntExtra("RED", 245);
                int greenColor = data.getIntExtra("GREEN", 0);
                int blueColor = data.getIntExtra("BLUE", 87);
                Log.i("LauncherActivity", title);
                Log.i("LauncherActivity", content);

                if(!title.isEmpty() || !content.isEmpty()) {
                    if(title.isEmpty())
                        title = "Untitled";
                    Note note = new Note(title, content, redColor, greenColor, blueColor);
                    fragment.addNote(note);
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
        if(requestCode == EDIT_NOTE_ACTIVITY_REQUEST_CODE) {
            if(resultCode == Activity.RESULT_OK){
                Log.i("LauncherActivity", "NoteActivity Result");
                String title = data.getStringExtra("TITLE");
                String content = data.getStringExtra("CONTENT");
                int redColor = data.getIntExtra("RED", 245);
                int greenColor = data.getIntExtra("GREEN", 0);
                int blueColor = data.getIntExtra("BLUE", 87);
                int position = data.getIntExtra("position", -1);
                Log.i("LauncherActivity", title);
                Log.i("LauncherActivity", content);

                if(!title.isEmpty() || !content.isEmpty()) {
                    if(title.isEmpty())
                        title = "Untitled";
                    Note note = new Note(title, content, redColor, greenColor, blueColor);
                    fragment.replaceNote(position, note);
                } else {
                    fragment.removeNote(position);
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
                fragment.validateNotesPresent();
            }
        } if(requestCode == SETTINGS_ACTIVITY_REQUEST_CODE) {
            Log.i("LauncherActivity", "Recreating");
            recreate();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int pos = intent.getIntExtra("position", 0);
        if(intent.getStringExtra("removeNotifs").equals("removeNotification")) {
            reminderFragment.removeNotification(pos);
        }
    }

}