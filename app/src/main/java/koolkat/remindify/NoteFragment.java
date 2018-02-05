package koolkat.remindify;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;
import com.pes.androidmaterialcolorpickerdialog.ColorPicker;
import com.pes.androidmaterialcolorpickerdialog.ColorPickerCallback;

import java.util.ArrayList;

import koolkat.remindify.adapter.NotesAdapter;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnNoteFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NoteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoteFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnNoteFragmentInteractionListener mListener;

    private RecyclerView notesRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ImageView noNotesImageView;
    private LinearLayout noNotesLayout;
    private NotesAdapter notesAdapter;
    private ArrayList<Note> notes;
    private Toast toast;

    public NoteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NoteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NoteFragment newInstance(String param1, String param2) {
        NoteFragment fragment = new NoteFragment();
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
        View view = inflater.inflate(R.layout.fragment_note, container, false);

        notesRecyclerView = view.findViewById(R.id.notes_recycler_view);
        noNotesImageView = view.findViewById(R.id.no_notes_image_view);
        noNotesLayout = view.findViewById(R.id.no_notes_layout);

        IconicsDrawable notificationsNoneDrawable = new IconicsDrawable(getContext())
                .icon(MaterialDesignIconic.Icon.gmi_file_text)
                .color(getResources().getColor(R.color.lightGrey))
                .sizeDp(128)
                .paddingDp(4);

        noNotesImageView.setImageDrawable(notificationsNoneDrawable);

        notes = App.getInstance().getNotes();
        notesAdapter = new NotesAdapter(this, getContext(), notes);

        if(notes == null || notes.size() == 0) {
            notesRecyclerView.setVisibility(View.GONE);
            noNotesLayout.setVisibility(View.VISIBLE);
        } else {
            noNotesLayout.setVisibility(View.GONE);
            notesRecyclerView.setVisibility(View.VISIBLE);
        }

        layoutManager = new LinearLayoutManager(getContext());
        notesRecyclerView.setLayoutManager(layoutManager);
        notesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        notesRecyclerView.setAdapter(notesAdapter);

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
        if (context instanceof OnNoteFragmentInteractionListener) {
            mListener = (OnNoteFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnNoteFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void openNote(Note note, int position) {
        Log.i("NoteFragment", "Note position : " + position);
        if (mListener != null) {
            mListener.openNote(note, position);
        }
    }

    public void validateNotesPresent() {
        if(notes == null || notes.size() == 0) {
            notesRecyclerView.setVisibility(View.GONE);
            noNotesLayout.setVisibility(View.VISIBLE);
        } else {
            noNotesLayout.setVisibility(View.GONE);
            notesRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    public void addNote(Note note) {
        App.getInstance().addNote(note);
        validateNotesPresent();
        notesAdapter.notifyDataSetChanged();
    }

    public void removeNote(int position) {
        App.getInstance().removeNote(position);
        validateNotesPresent();
        notesAdapter.notifyDataSetChanged();
    }

    public void replaceNote(int position, Note newNote) {
        App.getInstance().replaceNote(position, newNote);
        notesAdapter.notifyDataSetChanged();
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
        builder1.setMessage("Are you sure?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "DELETE",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        removeNote(position);
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

    public void showColorDialog(final int position, int redColor, int greenColor, int blueColor) {
        final ColorPicker cp = new ColorPicker(getActivity(), redColor, greenColor, blueColor);

        cp.show();

    /* Set a new Listener called when user click "select" */
        cp.setCallback(new ColorPickerCallback() {
            @Override
            public void onColorChosen(@ColorInt int color) {
                // Do whatever you want
                // Examples
                int r = Color.red(color);
                int g = Color.green(color);
                int b = Color.blue(color);

                Note note = notes.get(position);

                Note newNote = new Note(note.getNoteTitle(), note.getNoteContent(), r, g, b);
                replaceNote(position, newNote);

                cp.dismiss();
            }
        });
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
    public interface OnNoteFragmentInteractionListener {
        // TODO: Update argument type and name
        void openNote(Note note, int position);
        void onFragmentInteraction(Uri uri);
    }
}
