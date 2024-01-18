package fr.eurecom.mobservapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.ArrayList;
import java.util.Comparator;

import fr.eurecom.mobservapp.databinding.ActivityMainBinding;
import fr.eurecom.mobservapp.polls.Poll;
import fr.eurecom.mobservapp.ui.home.HomeFragment;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ArrayList<Poll> polls = new ArrayList<Poll>();

    DatabaseReference myRef;

    public static String USERNAME = "Elvina";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://mobservapp-33d11-default-rtdb.europe-west1.firebasedatabase.app/");
        myRef = database.getReference("polls");

        FragmentManager fm = getSupportFragmentManager();
        NavHostFragment navHostFragment = (NavHostFragment) fm.findFragmentById(R.id.nav_host_fragment_activity_main);
        HomeFragment homeFragment = (HomeFragment) navHostFragment.getChildFragmentManager().getFragments().get(0);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                polls.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Poll readPoll = snapshot.getValue(Poll.class);
                    readPoll.setId(snapshot.getKey());

                    // Check if the user has voted for the poll in question;
                    if (readPoll.getVotes() == null) {
                        ArrayList<ArrayList<String>> newList = new ArrayList<>();
                        for(int i = 0; i < readPoll.getAnswers().size(); i++) {
                            newList.add(new ArrayList<String>());
                        }
                        readPoll.setVotes(newList);
                    }
                    for (ArrayList<String> votesPerAnswer : readPoll.getVotes())
                        A:{
                            if (votesPerAnswer == null) {

                            }
                            for (String s : votesPerAnswer) {
                                if (s.equals(USERNAME)) {
                                    readPoll.setVoted(true);
                                    break A;
                                }
                            }
                        }
                    while(readPoll.getVotes().size() < readPoll.getAnswers().size()) {
                        readPoll.getVotes().add(new ArrayList<>());
                    }


                    // CHECK if the poll is still running
                    if (readPoll.getDeadline() != -1) {
                        if (System.nanoTime() > readPoll.getDeadline()) {
                            // Deadline is over
                            readPoll.setRunning(false);
                        }
                    }

                    polls.add(readPoll);
                }
                Log.i("Polls Updated!", "Poll count: " + polls.size());
                homeFragment.updateRecyclerView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void addPoll(Poll poll) {
        String key = myRef.push().getKey();
        poll.setId(key);
        myRef.child(key).setValue(poll);
        Log.i("ADDED POLL", "" + poll.getTitle());
    }

    public ArrayList<Poll> getPolls() {
        return polls;
    }


}