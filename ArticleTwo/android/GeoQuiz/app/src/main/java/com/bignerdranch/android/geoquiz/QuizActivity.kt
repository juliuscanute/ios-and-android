package com.bignerdranch.android.geoquiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_quiz.*
import android.app.Activity





class QuizActivity : AppCompatActivity() {

    private val TAG = "QuizActivity"
    private val KEY_INDEX = "index"
    private val KEY_ANSWERS = "answers"
    private val REQUEST_CODE_CHEAT = 0

    private val questionBank: Array<Question> = arrayOf(
            Question(R.string.question_australia, true),
            Question(R.string.question_oceans, true),
            Question(R.string.question_mideast, false),
            Question(R.string.question_africa, false),
            Question(R.string.question_americas, true),
            Question(R.string.question_asia, true)

    )
    private var currentIndex: Int = 0
    private var answers = hashMapOf<Int, Boolean>()
    private var isCheated: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle) called")
        setContentView(R.layout.activity_quiz)

        if (savedInstanceState != null) {
            currentIndex = savedInstanceState.getInt(KEY_INDEX, 0)
            answers = savedInstanceState.getSerializable(KEY_ANSWERS) as HashMap<Int, Boolean>
        }

        true_button.setOnClickListener {
            checkAnswer(true)
        }

        false_button.setOnClickListener {
            checkAnswer(false)
        }

        next_button.setOnClickListener {
            showNextQuestion()
        }

        question_text_view.setOnClickListener {
            showNextQuestion()
        }

        previous_button.setOnClickListener {
            showPreviousQuestion()
        }

        cheat_button.setOnClickListener {
            val answerIsTrue = questionBank[currentIndex].isAnswerTrue
            val intent = CheatActivity.getIntent(this, answerIsTrue)
            startActivityForResult(intent, REQUEST_CODE_CHEAT)
        }

        updateQuestion()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG, "onSaveInstanceState")
        savedInstanceState.putInt(KEY_INDEX, currentIndex)
        savedInstanceState.putSerializable(KEY_ANSWERS, answers)
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    private fun showNextQuestion() {
        currentIndex = (currentIndex + 1) % questionBank.size
        updateQuestion()
    }

    private fun showPreviousQuestion() {
        currentIndex = if (currentIndex == 0) questionBank.size - 1 else currentIndex - 1
        updateQuestion()
    }

    private fun updateQuestion() {
        isCheated = false
        val question = questionBank[currentIndex].questionKey
        question_text_view.setText(question)
        val buttonsEnabled = !answers.containsKey(currentIndex)
        true_button.isEnabled = buttonsEnabled
        false_button.isEnabled = buttonsEnabled
    }

    private fun checkAnswer(userPressedTrue: Boolean) {
        val answerIsTrue = questionBank[currentIndex].isAnswerTrue

        var messageResId = 0

        if(isCheated){
            messageResId = R.string.judgment_toast
        } else {
            if (userPressedTrue == answerIsTrue) {
                messageResId = R.string.correct_toast
                answers[currentIndex] = true
            } else {
                messageResId = R.string.incorrect_toast
                answers[currentIndex] = false
            }
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
                .show()
        updateQuestion()
        checkScore()
    }

    private fun checkScore() {
        if (answers.size == questionBank.size) {
            var correctAnswers: Int = 0
            for ((_, isCorrect) in answers) {
                if (isCorrect) {
                    correctAnswers++
                }
            }
            val score = Math.round(correctAnswers.toDouble() / answers.size * 100).toInt()
            val scoreText = applicationContext.resources.getString(R.string.quiz_score, score)
            Toast.makeText(this, scoreText, Toast.LENGTH_LONG).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != Activity.RESULT_OK) {
            return
        }

        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) {
                return
            }
            isCheated = CheatActivity.wasAnswerShown(data)
        }
    }
}
