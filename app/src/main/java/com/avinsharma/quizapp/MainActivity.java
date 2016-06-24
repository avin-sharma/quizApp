package com.avinsharma.quizapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private int[] type = {1, 1, 1, 1, 2, 2, 3};
    private String[] questions = {"What is the capital of New Zealand?", "Christiana is the former name of which European city?", "Dushanbe is the capital of which Central Asian republic?", "Michael Bloomberg is the mayor of which US city?","Multiples of 6","Grey is made up of?","World's most popular sports?"};
    private String[] answers = {"Wellington", "Oslo", "Tajikistan", "New York","2,3","Black,White","Football"};
    private String[][] options = {{"Sydney", "Wellington", "Auckland", "Christchurch"}, {"Oslo", "Bergen", "Gothenburg", "Stockholm"}, {"Kyrgyzstan", "Afghanistan", "Uzbekistan", "Tajikistan"}, {"Massachusetts", "New York", "Ohio", "Pennsylvania"},{"2","3","4","5"}, {"Black","White","Blue","Green"},{}};
    private int question_number = 0;
    private int total_score = 0;

    TextView textView;
    Button button;
    LinearLayout linearLayout;
    TextView score;
    EditText editText;
    CheckBox[] checkbox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.the_button);
        linearLayout = (LinearLayout) findViewById(R.id.options);
        textView = (TextView) findViewById(R.id.question);
        score = (TextView) findViewById(R.id.score);
    }


    public void onButtonClick(View view) {

        if (button.getText().toString().equals(getResources().getText(R.string.start))) {

            createRadioButtonQuestion();
        } else if (button.getText().toString().equals(getResources().getText(R.string.submit))) {

            switch (type[question_number]) {
                case 1:
                    checkRadioButtonAnswer();
                    break;
                case 2:
                    checkCheckBoxAnswer();
                    break;
                case 3:
                    checkEditTextAnswer();
                    break;

            }
        } else if (button.getText().toString().equals(getResources().getText(R.string.next))) {

            switch (type[question_number]) {
                case 1:
                    createRadioButtonQuestion();
                    break;
                case 2:
                    createCheckBoxQuestion();
                    break;
                case 3:
                    createEditTextQuestions();
                    break;
            }
        } else {
            restart();
        }
    }


    public void createRadioButtonQuestion() {
        linearLayout.removeAllViews();
        textView.setText(questions[question_number]);
        RadioGroup radioGroup = new RadioGroup(this);
        final RadioButton[] radioButtons = new RadioButton[4];
        radioGroup.setOrientation(RadioGroup.VERTICAL);
        for (int i = 0; i < 4; i++) {
            radioButtons[i] = new RadioButton(this);
            radioButtons[i].setText(options[question_number][i]);
            radioGroup.addView(radioButtons[i]);
            radioGroup.setId(R.id.group_id);
        }
        linearLayout.addView(radioGroup);
        button.setText(getResources().getText(R.string.submit));
        score.setVisibility(View.VISIBLE);
    }


    public void createCheckBoxQuestion(){
        linearLayout.removeAllViews();
        textView.setText(questions[question_number]);
        final CheckBox[] checkbox = new CheckBox[4];
        for (int i=0; i<4; i++){
            checkbox[i] = new CheckBox(this);
            checkbox[i].setText(options[question_number][i]);
            linearLayout.addView(checkbox[i]);
        }
        this.checkbox = checkbox;
        button.setText(getResources().getText(R.string.submit));
        score.setVisibility(View.VISIBLE);
    }


    public void createEditTextQuestions(){
        linearLayout.removeAllViews();
        textView.setText(questions[question_number]);
        EditText editText = new EditText(MainActivity.this);
        editText.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        editText.setHint("Enter Answer");
        linearLayout.addView(editText);
        this.editText = editText;
        button.setText(getResources().getText(R.string.submit));
        score.setVisibility(View.VISIBLE);
    }


    public void checkEditTextAnswer(){

        if (editText.getText().toString().equals(answers[question_number])){
            Toast.makeText(MainActivity.this, "Correct answer!", Toast.LENGTH_SHORT).show();
            total_score++;
            String scoreString = "Total score " + String.valueOf(total_score);
            score.setText(scoreString);
            button.setText(getResources().getText(R.string.next));
            question_number++;
            if (question_number == 7) {
                button.setText(getResources().getText(R.string.finish));
                Toast.makeText(MainActivity.this, scoreString, Toast.LENGTH_SHORT).show();
                total_score = 0;
            }
        }
        else if (TextUtils.isEmpty(editText.getText().toString())){
            Toast.makeText(MainActivity.this, "No answer given", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(MainActivity.this, "Wrong Answer!, The correct answer was " + answers[question_number], Toast.LENGTH_SHORT).show();
            button.setText(getResources().getText(R.string.next));
            question_number++;
            if (question_number == 7) {
                button.setText(getResources().getText(R.string.finish));
                String scoreString = "Total score " + String.valueOf(total_score);
                Toast.makeText(MainActivity.this, scoreString, Toast.LENGTH_SHORT).show();
                total_score = 0;
            }
        }
    }


    public void checkCheckBoxAnswer() {
        boolean nothingTicked = true;
        List<String> ticked = new ArrayList<>();
        List<String> correct = Arrays.asList(answers[question_number].split(","));
        for (int i=0; i<4; i++){
            if(checkbox[i].isChecked()){
                ticked.add(checkbox[i].getText().toString());
                nothingTicked = false;
            }
        }
        Collections.sort(ticked, Collator.getInstance());
        Collections.sort(correct, Collator.getInstance());
        if (ticked.equals(correct)){
            Toast.makeText(MainActivity.this, "Correct answer!", Toast.LENGTH_SHORT).show();
            total_score++;
            String scoreString = "Total score " + String.valueOf(total_score);
            score.setText(scoreString);
            button.setText(getResources().getText(R.string.next));
            question_number++;
            if (question_number == 7) {
                button.setText(getResources().getText(R.string.finish));
                Toast.makeText(MainActivity.this, scoreString, Toast.LENGTH_SHORT).show();
                total_score = 0;
            }
        }
        else if (nothingTicked == true){
            Toast.makeText(MainActivity.this, "No options selected", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(MainActivity.this, "Wrong Answer!, The correct answer was " + answers[question_number], Toast.LENGTH_SHORT).show();
            button.setText(getResources().getText(R.string.next));
            question_number++;
            if (question_number == 7) {
                button.setText(getResources().getText(R.string.finish));
                String scoreString = "Total score " + String.valueOf(total_score);
                Toast.makeText(MainActivity.this, scoreString, Toast.LENGTH_SHORT).show();
                total_score = 0;
            }
        }
    }


    public void checkRadioButtonAnswer() {
        RadioGroup rg = (RadioGroup) findViewById(R.id.group_id);
        if (rg.getCheckedRadioButtonId() == -1) {
            Toast.makeText(MainActivity.this, "Select an option", Toast.LENGTH_SHORT).show();
        } else {
            RadioButton rb = (RadioButton) findViewById(rg.getCheckedRadioButtonId());
            if (rb.getText().toString().equals(answers[question_number])) {
                Toast.makeText(MainActivity.this, "Correct answer", Toast.LENGTH_SHORT).show();
                total_score++;
                String scoreString = "Total score " + String.valueOf(total_score);
                score.setText(scoreString);
                button.setText(getResources().getText(R.string.next));
                question_number++;
                if (question_number == 7) {
                    button.setText(getResources().getText(R.string.finish));
                    Toast.makeText(MainActivity.this, scoreString, Toast.LENGTH_SHORT).show();
                    total_score = 0;
                }
            } else {
                Toast.makeText(MainActivity.this, "Wrong Answer!, The correct answer was " + answers[question_number], Toast.LENGTH_SHORT).show();
                button.setText(getResources().getText(R.string.next));
                question_number++;
                if (question_number == 7) {
                    button.setText(getResources().getText(R.string.finish));
                    String scoreString = "Total score " + String.valueOf(total_score);
                    Toast.makeText(MainActivity.this, scoreString, Toast.LENGTH_SHORT).show();
                    total_score = 0;
                }
            }

        }
    }


    public void restart() {
        linearLayout.removeAllViews();
        question_number = 0;
        button.setText(getResources().getText(R.string.start));
        textView.setText(getResources().getText(R.string.welcome));
        String scoreString = "Total score " + String.valueOf(total_score);
        score.setText(scoreString);
    }
}
