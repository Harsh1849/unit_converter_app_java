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

public class WeightCalculationActivity extends AppCompatActivity {

    String[] mass_unit_list = {"Tonne", "Kilogram", "Gram", "Pound", "Ounce"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_calculation);

        ActionBar action_bar = getSupportActionBar();
        if (action_bar != null) {
            action_bar.setTitle("Weight Conversions");
            action_bar.setDisplayShowHomeEnabled(true);
            action_bar.setDisplayHomeAsUpEnabled(true);
            action_bar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.purple_500)));
        }

        AutoCompleteTextView autoCompleteTextViewFrom = findViewById(R.id.autoCompleteTextView_from);
        AutoCompleteTextView autoCompleteTextViewTo = findViewById(R.id.autoCompleteTextView_to);

        EditText outlinedTextFieldMassUnit = findViewById(R.id.mass_units);
        TextView massOutputText = findViewById(R.id.textView_mass_output);

        Button convertBtn = findViewById(R.id.button_convert_mass);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, mass_unit_list);

        autoCompleteTextViewFrom.setAdapter(adapter);
        autoCompleteTextViewTo.setAdapter(adapter);

        outlinedTextFieldMassUnit.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(WeightCalculationActivity.this, v);
            }
        });

        convertBtn.setOnClickListener(v -> {
            try {
                String fromUnit = autoCompleteTextViewFrom.getText().toString();
                String toUnit = autoCompleteTextViewTo.getText().toString();

                double enteredUnits = Double.parseDouble(outlinedTextFieldMassUnit.getText().toString());

                WeightCalculationConverterUnit.Unit fromUnit1 = WeightCalculationConverterUnit.Unit.fromString(fromUnit);
                WeightCalculationConverterUnit.Unit toUnit1 = WeightCalculationConverterUnit.Unit.fromString(toUnit);

                WeightCalculationConverterUnit converter = new WeightCalculationConverterUnit(fromUnit1, toUnit1);
                double result = converter.convert(enteredUnits);

                CardView output_card = findViewById(R.id.cardView_mass_output);
                output_card.setVisibility(View.VISIBLE);
                massOutputText.setText(result + " " + toUnit);
                hideKeyboard(WeightCalculationActivity.this, v);
            } catch (NumberFormatException e) {
                if (autoCompleteTextViewFrom.getText().toString().trim().isEmpty()) {
                    showToast(WeightCalculationActivity.this, "Select option 'from' dropdown first!");
                    return;
                } else if (autoCompleteTextViewTo.getText().toString().trim().isEmpty()) {
                    showToast(WeightCalculationActivity.this, "Select option 'to' dropdown first!");
                    return;
                }
                outlinedTextFieldMassUnit.setError("Enter Some Value!");
                outlinedTextFieldMassUnit.requestFocus();
            } catch (Exception e) {
                showToast(WeightCalculationActivity.this, "Some Error occurred!");
            }
        });
    }
}