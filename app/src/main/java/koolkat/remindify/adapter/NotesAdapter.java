package koolkat.remindify.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;

import koolkat.remindify.Note;
import koolkat.remindify.NoteFragment;
import koolkat.remindify.R;

/**
 * Created by Suri on 1/4/2018.
 */

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    private Context context;
    private NoteFragment noteFragment;
    private ArrayList<Note> notes;

    public NotesAdapter(NoteFragment noteFragment, Context context, ArrayList<Note> notes) {
        this.noteFragment = noteFragment;
        this.context = context;
        this.notes = notes;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView noteCard;
        TextView noteTitleTv, noteContentTv;
        ExpandableLayout noteExpandableLayout;
        ImageView paletteNoteIv, deleteNoteIv, moreIv;

        public ViewHolder(View itemView) {
            super(itemView);
            noteCard = itemView.findViewById(R.id.note_card_view);
            noteTitleTv = itemView.findViewById(R.id.note_card_title);
            noteContentTv = itemView.findViewById(R.id.note_card_content);
            noteExpandableLayout = itemView.findViewById(R.id.note_expandable_layout);
//            editNoteIv = itemView.findViewById(R.id.edit_note_iv);
            paletteNoteIv = itemView.findViewById(R.id.palette_note_iv);
            deleteNoteIv = itemView.findViewById(R.id.delete_note_iv);
            moreIv = itemView.findViewById(R.id.note_view_more_iv);
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_layout, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final NotesAdapter.ViewHolder viewHolder, final int i) {

        final Note note = notes.get(i);

        int color = Color.rgb(note.getRedColor(), note.getGreenColor(), note.getBlueColor());

        viewHolder.noteCard.setCardBackgroundColor(color);

        if(isColorDark(color)){
            viewHolder.noteTitleTv.setTextColor(Color.WHITE);
            viewHolder.noteContentTv.setTextColor(Color.WHITE);

            IconicsDrawable paletteDrawable = new IconicsDrawable(context)
                    .icon(MaterialDesignIconic.Icon.gmi_palette)
                    .color(Color.WHITE)
                    .sizeDp(24)
                    .paddingDp(2);

            IconicsDrawable deleteDrawable = new IconicsDrawable(context)
                    .icon(MaterialDesignIconic.Icon.gmi_delete)
                    .color(Color.WHITE)
                    .sizeDp(24)
                    .paddingDp(2);

            IconicsDrawable moreDrawable = new IconicsDrawable(context)
                    .icon(MaterialDesignIconic.Icon.gmi_more)
                    .color(Color.WHITE)
                    .sizeDp(24)
                    .paddingDp(2);

//            viewHolder.editNoteIv.setImageDrawable(editDrawable);
            viewHolder.paletteNoteIv.setImageDrawable(paletteDrawable);
            viewHolder.deleteNoteIv.setImageDrawable(deleteDrawable);
            viewHolder.moreIv.setImageDrawable(moreDrawable);
        } else{
            viewHolder.noteTitleTv.setTextColor(Color.BLACK);
            viewHolder.noteContentTv.setTextColor(Color.BLACK);

//            IconicsDrawable editDrawable = new IconicsDrawable(context)
//                    .icon(MaterialDesignIconic.Icon.gmi_edit)
//                    .color(Color.BLACK)
//                    .sizeDp(32);

            IconicsDrawable paletteDrawable = new IconicsDrawable(context)
                    .icon(MaterialDesignIconic.Icon.gmi_palette)
                    .color(Color.BLACK)
                    .sizeDp(24)
                    .paddingDp(2);

            IconicsDrawable deleteDrawable = new IconicsDrawable(context)
                    .icon(MaterialDesignIconic.Icon.gmi_delete)
                    .color(Color.BLACK)
                    .sizeDp(24)
                    .paddingDp(2);

            IconicsDrawable moreDrawable = new IconicsDrawable(context)
                    .icon(MaterialDesignIconic.Icon.gmi_more)
                    .color(Color.BLACK)
                    .sizeDp(24)
                    .paddingDp(2);

//            viewHolder.editNoteIv.setImageDrawable(editDrawable);
            viewHolder.paletteNoteIv.setImageDrawable(paletteDrawable);
            viewHolder.deleteNoteIv.setImageDrawable(deleteDrawable);
            viewHolder.moreIv.setImageDrawable(moreDrawable);
        }

        String noteTitle = note.getNoteTitle();
        viewHolder.noteTitleTv.setText(noteTitle);

        String noteContent = note.getNoteContent();
        viewHolder.noteContentTv.setText(noteContent);

        viewHolder.noteContentTv.post(new Runnable() {
            @Override
            public void run() {
                int lineCnt = viewHolder.noteContentTv.getLineCount();
                // Perform any actions you want based on the line count here.

                if(lineCnt <= 4) {
                    viewHolder.moreIv.setVisibility(View.GONE);
                } else {
                    viewHolder.moreIv.setVisibility(View.VISIBLE);
                }
            }
        });

        viewHolder.noteCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noteFragment.openNote(note, viewHolder.getAdapterPosition());
            }
        });

        viewHolder.noteCard.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                viewHolder.noteExpandableLayout.toggle();
                return true;
            }
        });

        viewHolder.paletteNoteIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noteFragment.showColorDialog(i, note.getRedColor(), note.getGreenColor(), note.getBlueColor());
            }
        });


        viewHolder.deleteNoteIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noteFragment.showDeleteDialog(viewHolder.getAdapterPosition());
            }
        });

    }

    public boolean isColorDark(int color){
        double darkness = 1-(0.299*Color.red(color) + 0.587*Color.green(color) + 0.114*Color.blue(color))/255;
        if(darkness<0.5){
            return false; // It's a light color
        }else{
            return true; // It's a dark color
        }
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }
}
