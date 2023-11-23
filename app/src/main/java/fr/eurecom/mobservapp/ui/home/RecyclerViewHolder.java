package fr.eurecom.mobservapp.ui.home;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import fr.eurecom.mobservapp.R;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    private TextView view1;
    private TextView view2;
    private TextView view3;
    public RecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        view1 = itemView.findViewById(R.id.text_view_1);
        view2 = itemView.findViewById(R.id.text_view_2);
        view3 = itemView.findViewById(R.id.text_view_3);
    }

    public TextView getView(){
        return view1;
    }
}