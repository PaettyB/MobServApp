package fr.eurecom.mobservapp.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import fr.eurecom.mobservapp.MainActivity;
import fr.eurecom.mobservapp.R;
import fr.eurecom.mobservapp.databinding.FragmentFriendsListBinding;
import fr.eurecom.mobservapp.polls.User;

public class FriendsListFragment extends Fragment {

    FragmentFriendsListBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentFriendsListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        ScrollView scrollView = view.findViewById(R.id.friends_list_scroll);
        View friendElementLayout = inflater.inflate(R.layout.friend_element, null);
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        
        // Get the users and their friends from MainActivity
        HashMap<String, User> friendsList = MainActivity.getUsers();

        
        // Iterate over each friend and add a friend element to the scroll view
        for (Map.Entry<String, User> user : friendsList.entrySet()) {
            Log.i("MAin username", MainActivity.USERNAME);
            Log.i("Current username", user.getValue().getName());
            if (Objects.equals(MainActivity.USERNAME, user.getValue().getName())) {

                ArrayList<String> friends = user.getValue().getFriends();
                Log.i("Friends size", ""+friends.size());
                for (String friend : friends) {
                    Log.i("My friend", friend);
                    // Create a new friend element layout for each friend
                    View friendLayout = inflater.inflate(R.layout.friend_element, null);

                    // Set the friend details in the friend element layout
                    TextView friendNameTextView = friendLayout.findViewById(R.id.tvUserName);
                    TextView friendNameUsername = friendLayout.findViewById(R.id.tvUserHandle);

                    friendNameTextView.setText(friend);
                    friendNameUsername.setText("@"+friend);
                    // Add the friend element layout to the scroll view
                    linearLayout.addView(friendLayout);
                }


            }
        }

        scrollView.addView(linearLayout);

        Button backButton = view.findViewById(R.id.back_button);
        Button searchButton = view.findViewById(R.id.search_button);


        // Set up a click listener for friends button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController((AppCompatActivity) getActivity(), R.id.nav_host_fragment_activity_main);
                navController.navigate(R.id.navigation_home);
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController((AppCompatActivity) getActivity(), R.id.nav_host_fragment_activity_main);
                navController.navigate(R.id.fragment_search_friends);
            }
        });
        
        return view;
    }

}
