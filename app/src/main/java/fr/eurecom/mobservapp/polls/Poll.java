package fr.eurecom.mobservapp.polls;

import android.util.Log;

import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.Exclude;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Poll {
    private String id;
    private String title;
    private ArrayList<String> answers;
    private ArrayList<ArrayList<String>> votes;
    private String owner;
    private long created;
    private long deadline;
    private boolean isPublic;
    @Exclude private boolean running = true;
    @Exclude private boolean voted = false;
    @Exclude private String remainingText;
    @Exclude private String createdText;



    public Poll(String title, ArrayList<String> answers, String owner, long created, long deadline, boolean voted) {
        this.title = title;
        this.answers = answers;
        this.owner = owner;
        this.votes = new ArrayList<ArrayList<String>>();
        for(int i = 0; i < answers.size(); i++) {
            ArrayList<String> dummyList = new ArrayList<>();
            dummyList.add("dummy");
            votes.add(dummyList);
        }
        this.created = created;
        this.deadline = deadline;
        this.id = "null";
        this.voted = voted;

    }

    public void setTimeTexts() {
        Date date = new Date(created);
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yy HH:mm");
        createdText = format.format(date);

        if(deadline == -1) {
            remainingText = "No Deadline";
            return;
        }
        long remainingTime = deadline - System.currentTimeMillis();
        if(remainingTime < 0) {
            Log.i("DEADLINE", deadline+"");
            running = false;
            remainingText = "Finished";
            return;
        }
        running = true;
        long remainingTimeSeconds = remainingTime / 1000;
        if(remainingTimeSeconds <= 60) {
            remainingText = "Ends in " + (remainingTimeSeconds)+" s";
            return;
        }
        int remainingTimeMinutes = (int) (remainingTimeSeconds / 60);
        if(remainingTimeMinutes <= 60) {
            remainingText = "Ends in " + (remainingTimeMinutes)+" min";
            return;
        }
        int remainingTimeHours = (int) (remainingTimeMinutes / 60);
        if(remainingTimeHours <= 24) {
            remainingText = "Ends in " + (remainingTimeHours)+" h";
            return;
        }
        int remainingTimeDays = (int) (remainingTimeHours / 24);
        remainingText = "Ends in " + remainingTimeDays + " days";
    }

    public boolean vote(String username, int selectedOptionIndex) {
        if(voted) return false;
        if(!votes.get(selectedOptionIndex).contains(username)){
            votes.get(selectedOptionIndex).add(username);
            voted = true;
            Log.i("VOTE", "VOTED");
            return true;
        }
        return false;
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

    public String getTimeRemainingText() {
        return remainingText;
    }

    public String getCreatedText() {
        return createdText;
    }

    public void setDeadline(long deadline) {
        this.deadline = deadline;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }


    public int getVoteCount(int answerIndex) {
        // Subtract the dummy
        return votes.get(answerIndex).size()-1;
    }

    public int getTotalVotes() {
        int sum = 0;
        for(ArrayList<String> a : votes) {
            sum  += a.size()-1;
        }
        return sum;
    }

    public float getVotePercentage(int answerIndex) {
        return getVoteCount(answerIndex) / (float) getTotalVotes();
    }
}
