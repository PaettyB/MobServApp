package fr.eurecom.mobservapp.ui.home;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import fr.eurecom.mobservapp.R;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    LinearLayout optionsContainer;
    private TextView questionView;
    private TextView usernameView;

    private TextView endTimeView;
    public RecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        optionsContainer = itemView.findViewById(R.id.options_container);
        questionView = itemView.findViewById(R.id.text_question);
        usernameView = itemView.findViewById(R.id.text_username);
        endTimeView = itemView.findViewById(R.id.text_end_time);
    }




//    private TextView view1;
//    private TextView view2;
//    private TextView view3;
//    public RecyclerViewHolder(@NonNull View itemView) {
//        super(itemView);
//
//
//    }
//
    public void setQuestionText(String text) {
        questionView.setText(text);
    }
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