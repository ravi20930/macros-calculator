package com.itisravi.macroscalculator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    EditText editTextAge, editTextWeight, editTextHeight;
    Button btnProceed, toggleMale, toggleFemale;
    MaterialButtonToggleGroup maleFemaleToggle;
    TextInputLayout txtViewHeight, txtViewAge, txtViewWeight;
    int age;
    float weight, height;


    String ageFill, weightFill, heightFill;
    String gender = "gender";

    public void initViews() {
        editTextAge = findViewById(R.id.editTextAge);
        editTextWeight = findViewById(R.id.editTextWeight);
        editTextHeight = findViewById(R.id.editTextHeight);
        btnProceed = findViewById(R.id.btnProceed);
        maleFemaleToggle = findViewById(R.id.maleFemaleToggle);
        toggleMale = findViewById(R.id.toggleMale);
        toggleFemale = findViewById(R.id.toggleFemale);
        txtViewHeight = findViewById(R.id.txtViewHeight);
        txtViewAge = findViewById(R.id.txtViewAge);
        txtViewWeight = findViewById(R.id.txtViewWeight);
    }

    public void enableTxtFields() {
        txtViewHeight.setEnabled(true);
        txtViewWeight.setEnabled(true);
        txtViewAge.setEnabled(true);
        txtViewHeight.setHelperTextEnabled(false);
        txtViewWeight.setHelperTextEnabled(false);
        txtViewAge.setHelperTextEnabled(false);
    }

    public void maleFemaleToggle() {
        maleFemaleToggle.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if (isChecked) {
                    enableTxtFields();
                    switch (checkedId) {
                        case R.id.toggleMale:
                            gender = "male";
                            break;
                        case R.id.toggleFemale:
                            gender = "female";
                            break;
                    }
                }
            }
        });
    }

    public void onBtnProceedClick() {
        btnProceed.setOnClickListener(v -> {

            age = Integer.parseInt(editTextAge.getText().toString());
            weight = Float.parseFloat(editTextWeight.getText().toString());
            height = Float.parseFloat(editTextHeight.getText().toString());


            SharedPreferences personData = getSharedPreferences("personData", MODE_PRIVATE);
            SharedPreferences.Editor editor = personData.edit();

            editor.putInt("age", age);
            editor.putFloat("weight", weight);
            editor.putFloat("height", height);
            editor.putString("gender", gender);
            editor.apply();

            Intent intent = new Intent(MainActivity.this, BmrActivity.class);
            startActivity(intent);
        });
    }

    public void dataValidate (EditText editText, TextInputLayout textInputLayout, String string, Float limit) {
        if (editText.getText().toString().trim().length()>0) {
            if (Float.parseFloat(editText.getText().toString().trim())>limit) {
                textInputLayout.setHelperText("You are too "+string+" to use this app");
            }
            else {
                textInputLayout.setHelperTextEnabled(false);
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        initViews();

        editTextAge.addTextChangedListener(personDataFill);
        editTextWeight.addTextChangedListener(personDataFill);
        editTextHeight.addTextChangedListener(personDataFill);

        editTextHeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                dataValidate(editTextHeight, txtViewHeight, "TALL", 230f);
            }
        });
        editTextWeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                dataValidate(editTextWeight, txtViewWeight, "FAT", 400f);
            }
        });


        maleFemaleToggle();

        onBtnProceedClick();
    }

    public TextWatcher personDataFill = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            ageFill = editTextAge.getText().toString();
            weightFill = editTextWeight.getText().toString();
            heightFill = editTextHeight.getText().toString();

            btnProceed.setEnabled(!ageFill.equals("")
                    && !weightFill.equals("")
                    && !heightFill.equals("")
                    && !gender.equals("gender"));

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (editTextAge.getText().toString().trim().length()>0) {
                if (Integer.parseInt(editTextAge.getText().toString().trim())>99) {
                    txtViewAge.setHelperText("You are too OLD to use this app");
                }
                else {
                    txtViewAge.setHelperTextEnabled(false);
                }
            }

        }
    };

    }

