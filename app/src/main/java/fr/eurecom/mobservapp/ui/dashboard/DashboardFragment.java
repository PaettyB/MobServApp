package fr.eurecom.mobservapp.ui.dashboard;

import android.app.Activity;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import fr.eurecom.mobservapp.MainActivity;
import fr.eurecom.mobservapp.databinding.FragmentDashboardBinding;
import fr.eurecom.mobservapp.polls.Poll;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private int numAnswers = 2;
    private MainActivity context;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.addAnswersButton.setOnClickListener(this::addAnswer);
        binding.createPollButton.setOnClickListener(this::createPoll);
        context = (MainActivity) getContext();
        return root;
    }

    public void addAnswer(View view) {
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
        binding.answersList.addView(cardView, binding.answersList.indexOfChild(binding.addAnswersButton));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            binding.scrollView.scrollToDescendant(binding.addAnswersButton);
        }
    }

    public void createPoll(View view) {
        String[] answers = new String[binding.answersList.getChildCount()-1];
        for(int i = 0; i < answers.length; i++) {
            CardView childCard = (CardView) binding.answersList.getChildAt(i);
            String answerI = ((EditText)((LinearLayout)childCard.getChildAt(0)).getChildAt(1)).getText().toString();
            answers[i] = answerI;
        }
        Poll poll = new Poll(binding.titleTextField.getText().toString(), answers, "OWNER");
        context.addPoll(poll);
        Toast.makeText(context, "Poll added", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}