package com.itisravi.macroscalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;

public class BmrActivity extends AppCompatActivity {

    Spinner spinnerActivityLevel, spinnerGoal;
    ArrayAdapter adapterActivityLevels, adapterGoal;
    TextView finalCals, bmr, protein, carbs, fats, inGramsP, inGramsC, inGramsF;
    AutoCompleteTextView autoCompleteTextViewActivity, autoCompleteTextViewGoal;

    int ageFinal, intKcal, kcalGain, kcalLose, gramsP, gramsC, gramsF;
    int loseOrGain = 300;
    float weightFinal, heightFinal, bmrGeneral;
    String gender;


    public float bmrFormula(double m1, double m2, double m3, double m4) {
        float bmrValue = (float) (m1 + (m2 * weightFinal) + (m3 * heightFinal) - (m4 * ageFinal));
        return bmrValue;
    }

    public float bmrMale() {
        float value = bmrFormula(66.4, 13.75, 5.003, 6.755);
        float valueRounded = Math.round(value);
        bmrGeneral = valueRounded;
        return bmrGeneral;
    }

    public float bmrFemale() {
        float value =   bmrFormula(655.1, 9.536, 1.85, 4.676);
        float valueRounded = Math.round(value);
        bmrGeneral = valueRounded;
        return bmrGeneral;
    }

    public void maleOrFemale() {
        if (gender.equals("male")) {
            bmrMale();
        }
        else {
            bmrFemale();
        }
    }

    public void inGramsGain(int cp, int pp, int fp) {
        kcalGain = intKcal+loseOrGain;
        gramsP = kcalGain*pp/400;
        gramsC = kcalGain*cp/400;
        gramsF = kcalGain*fp/900;
    }

    public void inGramsLose(int cp, int pp, int fp) {
        kcalLose = intKcal-loseOrGain;
        gramsP = kcalLose*pp/400;
        gramsC = kcalLose*cp/400;
        gramsF = kcalLose*fp/900;
    }

    public void inGrams(int cp, int pp, int fp) {
        gramsP = intKcal*pp/400;
        gramsC = intKcal*cp/400;
        gramsF = intKcal*fp/900;
    }

    public void setTextInGrams() {
        inGramsP.setText(gramsP+"g");
        inGramsC.setText(gramsC+"g");
        inGramsF.setText(gramsF+"g");
    }

    public void calorie(float f){
        float kcal = bmrGeneral * f;
        intKcal = Math.round(kcal);
    }

    public void initTxtViews() {
        finalCals = findViewById(R.id.txtViewFinalCals);
        bmr = findViewById(R.id.txtViewBmr);
        inGramsP = findViewById(R.id.txtViewGramsP);
        inGramsC = findViewById(R.id.txtViewGramsC);
        inGramsF = findViewById(R.id.txtViewGramsF);
        autoCompleteTextViewActivity = findViewById(R.id.autoCompleteTextViewActivity);
        autoCompleteTextViewGoal = findViewById(R.id.autoCompleteTextViewGoal);
    }

//    public void spinnerActivityLevel() {
//
//        spinnerActivityLevel = findViewById(R.id.spinnerActivityLevel);
//        adapterActivityLevels = ArrayAdapter.createFromResource(this, R.array.activityLevels,
//                android.R.layout.simple_spinner_item);
//        adapterActivityLevels.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerActivityLevel.setAdapter(adapterActivityLevels);
//
//        spinnerActivityLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                switch (position) {
//                    case 0:
//                        calorie(1.20f);
//                        break;
//                    case 1:
//                        calorie(1.375f);
//                        break;
//                    case 2:
//                        calorie(1.55f);
//                        break;
//                    case 3:
//                        calorie(1.725f);
//                        break;
//                    case 4:
//                        calorie(1.9f);
//                        break;
//                    default:
//                        calorie(1.0f);
//                        break;
//
//                }
//                spinnerGoal();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//    }

//    public void spinnerGoal() {
//        spinnerGoal = findViewById(R.id.spinnerGoal);
//        adapterGoal = ArrayAdapter.createFromResource(this, R.array.goal, android.R.layout.simple_spinner_item);
//        adapterGoal.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerGoal.setAdapter(adapterGoal);
//
//        spinnerGoal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if (position==1) {
//                    inGramsGain(45, 25, 30);
//                    finalCals.setText("You need to consume " + kcalGain + " Calories according to your activity level");
//                }
//                else if (position==2) {
//                    inGramsLose(25, 35, 40);
//                    finalCals.setText("You need to consume " + kcalLose + " Calories according to your activity level");
//                }
//                else {
//                    inGrams(50, 20, 30);
//                    finalCals.setText("You need to consume " + intKcal + " Calories according to your activity level");
//                }
//                inGramsP.setText(gramsP+"g");
//                inGramsC.setText(gramsC+"g");
//                inGramsF.setText(gramsF+"g");
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//    }

    public void dropDownActivity() {
        String[] lifestyle = getResources().getStringArray(R.array.activityLevels);
        ArrayAdapter arrayAdapterActivity = new ArrayAdapter(this, R.layout.dropdown, lifestyle);

        autoCompleteTextViewActivity.setAdapter(arrayAdapterActivity);

        autoCompleteTextViewActivity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 1:
                        calorie(1.375f);
                        break;
                    case 2:
                        calorie(1.55f);
                        break;
                    case 3:
                        calorie(1.725f);
                        break;
                    case 4:
                        calorie(1.9f);
                        break;
                    default:
                        calorie(1.20f);
                        break;
                }
                dropDownGoal();
            }
        });
    }

    public void dropDownGoal() {
        String[] goal = getResources().getStringArray(R.array.goal);
        ArrayAdapter arrayAdapterGoal = new ArrayAdapter(this, R.layout.dropdown, goal);

        autoCompleteTextViewGoal.setAdapter(arrayAdapterGoal);
        autoCompleteTextViewGoal.setText(arrayAdapterGoal.getItem(0).toString(), false);
        
        inGrams(50, 20, 30);
        finalCals.setText("You need to consume " + intKcal + " Calories according to your activity level");
        setTextInGrams();

        autoCompleteTextViewGoal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position==1) {
                    inGramsGain(45, 25, 30);
                    finalCals.setText("You need to consume " + kcalGain + " Calories according to your activity level");
                }
                else if (position==2) {
                    inGramsLose(25, 35, 40);
                    finalCals.setText("You need to consume " + kcalLose + " Calories according to your activity level");
                }
                else {
                    inGrams(50, 20, 30);
                    finalCals.setText("You need to consume " + intKcal + " Calories according to your activity level");
                }
                setTextInGrams();
            }
        });

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmr);
        getSupportActionBar().hide();

        SharedPreferences personData = getSharedPreferences("personData", MODE_PRIVATE);

        ageFinal = personData.getInt("age", 0);
        weightFinal = personData.getFloat("weight", 0);
        heightFinal = personData.getFloat("height", 0);
        gender = personData.getString("gender", "null");


        initTxtViews();
        maleOrFemale();
        dropDownActivity();
//        spinnerActivityLevel();

        bmr.setText(bmrGeneral+" Kcals");
    }
}