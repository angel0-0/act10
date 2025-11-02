package com.angel.act10;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private ImageAdapter adapter;
    private Button prevButton, nextButton;
    private TextView dotsIndicator, textIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewPager);
        prevButton = findViewById(R.id.prevButton);
        nextButton = findViewById(R.id.nextButton);
        dotsIndicator = findViewById(R.id.dotsIndicator);
        textIndicator = findViewById(R.id.textIndicator);

        // Pass context to adapter
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
                updateNavigationControls(position);
                updateProgressIndicator(position, adapter.getItemCount());
            }
        });

        prevButton.setOnClickListener(v -> {
            if (viewPager.getCurrentItem() > 0) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
            }
        });

        nextButton.setOnClickListener(v -> {
            if (viewPager.getCurrentItem() < adapter.getItemCount() - 1) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            }
        });

        // Initial state
        updateNavigationControls(0);
        updateProgressIndicator(0, adapter.getItemCount());
    }

    private void updateNavigationControls(int position) {
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

    private void updateProgressIndicator(int currentPosition, int totalItems) {
        StringBuilder dots = new StringBuilder();
        for (int i = 0; i < totalItems; i++) {
            if (i == currentPosition) {
                dots.append("● ");
            } else {
                dots.append("○ ");
            }
        }
        dotsIndicator.setText(dots.toString().trim());

        // Use string resource with placeholders
        textIndicator.setText(getString(R.string.progress_indicator_format, currentPosition + 1, totalItems));
    }
}
