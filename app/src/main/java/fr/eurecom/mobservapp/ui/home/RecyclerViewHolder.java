package fr.eurecom.mobservapp.ui.home;

import android.util.Log;
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
    private TextView createdView;

    private TextView endTimeView;
    public RecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        optionsContainer = itemView.findViewById(R.id.options_container);
        questionView = itemView.findViewById(R.id.text_question);
        usernameView = itemView.findViewById(R.id.text_username);
        endTimeView = itemView.findViewById(R.id.text_end_time);
        createdView = itemView.findViewById(R.id.text_created_time);
    }


    public void setQuestionText(String text) {
        questionView.setText(text);
    }

    public void setUsernameText(String text) {
        usernameView.setText(text);
    }

    public void setEndTimeText(String deadline) {
        endTimeView.setText(deadline);
    }

    public void setCreatedText(String createdText) {
        createdView.setText(createdText);
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