package fr.eurecom.mobservapp.ui.dashboard;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import fr.eurecom.mobservapp.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private ScrollView scrollView;
    private LinearLayout answersList;
    private int numAnswers = 2;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.addAnswersButton.setOnClickListener(this::addAnswer);

        answersList = binding.answersList;

        return root;
    }

    public void addAnswer(View view) {
        Context context = getContext();
        Log.i("ADD", "ADDDED ANSWER");
        CardView cardView = new CardView(context);
        LinearLayout cardLin = new LinearLayout(context);
        cardLin.setOrientation(LinearLayout.VERTICAL);

        TextView textView = new TextView(context);
        textView.setText("Answer" + numAnswers++);

        EditText editText = new EditText(context);
        editText.setText("");
        editText.setHint("Answer" + numAnswers);

        cardLin.addView(textView);
        cardLin.addView(editText);
        cardView.addView(cardLin);
        answersList.addView(cardView,answersList.indexOfChild(binding.addAnswersButton));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            binding.scrollView.scrollToDescendant(binding.addAnswersButton);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}