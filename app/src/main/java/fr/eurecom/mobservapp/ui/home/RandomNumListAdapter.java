package fr.eurecom.mobservapp.ui.home;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.Random;

import fr.eurecom.mobservapp.R;
import fr.eurecom.mobservapp.ui.home.RecyclerViewHolder;

public class RandomNumListAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
    private Random random;

    public RandomNumListAdapter(int seed) {
        this.random = new Random(seed);
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.poll_layout;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i("onCreateHolder", "start");
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);

        View pollView = LayoutInflater.from(parent.getContext()).inflate(R.layout.poll_layout, parent, false);
//        LinearLayout optionsContainer = pollView.findViewById(R.id.options_container);
//        View pollOptionView = LayoutInflater.from(parent.getContext()).inflate(R.layout.poll_vote_button_layout, optionsContainer, false);
//        optionsContainer.addView(pollOptionView);

        RecyclerViewHolder poll = new RecyclerViewHolder(pollView);
        //myCard.setView1Text("hello world");

        return poll;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.optionsContainer.removeAllViews();

        for (int i = 0; i <= position; i++) {
            View pollOptionView = LayoutInflater.from(holder.itemView.getContext()).inflate(R.layout.poll_vote_button_layout, holder.optionsContainer, false);
            Button optionText = pollOptionView.findViewById(R.id.poll_button);
            optionText.setText("Option number " + String.valueOf(i));
            holder.optionsContainer.addView(pollOptionView);
            // Your code here
        }

//        optionsContainer.addView(pollOptionView);



//        holder.setView1Text(String.valueOf(position));

//
//        if (position == 2){
//            holder.hideView3Text();
//            ViewGroup.LayoutParams params =  holder.itemView.getLayoutParams();
//            params.height =200;
//            holder.itemView.setLayoutParams(params);
//        }


    }


    @Override
    public int getItemCount() {
        return 100;
    }
}
