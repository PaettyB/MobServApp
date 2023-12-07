package fr.eurecom.mobservapp.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import fr.eurecom.mobservapp.R;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    LinearLayout optionsContainer;
    private TextView optionText;
    public RecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        optionsContainer = itemView.findViewById(R.id.options_container); // Replace with your container ID
    }

//    private TextView view1;
//    private TextView view2;
//    private TextView view3;
//    public RecyclerViewHolder(@NonNull View itemView) {
//        super(itemView);
//        view1 = itemView.findViewById(R.id.text_view_1);
//        view2 = itemView.findViewById(R.id.text_view_2);
//        view3 = itemView.findViewById(R.id.text_view_3);
//    }
//
//    public void setView1Text(String text) {
//        view1.setText(text);
//    }
//
//    public void hideView3Text(){
//        view3.setVisibility(View.GONE);
//
//    }
//
//    public TextView[] getView(){
//        return new TextView[]{view1, view2, view3};
//    }
}