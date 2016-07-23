package com.ferran.geoquiz.Presenter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ferran.geoquiz.Model.Question;
import com.ferran.geoquiz.R;

public class QuizActivity extends AppCompatActivity {
    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mPreviousButton;
    private ImageButton mNextButton;
    private TextView mQuestionTestView;

    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true)};

    private int mCurrentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
        respond();
        updateQuestion();
    }

    // get reference of widget
    private void initialize() {
        mTrueButton = (Button) findViewById(R.id.true_button);
        mFalseButton = (Button) findViewById(R.id.false_button);
        mPreviousButton = (ImageButton) findViewById(R.id.previous_button);
        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mQuestionTestView = (TextView) findViewById(R.id.question_text_view);
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
        if (userPressedTrue == mQuestionBank[mCurrentIndex].ismAnswerTrue()) {
            Toast.makeText(QuizActivity.this, R.string.correct_toast, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(QuizActivity.this, R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
        }
    }
}
