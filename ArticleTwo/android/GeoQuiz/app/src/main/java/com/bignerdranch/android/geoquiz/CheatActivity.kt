package com.bignerdranch.android.geoquiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_cheat.*

class CheatActivity : AppCompatActivity() {

    private var cheater: Boolean = false

    companion object {
        const val EXTRA_ANSWER = "answer"
        const val EXTRA_CHEATED = "cheated"

        @JvmStatic
        fun getIntent(context: Context, answer: Boolean): Intent {
            val intent = Intent(context, CheatActivity::class.java)
            intent.putExtra(EXTRA_ANSWER, answer)
            return intent
        }

        @JvmStatic
        fun wasAnswerShown(result: Intent): Boolean {
            return result.getBooleanExtra(EXTRA_CHEATED, false)
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)
        val answer = intent.getBooleanExtra(EXTRA_ANSWER, false)
        showAnswer.setOnClickListener {
            if (answer)
                answerLabel.text = getString(R.string.true_button)
            else
                answerLabel.text = getString(R.string.false_button)
            setAnswerShownResult()
        }
    }

    private fun setAnswerShownResult() {
        val data = Intent().apply {
            putExtra(EXTRA_CHEATED, true)
        }
        setResult(Activity.RESULT_OK, data)
    }
}