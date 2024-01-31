package fr.eurecom.mobservapp.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import fr.eurecom.mobservapp.R;
import fr.eurecom.mobservapp.databinding.FragmentExploreFeedBinding;

public class NotificationsFragment extends Fragment {

    private FragmentExploreFeedBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentExploreFeedBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textNotifications;
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        ImageButton friendsButton = root.findViewById(R.id.friends_button);
        // Set up a click listener for friends button
        friendsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController((AppCompatActivity) getActivity(), R.id.nav_host_fragment_activity_main);
                navController.navigate(R.id.fragment_friends_list);
            }
        });

        ImageButton settingsButton = root.findViewById(R.id.settings_button);

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController((AppCompatActivity) getActivity(), R.id.nav_host_fragment_activity_main);
                navController.navigate(R.id.fragment_settings);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}