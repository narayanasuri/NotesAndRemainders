package koolkat.notiminder;

/**
 * Created by Naray on 11-10-2016.
 */

        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.TextView;

        import java.util.ArrayList;

        import io.paperdb.Paper;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private ArrayList<String> notiminders;
    private ArrayList<Boolean> persistence;

    public CustomAdapter(ArrayList<String> notiminders, ArrayList<Boolean> persistence) {
        this.persistence = persistence;
        this.notiminders = notiminders;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView cardtextview;
        TextView peristenttextview;


        public ViewHolder(View itemView) {
            super(itemView);
            cardtextview = (TextView) itemView.findViewById(R.id.card_text);
            peristenttextview = (TextView) itemView.findViewById(R.id.persistent_tv);
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_layout, parent, false);
        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }


    @Override
    public void onBindViewHolder(CustomAdapter.ViewHolder viewHolder, int i){

        Boolean isPersistent = persistence.get(i);

        viewHolder.cardtextview.setText(notiminders.get(i));
        if(isPersistent)
            viewHolder.peristenttextview.setText("Persistent");
        else
            viewHolder.peristenttextview.setText("");
    }

    @Override
    public int getItemCount() {
        return notiminders.size();
    }
}
