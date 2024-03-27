package com.unitconverter.app;

import static com.unitconverter.app.Extensions.hideKeyboard;
import static com.unitconverter.app.Extensions.showToast;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class TemperatureCalculationActivity extends AppCompatActivity {

    String[] temperature_unit_list = {"Celsius", "Fahrenheit", "Kelvin"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature_calculation);

        ActionBar action_bar = getSupportActionBar();
        if (action_bar != null) {
            action_bar.setTitle("Temperature Converter");
            action_bar.setDisplayShowHomeEnabled(true);
            action_bar.setDisplayHomeAsUpEnabled(true);
            action_bar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.purple_500)));
        }

        AutoCompleteTextView autoCompleteTextViewFrom = findViewById(R.id.autoCompleteTextView_from);
        AutoCompleteTextView autoCompleteTextViewTo = findViewById(R.id.autoCompleteTextView_to);

        EditText outlinedTextFieldTemperatureUnit = findViewById(R.id.temperature_units);
        TextView temperatureOutputText = findViewById(R.id.textView_temperature_output);

        Button convertBtn = findViewById(R.id.button_convert_temperature);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, temperature_unit_list);

        autoCompleteTextViewFrom.setAdapter(adapter);
        autoCompleteTextViewTo.setAdapter(adapter);

        outlinedTextFieldTemperatureUnit.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(TemperatureCalculationActivity.this, v);
            }
        });

        convertBtn.setOnClickListener(v -> {
            try {
                String fromUnit = autoCompleteTextViewFrom.getText().toString();
                String toUnit = autoCompleteTextViewTo.getText().toString();

                double enteredUnits = Double.parseDouble(outlinedTextFieldTemperatureUnit.getText().toString());

                TemperatureCalculationConverterUnit.Unit fromUnit1 = TemperatureCalculationConverterUnit.Unit.fromString(fromUnit);
                TemperatureCalculationConverterUnit.Unit toUnit1 = TemperatureCalculationConverterUnit.Unit.fromString(toUnit);

                TemperatureCalculationConverterUnit converter = new TemperatureCalculationConverterUnit(fromUnit1, toUnit1);
                double result = converter.convert(enteredUnits);

                CardView output_card = findViewById(R.id.cardView_temperature_output);
                output_card.setVisibility(View.VISIBLE);
                temperatureOutputText.setText(result + " " + toUnit);
                hideKeyboard(TemperatureCalculationActivity.this, v);
            } catch (NumberFormatException e) {
                if (autoCompleteTextViewFrom.getText().toString().trim().isEmpty()) {
                    showToast(TemperatureCalculationActivity.this, "Select option 'from' dropdown first!");
                    return;
                } else if (autoCompleteTextViewTo.getText().toString().trim().isEmpty()) {
                    showToast(TemperatureCalculationActivity.this, "Select option 'to' dropdown first!");
                    return;
                }
                outlinedTextFieldTemperatureUnit.setError("Enter Some Value!");
                outlinedTextFieldTemperatureUnit.requestFocus();
            } catch (Exception e) {
                showToast(TemperatureCalculationActivity.this, "Some Error occurred!");
            }
        });
    }
}