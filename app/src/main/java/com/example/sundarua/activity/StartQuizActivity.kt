package com.example.sundarua.activity

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.sundarua.R
import com.example.sundarua.databinding.ActivityStartQuizBinding
import com.example.sundarua.databinding.ScoreDialogBinding
import com.example.sundarua.model.QuestionModel
import com.example.sundarua.test.StartQuizHelper.calculateScorePercentage
import com.example.sundarua.test.StartQuizHelper.getCoin
import com.example.sundarua.test.StartQuizHelper.getLevel
import com.example.sundarua.test.StartQuizHelper.saveCoinAndLevel
import com.example.sundarua.test.StartQuizHelper.startBackgroundMusic
import com.example.sundarua.test.StartQuizHelper.stopBackgroundMusic
import com.example.sundarua.test.StartQuizHelper.updateCoinAndLevel

class StartQuizActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        var questionModelList: List<QuestionModel> = listOf()
        var time: String = ""
    }

    private lateinit var binding: ActivityStartQuizBinding
    private var currentQuestionIndex = 0
    private var selectedAnswer = ""
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
        startBackgroundMusic(this, R.raw.manukdadali)
        loadQuestions()
        startTimer()
    }

    private fun setupListeners() {
        binding.apply {
            btn0.setOnClickListener(this@StartQuizActivity)
            btn1.setOnClickListener(this@StartQuizActivity)
            btn2.setOnClickListener(this@StartQuizActivity)
            btn3.setOnClickListener(this@StartQuizActivity)
            nextBtn.setOnClickListener(this@StartQuizActivity)
        }
    }

    private fun startTimer() {
        val totalTimeInMillis = time.toInt() * 60 * 1000L
        object : CountDownTimer(totalTimeInMillis, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                val minutes = (millisUntilFinished / 1000) / 60
                val seconds = (millisUntilFinished / 1000) % 60
                binding.timerIndicatorTextview.text = String.format("%02d:%02d", minutes, seconds)
            }

            override fun onFinish() {
                finishQuiz()
            }
        }.start()
    }

    private fun loadQuestions() {
        selectedAnswer = ""

        if (currentQuestionIndex == questionModelList.size) {
            finishQuiz()
            return
        }

        val question = questionModelList[currentQuestionIndex]
        binding.apply {
            questionIndicatorTextview.text = "Question ${currentQuestionIndex + 1}/${questionModelList.size}"
            questionProgressIndicator.progress =
                ((currentQuestionIndex.toFloat() / questionModelList.size) * 100).toInt()
            questionTextview.text = question.question
            btn0.text = question.options[0]
            btn1.text = question.options[1]
            btn2.text = question.options[2]
            btn3.text = question.options[3]
        }
    }

    override fun onClick(view: View?) {
        if (view?.id == R.id.next_btn) {
            if (selectedAnswer.isEmpty()) {
                Toast.makeText(this, "Pilih heula soalna!", Toast.LENGTH_SHORT).show()
                return
            }

            if (selectedAnswer == questionModelList[currentQuestionIndex].correct) {
                score++
            }

            currentQuestionIndex++
            resetOptionColors()
            loadQuestions()
        } else if (view is Button) {
            resetOptionColors()
            selectedAnswer = view.text.toString()
            view.setBackgroundColor(getColor(R.color.green))
        }
    }

    private fun resetOptionColors() {
        binding.apply {
            val defaultColor = getColor(R.color.gray)
            btn0.setBackgroundColor(defaultColor)
            btn1.setBackgroundColor(defaultColor)
            btn2.setBackgroundColor(defaultColor)
            btn3.setBackgroundColor(defaultColor)
        }
    }

    private fun finishQuiz() {
        stopBackgroundMusic()

        val percentage = calculateScorePercentage(score, questionModelList.size)
        val dialogBinding = ScoreDialogBinding.inflate(layoutInflater)

        dialogBinding.apply {
            scoreProgressIndicator.progress = percentage
            scoreProgressText.text = "$percentage%"

            if (percentage >= 60) {
                scoreTitle.text = "Ngiring bingah! hade pisan"
                val (newCoin, newLevel) = updateCoinAndLevel(getCoin(this@StartQuizActivity), getLevel(this@StartQuizActivity), true)
                saveCoinAndLevel(this@StartQuizActivity, newCoin, newLevel)
            } else {
                scoreTitle.text = "Hayu diajar deui sing rajin"
            }

            scoreSubtitle.text = "$score ti ${questionModelList.size} jawaban anu bener"

            finishBtn.setOnClickListener {
                val intent = Intent(this@StartQuizActivity, QuizActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
                finish()
            }
        }

        AlertDialog.Builder(this)
            .setView(dialogBinding.root)
            .setCancelable(false)
            .show()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopBackgroundMusic()
    }
}