package com.ferran.geoquiz.Presenter;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ferran.geoquiz.Model.Question;
import com.ferran.geoquiz.R;

public class QuizActivity extends AppCompatActivity {
    private static final String TAG = "QuizActivity";
    private static final String KEY_QUESTION_INDEX = "index";
    private static final String KEY_IS_CHEAT = "cheat";
    private static final int REQUEST_CODE_CHEAT = 0;

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mCheatButton;
    private ImageButton mPreviousButton;
    private ImageButton mNextButton;
    private TextView mQuestionTestView;
    private TextView mAPIVersionTestView;

    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true)};

    private boolean[] mCheatTable = new boolean[]{false, false, false, false, false};
    private int mCurrentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_main);
        // save savedInstanceState
        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_QUESTION_INDEX);
            mCheatTable = savedInstanceState.getBooleanArray(KEY_IS_CHEAT);
        }
        initialize();
        respond();
        updateQuestion();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart(Bundle) called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume(Bundle) called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause(Bundle) called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop(Bundle) called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy(Bundle) called");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState");
        outState.putInt(KEY_QUESTION_INDEX, mCurrentIndex);
        outState.putBooleanArray(KEY_IS_CHEAT, mCheatTable);
    }


    // get reference of widget
    private void initialize() {
        mTrueButton = (Button) findViewById(R.id.true_button);
        mFalseButton = (Button) findViewById(R.id.false_button);
        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mPreviousButton = (ImageButton) findViewById(R.id.previous_button);
        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mQuestionTestView = (TextView) findViewById(R.id.question_text_view);
        mAPIVersionTestView = (TextView) findViewById(R.id.api_version_text_view);
        CharSequence charSequence="API LEVEL "+Build.VERSION.SDK_INT;
        mAPIVersionTestView.setText(charSequence);
    }

    private void respond() {
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean answer = mQuestionBank[mCurrentIndex].ismAnswer();
                Intent i = CheatActivity.newIntent(QuizActivity.this, answer);
                startActivityForResult(i, REQUEST_CODE_CHEAT);
            }
        });
        mPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentIndex == 0) {
                    mCurrentIndex = mQuestionBank.length - 1;
                } else {
                    --mCurrentIndex;
                }
                updateQuestion();
            }
        });
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });
        mQuestionTestView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });
    }

    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getmTextResId();
        mQuestionTestView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue) {
        if (mCheatTable[mCurrentIndex]) {
            Toast.makeText(QuizActivity.this, R.string.judgment_toast, Toast.LENGTH_SHORT).show();
        } else {
            if (userPressedTrue == mQuestionBank[mCurrentIndex].ismAnswer()) {
                Toast.makeText(QuizActivity.this, R.string.correct_toast, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(QuizActivity.this, R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) {
                return;
            }
            mCheatTable[mCurrentIndex] = CheatActivity.wasAnswerShown(data);
        }
    }
}
