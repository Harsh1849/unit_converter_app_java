package com.unitconverter.app;

import static com.unitconverter.app.Extensions.showToast;
import static com.unitconverter.app.Extensions.hideKeyboard;
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

public class LengthCalculationActivity extends AppCompatActivity {

    String[] length_unit_list = {"Inch", "Centimeter", "Mile", "Kilometer", "Foot", "Yard"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_length_calculation);

        ActionBar action_bar = getSupportActionBar();
        if (action_bar != null) {
            action_bar.setTitle("Length Converter");
            action_bar.setDisplayShowHomeEnabled(true);
            action_bar.setDisplayHomeAsUpEnabled(true);
            action_bar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.purple_500)));
        }

        AutoCompleteTextView autoCompleteTextViewFrom = findViewById(R.id.autoCompleteTextView_from);
        AutoCompleteTextView autoCompleteTextViewTo = findViewById(R.id.autoCompleteTextView_to);

        EditText outlinedTextFieldLengthUnit = findViewById(R.id.length_units);
        TextView lengthOutputText = findViewById(R.id.textView_length_output);

        Button convertBtn = findViewById(R.id.button_convert_length);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, length_unit_list);

        autoCompleteTextViewFrom.setAdapter(adapter);
        autoCompleteTextViewTo.setAdapter(adapter);

        outlinedTextFieldLengthUnit.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(LengthCalculationActivity.this, v);
            }
        });

        convertBtn.setOnClickListener(v -> {
            try {
                String fromUnit = autoCompleteTextViewFrom.getText().toString();
                String toUnit = autoCompleteTextViewTo.getText().toString();

                double enteredUnits = Double.parseDouble(outlinedTextFieldLengthUnit.getText().toString());

                LengthCalculationConverterUnit.Unit fromUnit1 = LengthCalculationConverterUnit.Unit.fromString(fromUnit);
                LengthCalculationConverterUnit.Unit toUnit1 = LengthCalculationConverterUnit.Unit.fromString(toUnit);

                LengthCalculationConverterUnit converter = new LengthCalculationConverterUnit(fromUnit1, toUnit1);
                double result = converter.convert(enteredUnits);

                CardView output_card = findViewById(R.id.cardView_length_output);
                output_card.setVisibility(View.VISIBLE);
                lengthOutputText.setText(result + " " + toUnit);
                hideKeyboard(LengthCalculationActivity.this, v);
            } catch (NumberFormatException e) {
                if (autoCompleteTextViewFrom.getText().toString().trim().isEmpty()) {
                    showToast(LengthCalculationActivity.this, "Select option 'from' dropdown first!");
                    return;
                } else if (autoCompleteTextViewTo.getText().toString().trim().isEmpty()) {
                    showToast(LengthCalculationActivity.this, "Select option 'to' dropdown first!");
                    return;
                }
                outlinedTextFieldLengthUnit.setError("Enter Some Value!");
                outlinedTextFieldLengthUnit.requestFocus();
            } catch (Exception e) {
                showToast(LengthCalculationActivity.this, "Some Error occurred!");
            }
        });
    }
}