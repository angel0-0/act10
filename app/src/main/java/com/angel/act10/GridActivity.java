package com.angel.act10;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class GridActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);

        // Edge-to-Edge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.grid), (v, insets) -> {
            int systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars()).top;
            v.setPadding(0, systemBars, 0, 0);
            return insets;
        });

        RecyclerView recyclerView = findViewById(R.id.gridRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        GridAdapter adapter = new GridAdapter(position -> {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("selected_position", position);
            setResult(RESULT_OK, resultIntent);
            finish();
        });

        recyclerView.setAdapter(adapter);
    }
}