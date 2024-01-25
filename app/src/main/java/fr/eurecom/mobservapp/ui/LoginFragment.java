package fr.eurecom.mobservapp.ui;

import static android.view.View.GONE;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import fr.eurecom.mobservapp.MainActivity;
import fr.eurecom.mobservapp.R;
import fr.eurecom.mobservapp.databinding.LoginFragmentBinding;
import fr.eurecom.mobservapp.PrefManager;

public class LoginFragment extends Fragment {
//    Log in screen that appears when you first open the app

    LoginFragmentBinding binding;

    TextView usernameTextView;

    BottomNavigationView navView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        hide stuff
        try {
            ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        hide stuff
        navView = getActivity().findViewById(R.id.nav_view);
        navView.setVisibility(GONE);
        super.onCreateView(inflater, container, savedInstanceState);
//        connect elements to views
        binding = LoginFragmentBinding.inflate(inflater, container, false);
        binding.loginButton.setOnClickListener(this::logIn);
        View view = binding.getRoot();
        usernameTextView = view.findViewById(R.id.usernameText);
        return view;
    }

    public void logIn(View view) {
        String username = usernameTextView.getText().toString();
        Log.i("Username", username);
//        check if username was entered
        if (!usernameTextView.getText().toString().isEmpty()) {
//            save username
            new PrefManager((AppCompatActivity) getActivity()).saveLoginDetails(usernameTextView.getText().toString());
//            show stuff that was hidden
            try {
//                ((AppCompatActivity) getActivity()).getSupportActionBar().show();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            navView.setVisibility(View.VISIBLE);
//move to main screen
            NavController navController = Navigation.findNavController(((AppCompatActivity) getActivity()), R.id.nav_host_fragment_activity_main);
            MainActivity.USERNAME = username;
            navController.navigate(R.id.navigation_home);
        }
    }
}
