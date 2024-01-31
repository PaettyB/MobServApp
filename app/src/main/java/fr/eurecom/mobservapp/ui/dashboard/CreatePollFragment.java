package fr.eurecom.mobservapp.ui.dashboard;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.ArrayList;

import fr.eurecom.mobservapp.MainActivity;
import fr.eurecom.mobservapp.R;
import fr.eurecom.mobservapp.databinding.FragmentCreatePollBinding;
import fr.eurecom.mobservapp.polls.Poll;

public class CreatePollFragment extends Fragment {

    private FragmentCreatePollBinding binding;
    private int numAnswers = 2;
    private long deadline = -1;
    private MainActivity context;

    private SeekBar seekBar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCreatePollBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.addAnswersButton.setOnClickListener(this::addAnswer);
        binding.createPollButton.setOnClickListener(this::createPoll);
        context = (MainActivity) getContext();
        seekBar = binding.seekBar;
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                deadline = msFromPercentage(progress);
                binding.deadlineText.setText(deadlineStringFromMS(deadline));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        return root;
    }

    private long msFromPercentage(int p) {
        if (p == 0) return -1;
        p += 4;
        long scaledP = (int) Math.pow(p, 3);
        long msPerWeek = 604800000;
        return (int) (scaledP * Math.pow(msPerWeek, 1 / 3.0));
    }

    private String deadlineStringFromMS(long durationMS) {
        if (durationMS == -1) return "No Deadline";

        long remainingTimeSeconds = durationMS / 1000;
        int remainingTimeMinutes = (int) (remainingTimeSeconds / 60);
        if (remainingTimeMinutes <= 60) {
            return (remainingTimeMinutes) + " minutes";
        }
        int remainingTimeHours = (int) (remainingTimeMinutes / 60);
        if (remainingTimeHours <= 24) {
            return (remainingTimeHours) + " hours";
        }
        int remainingTimeDays = (int) (remainingTimeHours / 24);
        return remainingTimeDays + " days";
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

    public void clearFields(View view) {
        binding.answersList.removeViews(2, numAnswers - 2);
        numAnswers = 2;
        CardView childCard0 = (CardView) binding.answersList.getChildAt(0);
        ((EditText) ((LinearLayout) childCard0.getChildAt(0)).getChildAt(1)).setText("");

        CardView childCard1 = (CardView) binding.answersList.getChildAt(1);
        ((EditText) ((LinearLayout) childCard1.getChildAt(0)).getChildAt(1)).setText("");
        binding.titleTextField.setText("");
    }

    public void createPoll(View view) {
//        String[] answers = new String[binding.answersList.getChildCount()-1];
        ArrayList<String> answers = new ArrayList<>();
        for (int i = 0; i < binding.answersList.getChildCount() - 1; i++) {
            CardView childCard = (CardView) binding.answersList.getChildAt(i);
            String answerI = ((EditText) ((LinearLayout) childCard.getChildAt(0)).getChildAt(1)).getText().toString();
            answers.add(answerI);
        }
        long actualDeadline = (deadline == -1) ? -1 : System.currentTimeMillis() + deadline;
        Poll poll = new Poll(binding.titleTextField.getText().toString(), answers, MainActivity.USERNAME, System.currentTimeMillis(), actualDeadline, false);
        context.addPoll(poll);
        clearFields(view);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}