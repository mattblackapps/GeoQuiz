package com.matthewcoggin.mcgeoquiz.mcgeoquiz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by MC on 6/27/16.
 */
public class CheatActivity extends AppCompatActivity {

    TextView cheatAnswer;
    Button returnButton;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        returnButton = (Button) findViewById(R.id.return_button);
        cheatAnswer = (TextView) findViewById(R.id.cheat_answer);
        boolean isTrue = getIntent().getBooleanExtra(QuizActivity.KEY_IS_TRUE, false);
        if(isTrue) {
            cheatAnswer.setText(R.string.true_text);
        } else {
            cheatAnswer.setText(R.string.false_text);
        }

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
