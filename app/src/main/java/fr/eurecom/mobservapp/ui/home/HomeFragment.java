package fr.eurecom.mobservapp.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import fr.eurecom.mobservapp.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    private boolean showFinishedPolls = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.activePollsButton.setChecked(true);
        return root;
    }

    public void toggleActiveFinishedPolls(View view) {
        binding.finishedPollsButton.setChecked(!showFinishedPolls);
        binding.activePollsButton.setChecked(showFinishedPolls);
        showFinishedPolls = !showFinishedPolls;
        Log.i("TEST", "TEST");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}