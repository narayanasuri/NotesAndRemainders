package koolkat.remindify;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import java.util.ArrayList;

import koolkat.remindify.adapter.RemindersAdapter;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ReminderFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ReminderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReminderFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static final int NOTIFICATION_REQUEST_CODE = 111;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private RecyclerView remindersRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ImageView noNotificationsImageView;
    private LinearLayout noRemindersLayout;
    private RemindersAdapter remindersAdapter;
    private ArrayList<Reminder> reminders;
    private Toast toast;

    public ReminderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReminderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReminderFragment newInstance(String param1, String param2) {
        ReminderFragment fragment = new ReminderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reminder, container, false);

        remindersRecyclerView = view.findViewById(R.id.reminders_recycler_view);
        noNotificationsImageView = view.findViewById(R.id.no_reminders_image_view);
        noRemindersLayout = view.findViewById(R.id.no_reminders_layout);

        IconicsDrawable notificationsNoneDrawable = new IconicsDrawable(getContext())
                .icon(MaterialDesignIconic.Icon.gmi_notifications_none)
                .color(getResources().getColor(R.color.lightGrey))
                .sizeDp(128)
                .paddingDp(4);

        noNotificationsImageView.setImageDrawable(notificationsNoneDrawable);

        reminders = App.getInstance().getReminders();
        remindersAdapter = new RemindersAdapter(this, getContext(), reminders);

        if(reminders == null || reminders.size() == 0) {
            remindersRecyclerView.setVisibility(View.GONE);
            noRemindersLayout.setVisibility(View.VISIBLE);
        } else {
            noRemindersLayout.setVisibility(View.GONE);
            remindersRecyclerView.setVisibility(View.VISIBLE);
        }

        layoutManager = new LinearLayoutManager(getContext());
        remindersRecyclerView.setLayoutManager(layoutManager);
        remindersRecyclerView.setItemAnimator(new DefaultItemAnimator());
        remindersRecyclerView.setAdapter(remindersAdapter);

        remindersRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            GestureDetector gestureDetector = new GestureDetector(getContext(),
                    new GestureDetector.SimpleOnGestureListener() {

                        @Override
                        public boolean onSingleTapUp(MotionEvent e) {
                            return true;
                        }

                    });

            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                View child = rv.findChildViewUnder(e.getX(), e.getY());
                if (child != null && gestureDetector.onTouchEvent(e)) {
                    final int i = rv.getChildAdapterPosition(child);



                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }

        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnNoteFragmentInteractionListener) {
//            mListener = (OnNoteFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnNoteFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    public void addReminderDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.reminder_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText reminderEditText = dialogView.findViewById(R.id.add_reminder_et);
        final Button createReminderButton = dialogView.findViewById(R.id.reminder_positive_btn);
        final Button discardReminderButton = dialogView.findViewById(R.id.reminder_negative_btn);
        final CheckBox persistentCheckBox = dialogView.findViewById(R.id.reminder_persistent_checkbox);
        final TextView reminderTextCountTextView = dialogView.findViewById(R.id.reminder_text_count_tv);
        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
        reminderEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count >=2 && count <= 24) {
                    reminderTextCountTextView.setTextColor(getResources().getColor(R.color.correct_color));
                } else {
                    reminderTextCountTextView.setTextColor(getResources().getColor(R.color.wrong_color));
                }
                reminderTextCountTextView.setText(String.format("%d/24", s.length()));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        discardReminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        createReminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String reminderText = reminderEditText.getText().toString();
                if(!reminderText.isEmpty()) {
                    int count = reminderEditText.getText().toString().length();
                    if(count >=2 && count <= 24) {
                        alertDialog.dismiss();
                        Reminder reminder;
                        if(persistentCheckBox.isChecked()) {
                            reminder = new Reminder(reminders.size(), reminderText, true);
                        } else {
                            reminder = new Reminder(reminders.size(), reminderText, false);
                        }
                        addReminder(reminder);
                    } else if(count < 2) {
                        showToast("A reminder should at least be 2 characters long!");
                    } else {
                        showToast("Please confine your reminder to be less than 25 characters!");
                    }
                }
            }
        });
    }

    private void addReminder(Reminder reminder) {
        App.getInstance().addReminder(reminder);
        remindersAdapter.notifyDataSetChanged();
        if(reminders == null || reminders.size() == 0) {
            remindersRecyclerView.setVisibility(View.GONE);
            noRemindersLayout.setVisibility(View.VISIBLE);
        } else {
            noRemindersLayout.setVisibility(View.GONE);
            remindersRecyclerView.setVisibility(View.VISIBLE);
        }
        createNotification(reminder.getContent(), reminders.indexOf(reminder), reminder.isPersistent());
    }

    private void removeReminder(int position) {
        removeNotification(position);
        App.getInstance().removeReminder(position);
        if(reminders == null || reminders.size() == 0) {
            remindersRecyclerView.setVisibility(View.GONE);
            noRemindersLayout.setVisibility(View.VISIBLE);
        } else {
            noRemindersLayout.setVisibility(View.GONE);
            remindersRecyclerView.setVisibility(View.VISIBLE);
        }
        remindersAdapter.notifyDataSetChanged();
    }

    private void showToast(String message) {
        if (toast != null) {
            toast.cancel();
            toast = null;
        }
        toast = Toast.makeText(getContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void showDeleteDialog(final int position) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
        builder1.setMessage("Delete reminder?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "DELETE",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        removeReminder(position);
                    }
                });

        builder1.setNegativeButton(
                "DISMISS",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public void createNotification(String reminder, int position, boolean isPersistent) {

        Intent intent = new Intent(getContext(), LauncherActivity.class);
        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(getContext());
        taskStackBuilder.addParentStack(LauncherActivity.class);
        taskStackBuilder.addNextIntent(intent);

        PendingIntent pendingIntent = taskStackBuilder.
                getPendingIntent(NOTIFICATION_REQUEST_CODE, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(getContext());
        nBuilder.setContentTitle("Reminder");
        nBuilder.setContentText(reminder);
        nBuilder.setSmallIcon(R.drawable.ic_notification);
        nBuilder.setContentIntent(pendingIntent);

        if(isPersistent)
            nBuilder.setOngoing(true);

        Notification notification = nBuilder.build();

        Object systemService = getActivity()
                .getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationManager notificationManager = (NotificationManager) systemService;
        assert notificationManager != null;
        notificationManager.notify(position, notification);

    }

    public void removeNotification(int position) {

        Reminder reminder = reminders.get(position);

        NotificationManager notificationManager = (NotificationManager) getActivity()
                .getSystemService(Context.NOTIFICATION_SERVICE);

        assert notificationManager != null;
        notificationManager.cancel(reminder.getId());
//        removeReminder(position);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
