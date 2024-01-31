package fr.eurecom.mobservapp;

import static androidx.navigation.ui.NavigationUI.setupActionBarWithNavController;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

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
import java.util.HashMap;

import fr.eurecom.mobservapp.databinding.ActivityMainBinding;
import fr.eurecom.mobservapp.polls.Poll;
import fr.eurecom.mobservapp.polls.User;
import fr.eurecom.mobservapp.ui.home.FriendsFeedFragment;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ArrayList<Poll> polls = new ArrayList<Poll>();
    private static HashMap<String, User> users = new HashMap<>();

    DatabaseReference myRef;
    DatabaseReference userRef;

    FriendsFeedFragment homeFragment;

    public static String USERNAME = "test";

    NavController mainNavController;


    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // hide top toolbar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(1024,1024);
        getSupportActionBar().hide();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        mainNavController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        setupActionBarWithNavController(this, mainNavController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, mainNavController);
        mainNavController.enableOnBackPressed(false);

        //        check if user is logged in
        if(new PrefManager(this).isUserLogedOut()){
//          if not, move to log screen
            mainNavController.navigate(R.id.login_fragment);
        }
        USERNAME = new PrefManager(this).getUser();
        Log.i("Main activity user", new PrefManager(this).getUser());

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://mobservapp-33d11-default-rtdb.europe-west1.firebasedatabase.app/");
        myRef = database.getReference("polls");

        userRef = database.getReference("users");

//        FragmentManager fm = getSupportFragmentManager();
//        NavHostFragment navHostFragment = (NavHostFragment) fm.findFragmentById(R.id.nav_host_fragment_activity_main);
//        homeFragment = (FriendsFeedFragment) navHostFragment.getChildFragmentManager().getFragments().get(0);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                polls.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Poll readPoll = snapshot.getValue(Poll.class);
                    readPoll.setId(snapshot.getKey());
                    readPoll.setTimeTexts();
                    Log.i("MAIN USERNAME", USERNAME);
                    // Check if the user has voted for the poll in question;
                    if(readPoll.getOwner().equals(USERNAME)) {
                        readPoll.setVoted(true);
                    } else {
                        for (ArrayList<String> votesPerAnswer : readPoll.getVotes())
                            A:{
                                for (String s : votesPerAnswer) {
                                    if (s.equals(USERNAME)) {
                                        readPoll.setVoted(true);
                                        break A;
                                    }
                                }
                            }
                    }

                    polls.add(readPoll);
                }
//                try {
//                    Thread.sleep(200);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }

                FragmentManager fm = getSupportFragmentManager();
                NavHostFragment navHostFragment = (NavHostFragment) fm.findFragmentById(R.id.nav_host_fragment_activity_main);
                if((navHostFragment.getChildFragmentManager().getFragments().get(0) instanceof FriendsFeedFragment)) {
                    homeFragment = (FriendsFeedFragment) navHostFragment.getChildFragmentManager().getFragments().get(0);

                    Log.i("Polls Updated!", "Poll count: " + polls.size());
                    Log.i("Fragments" , ""+ navHostFragment.getChildFragmentManager().getFragments().size());
                    homeFragment.updateRecyclerView();
                }
            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                users.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    if(user.getFriends() == null)
                        user.setFriends(new ArrayList<>());
                    users.put(user.getName(), user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//        User elvina = new User("Ingvild");
//        elvina.setFriends(new ArrayList<String>(Arrays.asList("Patrick")));
//        addUser(elvina);
    }

    public void setLocalPollProperties() {
        for(Poll readPoll : polls) {
            readPoll.setVoted(false);
            if(readPoll.getOwner().equals(USERNAME)) {
                readPoll.setVoted(true);
            } else {
                for (ArrayList<String> votesPerAnswer : readPoll.getVotes())
                    A:{
                        for (String s : votesPerAnswer) {
                            if (s.equals(USERNAME)) {
                                readPoll.setVoted(true);
                                break A;
                            }
                        }
                    }
            }
        }
    }

    public void addUser(User user) {
        String key = userRef.push().getKey();
        userRef.child(key).setValue(user);
        Log.i("ADDED USER", user.getName());

    }



    /**
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.my_custom_button) {
            // Handle your button click here
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        return NavigationUI.onNavDestinationSelected(item, navController)
                || super.onOptionsItemSelected(item);
    }
     **/

    public void addPoll(Poll poll) {
        String key = myRef.push().getKey();
        poll.setId(key);
        myRef.child(key).setValue(poll);
        Log.i("ADDED POLL", "" + poll.getTitle());
    }

    public Poll getPollById(String id) {
        for(Poll p : polls) {
            if(p.getId().equals(id)){
                return p;
            }
        }
        return null;
    }

    public void triggerUpdatePolls(String pollId, Poll poll) {
        homeFragment.updateRecyclerView();
        myRef.child(pollId).child("votes").setValue(poll.getVotes());
    }


    public ArrayList<Poll> getPolls() {
        return polls;
    }

    public static HashMap<String, User> getUsers() {
        return users;
    }

    public NavController getMainNavController() {
        return mainNavController;
    }
}