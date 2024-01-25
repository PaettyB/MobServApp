package fr.eurecom.mobservapp.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import fr.eurecom.mobservapp.MainActivity;
import fr.eurecom.mobservapp.R;
import fr.eurecom.mobservapp.databinding.FragmentFriendsListBinding;
import fr.eurecom.mobservapp.databinding.SearchFriendsFragmentBinding;
import fr.eurecom.mobservapp.polls.User;

public class SearchFriendsFragment extends Fragment {

    TextView usernameTextView;

    SearchFriendsFragmentBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = SearchFriendsFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        Button backButton = view.findViewById(R.id.back_button);
        Button searchButton = view.findViewById(R.id.search_friend_button);
        usernameTextView = view.findViewById(R.id.search_field);
        searchButton.setOnClickListener(this::addFriend);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController((AppCompatActivity) getActivity(), R.id.nav_host_fragment_activity_main);
                navController.navigate(R.id.fragment_friends_list);
            }
        });
        return view;
    }

    public void addFriend(View view) {
        boolean friendAdded = false;
        String username = usernameTextView.getText().toString();
        Log.i("Main username (search)", MainActivity.USERNAME);
        // Get the list of users from MainActivity
        HashMap<String, User> userList = MainActivity.getUsers();
        for (Map.Entry<String, User> user : userList.entrySet()) {
            if (Objects.equals(username, user.getValue().getName())) {
                User me = userList.get(MainActivity.USERNAME);
                assert me != null;
                if(contains(me.getFriends(), username)){
                    Log.i("Friend already exists (search)", username);
                    Toast.makeText((AppCompatActivity) getActivity(), user.getValue().getName()+" is already your friend", Toast.LENGTH_LONG).show();
                    return;
                }
                if(username.equals(MainActivity.USERNAME)){
                    Toast.makeText((AppCompatActivity) getActivity(), "You are logged in as "+user.getValue().getName(), Toast.LENGTH_LONG).show();
                    return;
                }
                me.addFriend(user.getValue().getName());
                userList.put(MainActivity.USERNAME, me);
                Log.i("Added friend (search)", user.getValue().getName());
                Toast.makeText((AppCompatActivity) getActivity(), "Added friend "+user.getValue().getName(), Toast.LENGTH_LONG).show();
                friendAdded = true;
            }

        }
        if(!friendAdded){
            Log.i("Person not found (search)", username);
            Toast.makeText((AppCompatActivity) getActivity(), "User not found", Toast.LENGTH_LONG).show();
        }
    }

    public static boolean contains(ArrayList<String> arr, String item) {
        for (String n : arr) {
            if (item.equals(n)) {
                return true;
            }
        }
        return false;
    }
}
