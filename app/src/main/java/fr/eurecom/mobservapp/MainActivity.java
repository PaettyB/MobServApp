package fr.eurecom.mobservapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import java.util.Comparator;

import fr.eurecom.mobservapp.databinding.ActivityMainBinding;
import fr.eurecom.mobservapp.polls.Poll;
import fr.eurecom.mobservapp.ui.home.HomeFragment;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ArrayList<Poll> polls = new ArrayList<Poll>();

    DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://mobservapp-33d11-default-rtdb.europe-west1.firebasedatabase.app/");
        myRef = database.getReference("polls");

        FragmentManager fm = getSupportFragmentManager();
        NavHostFragment navHostFragment = (NavHostFragment) fm.findFragmentById(R.id.nav_host_fragment_activity_main);
        HomeFragment homeFragment = (HomeFragment)navHostFragment.getChildFragmentManager().getFragments().get(0);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                polls.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Poll readPoll = snapshot.getValue(Poll.class);
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


    public void addPoll(Poll poll){
        String key = myRef.push().getKey();
        myRef.child(key).setValue(poll);
        Log.i("ADDED POLL", ""+poll.getTitle());
    }

    public ArrayList<Poll> getPolls(){
        return polls;
    }



}