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
        View root = binding.getRoot();
        //binding.activePollsButton.setChecked(true);

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(new RandomNumListAdapter(1234));
        return view;
    }

    public void toggleActiveFinishedPolls(View view) {
        //binding.finishedPollsButton.setChecked(!showFinishedPolls);
        //binding.activePollsButton.setChecked(showFinishedPolls);
        showFinishedPolls = !showFinishedPolls;
        Log.i("TEST", "TEST");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}