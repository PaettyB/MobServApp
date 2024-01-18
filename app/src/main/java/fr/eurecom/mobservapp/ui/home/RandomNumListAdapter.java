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
import java.util.stream.Stream;

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

    private boolean displayFinishedPolls = false;


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
        Poll p = filtered.get(position);
        holder.optionsContainer.removeAllViews();
        Log.i("POLL TITLE", p.getTitle());
        holder.setQuestionText(p.getTitle());
        holder.setCreatedText(p.getCreatedText());
        holder.setEndTimeText(p.getTimeRemainingText());
        holder.setUsernameText(p.getOwner());

        for (int i = 0; i <= p.getAnswers().size()-1; i++) {
            View pollOptionView = LayoutInflater.from(holder.itemView.getContext()).inflate(R.layout.poll_vote_button_layout, holder.optionsContainer, false);
            Button optionText = pollOptionView.findViewById(R.id.poll_button);
            optionText.setText(p.getAnswers().get(i));
            holder.optionsContainer.addView(pollOptionView);
        }
    }


    @Override
    public int getItemCount() {
        Stream<Poll> s = polls.stream().filter(poll -> {
            // first filter active / finished polls
            if(displayFinishedPolls) {
                if(poll.isRunning()) return false;
            } else {
                if(!poll.isRunning()) return false;
            }

            // keep if the poll was created by a friend of ours or ourselves
            if(poll.getOwner().equals(MainActivity.USERNAME)) return true;

            String owner = poll.getOwner();
            ArrayList<String> ourFriends = users.get(MainActivity.USERNAME).getFriends();
            return ourFriends.contains(owner);
        });

        filtered = s.collect(Collectors.toCollection(ArrayList::new));

        Log.i("COUNT FUNCTION", filtered.size() + "");
        return filtered.size();
    }

    public void setDisplayFinishedPolls(boolean displayFinishedPolls) {
        this.displayFinishedPolls = displayFinishedPolls;
    }
}
