// PlayGameActivity.java
package com.example.madgame;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class PlayGameActivity extends AppCompatActivity {

    private List<Button> buttons;
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

        // Display colors sequentially
        displayColors();
    }

    private void displayColors() {
        final Handler handler = new Handler();
        final int delay = 1000; // Adjust the delay between displaying buttons (in milliseconds)
        final int showDuration = 500; // Duration to show each button (in milliseconds)

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (currentButtonIndex < buttons.size()) {
                    hideAllButtons();
                    showButton(currentButtonIndex);
                    currentButtonIndex++;
                    handler.postDelayed(this, delay + showDuration);
                }
            }
        }, delay);
    }

    private void hideAllButtons() {
        for (Button button : buttons) {
            button.setVisibility(View.INVISIBLE); // Hide buttons
        }
    }

    private void showButton(int index) {
        buttons.get(index).setVisibility(View.VISIBLE); // Show the button
    }
}
