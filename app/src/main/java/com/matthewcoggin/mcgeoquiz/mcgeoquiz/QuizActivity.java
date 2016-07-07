package com.matthewcoggin.mcgeoquiz.mcgeoquiz;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class QuizActivity extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mPrevButton;
    private Button mNextButton;
    private TextView mQuestionTextView;
    private TrueFalse[] mQuestions;
    private int mQuestionIndex;
    private enum mDirection {
        PREVIOUS, NEXT
    }
    private boolean mHasStarted;

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "mQuestionIndex";
    private static final String KEY_STARTED = "mHasStarted";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        mTrueButton = (Button)findViewById(R.id.true_button);
        mFalseButton = (Button)findViewById(R.id.false_button);
        mPrevButton = (Button)findViewById(R.id.prev_button);
        mNextButton = (Button)findViewById(R.id.next_button);
        mQuestionTextView = (TextView)findViewById(R.id.question_text);

        if (savedInstanceState != null) {
            mQuestionIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            mHasStarted = savedInstanceState.getBoolean(KEY_STARTED, false);
        } else {
            mQuestionIndex = 0;
            mHasStarted = false;
        }


        mQuestions = new TrueFalse[]{
                new TrueFalse(R.string.q1, R.string.cor1, R.string.inc1, true),
                new TrueFalse(R.string.q2, R.string.cor2, R.string.inc2, true),
                new TrueFalse(R.string.q3, R.string.cor3, R.string.inc3, true),
                new TrueFalse(R.string.q4, R.string.cor4, R.string.inc4, true),
                new TrueFalse(R.string.q5, R.string.cor5, R.string.inc5, true)
        };

        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mHasStarted) mQuestionTextView.setText(mQuestions[mQuestionIndex].getResponseIdFor(true));
            }
        });

        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mHasStarted) mQuestionTextView.setText(mQuestions[mQuestionIndex].getResponseIdFor(false));
            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHasStarted = true;
                updateQuestion(mDirection.NEXT);
            }
        });

        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mHasStarted) updateQuestion(mDirection.PREVIOUS);
            }
        });

        updateQuestion();
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mQuestionIndex);
        savedInstanceState.putBoolean(KEY_STARTED, mHasStarted);
    }

    @Override
    public void onStart() {
        super.onStart();

        Log.v(TAG, "onStart called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.v(TAG, "onPause called");
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.v(TAG, "onResume called");
    }

    @Override
    public void onStop() {
        super.onStop();

        Log.v(TAG, "onStop called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.v(TAG, "onDestroy called");
    }


    private void updateQuestion(mDirection direction) {

        if(direction == mDirection.NEXT) {
            // Go to next question or reset to the first question
            mQuestionIndex = mQuestionIndex+1 > mQuestions.length-1 ? 0 : mQuestionIndex+1;
        } else if(direction == mDirection.PREVIOUS) {
            // Go to previous question or wrap around to the last question
            mQuestionIndex = mQuestionIndex - 1 < 0 ? mQuestions.length - 1 : mQuestionIndex - 1;
        }
        updateQuestion();
    };

    private void updateQuestion() {
        mQuestionTextView.setText(mQuestions[mQuestionIndex].getQuestionId());
    }


}
