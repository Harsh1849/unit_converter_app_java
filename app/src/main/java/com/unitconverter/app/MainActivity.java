package com.unitconverter.app;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar action_bar = getSupportActionBar();
        if (action_bar != null) {
            action_bar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.purple_500)));
        }

        CardView length_card, temperature_card, mass_card;

        length_card = findViewById(R.id.cardView_length);
        temperature_card = findViewById(R.id.cardView_temperature);
        mass_card = findViewById(R.id.cardView_mass);

        length_card.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, LengthCalculationActivity.class)));

        temperature_card.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, TemperatureCalculationActivity.class)));

        mass_card.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, WeightCalculationActivity.class)));
    }
}