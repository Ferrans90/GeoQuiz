package com.ferran.geoquiz.Model;

public class Question {
    private int mTextResId;
    private boolean mAnswer;

    public Question(int mTextResId, boolean mAnswer) {
        this.mTextResId = mTextResId;
        this.mAnswer = mAnswer;
    }

    public Question(Question question) {
        this.mTextResId = question.getmTextResId();
        this.mAnswer = question.ismAnswer();
    }

    public int getmTextResId() {
        return mTextResId;
    }

    public void setmTextResId(int mTextResId) {
        this.mTextResId = mTextResId;
    }

    public boolean ismAnswer() {
        return mAnswer;
    }

    public void setmAnswer(boolean mAnswer) {
        this.mAnswer = mAnswer;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
