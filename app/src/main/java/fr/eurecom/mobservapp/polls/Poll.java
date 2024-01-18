package fr.eurecom.mobservapp.polls;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.Arrays;

public class Poll {


    private String id;
    private String title;
    private ArrayList<String> answers;
    private ArrayList<ArrayList<String>> votes;
    private String owner;

    private long created;
    private long deadline;
    @Exclude
    private boolean running = true;
    @Exclude boolean voted = false;



    public Poll(String title, ArrayList<String> answers, String owner, long created, long deadline, boolean voted) {
        this.title = title;
        this.answers = answers;
        this.owner = owner;
        this.votes = new ArrayList<ArrayList<String>>();
        for(int i = 0; i < answers.size(); i++) {
            votes.add(new ArrayList<String>());
        }
        this.created = created;
        this.deadline = deadline;
        this.id = "null";
        this.voted = voted;

    }

    public Poll() {
        // This constructor is needed to be created from the firebase database
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
    }

    public ArrayList<ArrayList<String>> getVotes() {
        return votes;
    }

    public void setVotes(ArrayList<ArrayList<String>> votes) {
        this.votes = votes;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public boolean isVoted() {
        return voted;
    }

    public void setVoted(boolean voted) {
        this.voted = voted;
    }

    public long getDeadline() {
        return deadline;
    }

    public void setDeadline(long deadline) {
        this.deadline = deadline;
    }
}
