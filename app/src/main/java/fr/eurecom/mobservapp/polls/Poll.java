package fr.eurecom.mobservapp.polls;

import java.util.ArrayList;

public class Poll {

    private String title;
    private ArrayList<String> answers;
    private ArrayList<Integer> votes;
    private String owner;
    private boolean running = true;

    public Poll(String title, ArrayList<String> answers, String owner) {
        this.title = title;
        this.answers = answers;
        this.owner = owner;
        this.votes = new ArrayList<>();
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

    public ArrayList<Integer> getVotes() {
        return votes;
    }

    public void setVotes(ArrayList<Integer> votes) {
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
}
