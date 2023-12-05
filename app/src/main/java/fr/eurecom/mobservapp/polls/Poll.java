package fr.eurecom.mobservapp.polls;

public class Poll {

    private String title;
    private String[] answers;
    private int[] votes;
    private String owner;
    private boolean running = true;

    public Poll(String title, String[] answers, String owner) {
        this.title = title;
        this.answers = answers;
        this.owner = owner;
        this.votes = new int[answers.length];
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getAnswers() {
        return answers;
    }

    public void setAnswers(String[] answers) {
        this.answers = answers;
    }

    public int[] getVotes() {
        return votes;
    }

    public void setVotes(int[] votes) {
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
