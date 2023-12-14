// PlayGameActivity.java
package com.example.madgame;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import android.os.CountDownTimer;

public class PlayGameActivity extends AppCompatActivity {

    private List<Button> buttons;
    private List<Integer> buttonSequence;
    private int currentButtonIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_game);

        // Initialize buttons
        Button colorTop = findViewById(R.id.colorTop);
        Button colorLeft = findViewById(R.id.colorLeft);
        Button colorRight = findViewById(R.id.colorRight);
        Button colorBottom = findViewById(R.id.colorBottom);

        // Store buttons in a list
        buttons = new ArrayList<>();
        buttons.add(colorTop);
        buttons.add(colorLeft);
        buttons.add(colorRight);
        buttons.add(colorBottom);

        // Create a sequence of button indices in random order
        createRandomButtonSequence();

        // Find the Start Sequence button
        Button startButton = findViewById(R.id.startButton);

        // Set a click listener for the Start Sequence button
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSequenceDisplay();
            }
        });
    }

    private int expectedButtonIndex = 0; // To track the expected button index in the sequence

    public void startSequence(View view) {
        startSequenceDisplay();
    }

    private void startSequenceDisplay() {
        displayColors();
        displayColorsWithTransition();
    }

    private void createRandomButtonSequence() {
        buttonSequence = new ArrayList<>();
        for (int i = 0; i < buttons.size(); i++) {
            buttonSequence.add(i);
        }
        Collections.shuffle(buttonSequence);
    }

    private void displayColors() {
        final int showDuration = 500; // Duration to show each button (in milliseconds)
        final int sequenceDuration = 10000; // Total duration for the sequence (in milliseconds)

        final Handler handler = new Handler();

        final CountDownTimer sequenceTimer = new CountDownTimer(sequenceDuration, showDuration) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (currentButtonIndex < buttonSequence.size()) {
                    hideAllButtons();
                    showButton(buttonSequence.get(currentButtonIndex));
                    currentButtonIndex++;
                }
            }

            @Override
            public void onFinish() {
                initiateInputTransition(); // Transition after the sequence duration
            }
        };

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                sequenceTimer.start(); // Start the sequence display
            }
        }, showDuration); // Start after a brief delay to show the first button immediately
    }


    private void hideAllButtons() {
        for (Button button : buttons) {
            button.setVisibility(View.INVISIBLE); // Hide buttons
        }
    }

    private void showButton(int index) {
        buttons.get(index).setVisibility(View.VISIBLE); // Show the button
    }

    private void displayColorsWithTransition() {
        final Handler handler = new Handler();
        final int delay = 2000; // Adjust the delay between displaying buttons (in milliseconds)
        final int showDuration = 2000; // Duration to show each button (in milliseconds)
        final int transitionDelay = 10000; // Delay before transitioning to the input screen (in milliseconds)

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (currentButtonIndex < buttonSequence.size()) {
                    hideAllButtons();
                    showButton(buttonSequence.get(currentButtonIndex));
                    currentButtonIndex++;
                    handler.postDelayed(this, delay + showDuration);
                } else {
                    // Displayed the sequence, initiate the transition to the input screen
                    initiateInputTransition();
                }
            }
        }, delay);

        // Schedule transition to the input screen after the sequence display
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                initiateInputTransition();
            }
        }, delay * buttonSequence.size() + transitionDelay); // Delay for the entire sequence display plus transition delay
    }

    private void initiateInputTransition() {
        Intent intent = new Intent(PlayGameActivity.this, PlayerInputActivity.class);
        intent.putIntegerArrayListExtra("displayedSequence", (ArrayList<Integer>) buttonSequence);
        startActivity(intent);
        finish(); // Finish this activity so the user cannot go back to the sequence display
    }


}
