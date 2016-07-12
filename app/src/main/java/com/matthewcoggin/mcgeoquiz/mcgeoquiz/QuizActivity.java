package com.matthewcoggin.mcgeoquiz.mcgeoquiz;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    public static final String KEY_IS_TRUE = "com.matthewcoggin.mcgeoquiz.isTrue";

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mPrevButton;
    private Button mNextButton;
    private ImageButton mCheatButton;
    private TextView mQuestionTextView;
    private TrueFalse[] mQuestions;
    private int mQuestionIndex;
    private enum mDirection {
        PREVIOUS, NEXT
    }
    private boolean mHasStarted;
    private boolean mIsResponding;

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "mQuestionIndex";
    private static final String KEY_STARTED = "mHasStarted";
    private static final String KEY_RESPONDING = "mIsResponding";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        mTrueButton = (Button)findViewById(R.id.true_button);
        mFalseButton = (Button)findViewById(R.id.false_button);
        mPrevButton = (Button)findViewById(R.id.prev_button);
        mNextButton = (Button)findViewById(R.id.next_button);
        mQuestionTextView = (TextView)findViewById(R.id.question_text);
        mCheatButton = (ImageButton)findViewById(R.id.cheat_button);

        if ( savedInstanceState == null ) {
            mQuestionIndex = 0;
            mHasStarted = false;
            mIsResponding = false;
        } else {
            mQuestionIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            mHasStarted = savedInstanceState.getBoolean(KEY_STARTED, false);
            mIsResponding = savedInstanceState.getBoolean(KEY_RESPONDING, false);
        }



        mQuestions = new TrueFalse[]{
                new TrueFalse(R.string.q1, R.string.cor1, R.string.inc1, true),
                new TrueFalse(R.string.q2, R.string.cor2, R.string.inc2, true),
                new TrueFalse(R.string.q3, R.string.cor3, R.string.inc3, false),
                new TrueFalse(R.string.q4, R.string.cor4, R.string.inc4, false),
                new TrueFalse(R.string.q5, R.string.cor5, R.string.inc5, true)
        };

        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mHasStarted) {
                    mQuestionTextView.setText("");
                    Toast t = Toast.makeText(QuizActivity.this,
                            mQuestions[mQuestionIndex].getResponseIdFor(true),
                            Toast.LENGTH_LONG);
                    t.setGravity(Gravity.CENTER, 0, 0);
                    t.show();
                    mIsResponding = true;
                }
            }
        });

        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mHasStarted) {
                    mQuestionTextView.setText("");
                    Toast t = Toast.makeText(QuizActivity.this,
                            mQuestions[mQuestionIndex].getResponseIdFor(true),
                            Toast.LENGTH_LONG);
                    t.setGravity(Gravity.CENTER, 0, 0);
                    t.show();
                    mIsResponding = true;
                }
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

        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mHasStarted) showCheatDialog();
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
        savedInstanceState.putBoolean(KEY_RESPONDING, mIsResponding);
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
    }

    private void updateQuestion() {
        if(mHasStarted) {
            mQuestionTextView.setText(mQuestions[mQuestionIndex].getQuestionId());
            mIsResponding = false;
        }
    }

    private void showCheatDialog() {
        String question = getResources().getString(R.string.warning_text);
        String confirmMsg = getResources().getString(R.string.show_answer_button);
        String denyMsg = getResources().getString(R.string.cancel_button);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setMessage(question)
                .setPositiveButton(confirmMsg,  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        cheat();
                    }
                })
                .setNegativeButton(denyMsg, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                })
                .show();

    }

    private void cheat() {
        Intent intent = new Intent(this, CheatActivity.class);
        intent.putExtra(KEY_IS_TRUE, mQuestions[mQuestionIndex].isTrue());
        startActivity(intent);
    }

}
