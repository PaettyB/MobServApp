package fr.eurecom.mobservapp.ui.home;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.MissingFormatArgumentException;
import java.util.Random;
import java.util.stream.Collectors;

import fr.eurecom.mobservapp.MainActivity;
import fr.eurecom.mobservapp.R;
import fr.eurecom.mobservapp.polls.Poll;
import fr.eurecom.mobservapp.polls.User;
import fr.eurecom.mobservapp.ui.home.RecyclerViewHolder;

public class RandomNumListAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
    private Random random;
    private MainActivity mainActivity;

    private ArrayList<Poll> polls = new ArrayList<Poll>();
    private ArrayList<Poll> filtered = new ArrayList<Poll>();
    private HashMap<String, User> users = new HashMap<>();


    public RandomNumListAdapter(Context activity) {
        this.mainActivity = (MainActivity) activity;
        polls = mainActivity.getPolls();
        users = mainActivity.getUsers();
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
        Log.i("POSITION", ""+position);
        position = filtered.size()-position-1;
        holder.optionsContainer.removeAllViews();
        Log.i("POLL TITLE", filtered.get(position).getTitle());
        holder.setQuestionText(filtered.get(position).getTitle());

        for (int i = 0; i <= filtered.get(position).getAnswers().size()-1; i++) {
            View pollOptionView = LayoutInflater.from(holder.itemView.getContext()).inflate(R.layout.poll_vote_button_layout, holder.optionsContainer, false);
            Button optionText = pollOptionView.findViewById(R.id.poll_button);
            optionText.setText(filtered.get(position).getAnswers().get(i));
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
        filtered = polls.stream().filter(poll -> {
            // keep if the poll was created by a friend of ours
            String owner = poll.getOwner();
            ArrayList<String> ourFriends = users.get(MainActivity.USERNAME).getFriends();
            return ourFriends.contains(owner);
        }).collect(Collectors.toCollection(ArrayList::new));

        Log.i("COUNT FUNCTION", filtered.size() + "");
        return filtered.size();
    }
}
