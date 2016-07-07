package com.matthewcoggin.mcgeoquiz.mcgeoquiz;

/**
 * Created by MC on 6/14/16.
 */
public class TrueFalse extends java.lang.Object {
    private int mQuestion;
    private int mCorrectResponse;
    private int mIncorrectResponse;
    private boolean mTrueQuestion;

    public TrueFalse(int question, int correctResponse, int incorrectResponse, boolean trueQuestion) {
        mQuestion = question;
        mCorrectResponse = correctResponse;
        mIncorrectResponse = incorrectResponse;
        mTrueQuestion = trueQuestion;
    }

    public int getQuestionId() {
        return mQuestion;
    }

    public int getResponseIdFor(boolean isTrue) {
        return isTrue == mTrueQuestion ? mCorrectResponse : mIncorrectResponse;
    }

    public boolean isTrue() { return mTrueQuestion; }

}
