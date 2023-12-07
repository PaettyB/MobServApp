package fr.eurecom.mobservapp.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import fr.eurecom.mobservapp.R;
import fr.eurecom.mobservapp.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;

    private FragmentHomeBinding binding;

    private boolean showFinishedPolls = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(new RandomNumListAdapter(1234));



        //Toggle buttons active/finished feeds
        binding.activePollsButton.setOnClickListener(this::toggleActiveFinishedPolls);
        binding.finishedPollsButton.setOnClickListener(this::toggleActiveFinishedPolls);
        return view;
    }

    public void toggleActiveFinishedPolls(View view) {
        binding.finishedPollsButton.setChecked(!showFinishedPolls);
        binding.activePollsButton.setChecked(showFinishedPolls);
        showFinishedPolls = !showFinishedPolls;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}