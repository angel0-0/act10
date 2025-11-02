package com.angel.act10;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private ImageAdapter adapter;
    private Button prevButton;
    private Button nextButton;
    private Button menuButton;
    private TextView dotsIndicator;
    private TextView textIndicator;

    private final ActivityResultLauncher<Intent> gridLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    int selectedPosition = result.getData().getIntExtra("selected_position", 0);
                    viewPager.setCurrentItem(selectedPosition, false);
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Edge-to-Edge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            int systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars()).top;
            v.setPadding(0, systemBars, 0, 0);
            return insets;
        });

        viewPager = findViewById(R.id.viewPager);
        prevButton = findViewById(R.id.prevButton);
        nextButton = findViewById(R.id.nextButton);
        menuButton = findViewById(R.id.menuButton);
        dotsIndicator = findViewById(R.id.dotsIndicator);
        textIndicator = findViewById(R.id.textIndicator);

        adapter = new ImageAdapter(this);
        viewPager.setAdapter(adapter);

        viewPager.setOffscreenPageLimit(3);
        viewPager.setClipToPadding(false);
        viewPager.setClipChildren(false);
        viewPager.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                updateProgressIndicator(position);
                updateNavigationButtons(position);
            }
        });

        prevButton.setOnClickListener(v -> viewPager.setCurrentItem(viewPager.getCurrentItem() - 1));
        nextButton.setOnClickListener(v -> viewPager.setCurrentItem(viewPager.getCurrentItem() + 1));
        menuButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, GridActivity.class);
            gridLauncher.launch(intent);
        });

        updateProgressIndicator(0);
        updateNavigationButtons(0);
    }

    private void updateNavigationButtons(int position) {
        if (position == 0) {
            prevButton.setVisibility(View.INVISIBLE);
        } else {
            prevButton.setVisibility(View.VISIBLE);
        }

        if (position == adapter.getItemCount() - 1) {
            nextButton.setVisibility(View.INVISIBLE);
        } else {
            nextButton.setVisibility(View.VISIBLE);
        }
    }

    private void updateProgressIndicator(int position) {
        int total = adapter.getItemCount();
        String text = getString(R.string.progress_indicator_format, position + 1, total);
        textIndicator.setText(text);

        StringBuilder dots = new StringBuilder();
        for (int i = 0; i < total; i++) {
            if (i == position) {
                dots.append("● ");
            } else {
                dots.append("○ ");
            }
        }
        dotsIndicator.setText(dots.toString().trim());
    }
}