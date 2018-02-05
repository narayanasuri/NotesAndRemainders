package koolkat.remindify.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import koolkat.remindify.R;
import koolkat.remindify.Reminder;
import koolkat.remindify.ReminderFragment;

/**
 * Created by Suri on 1/4/2018.
 */

public class RemindersAdapter extends RecyclerView.Adapter<RemindersAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Reminder> reminders;
    private Toast toast;
    private ReminderFragment reminderFragment;

    public RemindersAdapter(ReminderFragment reminderFragment, Context context, ArrayList<Reminder> reminders) {
        this.reminderFragment = reminderFragment;
        this.context = context;
        this.reminders = reminders;

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView reminderTextView, reminderPersistentTextView;
        CardView reminderCardView;

        public ViewHolder(View itemView) {
            super(itemView);
            reminderTextView = itemView.findViewById(R.id.reminder_text_view);
            reminderPersistentTextView = itemView.findViewById(R.id.reminder_persistent_tv);
            reminderCardView = itemView.findViewById(R.id.reminder_card_view);
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reminder_layout, parent, false);
        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }


    @Override
    public void onBindViewHolder(final RemindersAdapter.ViewHolder viewHolder, final int i) {

        Reminder reminder = reminders.get(i);

        String reminderText = reminder.getContent();
        viewHolder.reminderTextView.setText(reminderText);

        if(reminder.isPersistent())
            viewHolder.reminderPersistentTextView.setText("Persistent");
        else
            viewHolder.reminderPersistentTextView.setText("");

        viewHolder.reminderCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reminderFragment.showDeleteDialog(i);
            }
        });

        reminderFragment.createNotification(reminderText, i, reminder.isPersistent());

    }

    @Override
    public int getItemCount() {
        return reminders.size();
    }
}
