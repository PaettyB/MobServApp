package fr.eurecom.mobservapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import fr.eurecom.mobservapp.R;
import fr.eurecom.mobservapp.databinding.FragmentFriendsFeedBinding;

public class FriendsFeedFragment extends Fragment {

    private RecyclerView recyclerView;
    private PollListAdapter adapter;

    private FragmentFriendsFeedBinding binding;



    private boolean showFinishedPolls = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentFriendsFeedBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        adapter = new PollListAdapter(getContext());
        recyclerView.setAdapter(adapter);



        //Toggle buttons active/finished feeds
        binding.activePollsButton.setOnClickListener(this::toggleActiveFinishedPolls);
        binding.finishedPollsButton.setOnClickListener(this::toggleActiveFinishedPolls);
        return view;
    }

    public void toggleActiveFinishedPolls(View view) {
        binding.finishedPollsButton.setChecked(!showFinishedPolls);
        binding.activePollsButton.setChecked(showFinishedPolls);
        showFinishedPolls = !showFinishedPolls;

        adapter.setDisplayFinishedPolls(showFinishedPolls);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar friends_feed_toolbar = view.findViewById(R.id.home_toolbar);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();

        NavController navHostFragment = NavHostFragment.findNavController(this);
        NavigationUI.setupWithNavController(friends_feed_toolbar, navHostFragment, appBarConfiguration);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void updateRecyclerView(){
        recyclerView.getAdapter().notifyDataSetChanged();
    }
}