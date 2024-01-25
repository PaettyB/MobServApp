package fr.eurecom.mobservapp.ui.home;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import fr.eurecom.mobservapp.MainActivity;
import fr.eurecom.mobservapp.R;
import fr.eurecom.mobservapp.polls.Poll;
import fr.eurecom.mobservapp.polls.User;

public class PollListAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
    private Random random;
    private MainActivity mainActivity;

    private ArrayList<Poll> polls = new ArrayList<Poll>();
    private ArrayList<Poll> filtered = new ArrayList<Poll>();
    private HashMap<String, User> users = new HashMap<>();

    private boolean displayFinishedPolls = false;


    public PollListAdapter(Context activity) {
        this.mainActivity = (MainActivity) activity;
        polls = mainActivity.getPolls();
        users = mainActivity.getUsers();
    }



    @Override
    public int getItemViewType(final int position) {
//        int p = filtered.size()-position-1;
//        Poll poll = filtered.get(p);
//        Log.i("Title at position", position + ", " +poll.getTitle());
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
//        poll.
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

        if(p.isVoted()){
            for (int i = 0; i <= p.getAnswers().size() - 1; i++) {
                View pollVoteView = LayoutInflater.from(holder.itemView.getContext()).inflate(R.layout.poll_show_votes_layout, holder.optionsContainer, false);
                holder.optionsContainer.addView(pollVoteView);
                TextView optText = pollVoteView.findViewById(R.id.optionText);
                optText.setText(p.getAnswers().get(i));
                TextView voteCount = pollVoteView.findViewById(R.id.voteCount);
                voteCount.setText(""+p.getVoteCount(i));

            }
        } else {

            for (int i = 0; i <= p.getAnswers().size() - 1; i++) {
                View pollOptionView = LayoutInflater.from(holder.itemView.getContext()).inflate(R.layout.poll_vote_button_layout, holder.optionsContainer, false);
                Button voteButton = pollOptionView.findViewById(R.id.poll_button);
                voteButton.setText(p.getAnswers().get(i));
                // Set a unique tag for each button
                voteButton.setTag(i);

                // Set click listener for the button
                int finalI = i;
                voteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Handle the button click
                        int selectedOptionIndex = finalI;
                        // Here you can handle the vote count or any other logic you need
                        handleVote(p.getId(), selectedOptionIndex);

                    }
                });
                holder.optionsContainer.addView(pollOptionView);
            }
        }
    }

    // Method to handle the vote logic
    private void handleVote(String pollId, int selectedOptionIndex) {
        // Logic to count the vote for the selected option
//        Toast.makeText(mainActivity, "ID:" + pollId + ", answer: " + selectedOptionIndex, Toast.LENGTH_LONG).show();
        Poll poll = mainActivity.getPollById(pollId);
        if(poll == null) {
            Toast.makeText(mainActivity, "Poll not found!", Toast.LENGTH_LONG).show();
            return;
        }
        boolean success = poll.vote(MainActivity.USERNAME, selectedOptionIndex);
        if(!success) {
            Toast.makeText(mainActivity, "Vote could not be addded", Toast.LENGTH_LONG).show();
            return;
        }
        Toast.makeText(mainActivity, "Voted!", Toast.LENGTH_SHORT).show();
        mainActivity.triggerUpdatePolls(pollId, poll);
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

            Log.i("UserNAME count", MainActivity.USERNAME);

            // keep if the poll was created by a friend of ours or ourselves
            if(!users.containsKey(MainActivity.USERNAME)) return false;
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
