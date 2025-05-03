package com.example.sundarua.activity

import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
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

class StartQuizActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        var questionModelList: List<QuestionModel> = listOf()
        var time: String = ""
    }

    private lateinit var binding: ActivityStartQuizBinding
    private var currentQuestionIndex = 0
    private var selectedAnswer = ""
    private var score = 0

    private var mediaPlayer: MediaPlayer? = null // ⬅️ Tambahkan MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btn0.setOnClickListener(this@StartQuizActivity)
            btn1.setOnClickListener(this@StartQuizActivity)
            btn2.setOnClickListener(this@StartQuizActivity)
            btn3.setOnClickListener(this@StartQuizActivity)
            nextBtn.setOnClickListener(this@StartQuizActivity)
        }

        // ⬇️ Mulai putar musik saat kuis mulai
        mediaPlayer = MediaPlayer.create(this, R.raw.manukdadali)
        mediaPlayer?.isLooping = true
        mediaPlayer?.start()

        loadQuestions()
        startTimer()
    }

    private fun startTimer() {
        val totalTimeInMillis = time.toInt() * 60 * 1000L
        object : CountDownTimer(totalTimeInMillis, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                val seconds = millisUntilFinished / 1000
                val minutes = seconds / 60
                val remainingSeconds = seconds % 60
                binding.timerIndicatorTextview.text = String.format("%02d:%02d", minutes, remainingSeconds)
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

        binding.apply {
            questionIndicatorTextview.text = "Question ${currentQuestionIndex + 1}/${questionModelList.size}"
            questionProgressIndicator.progress =
                (currentQuestionIndex.toFloat() / questionModelList.size.toFloat() * 100).toInt()
            questionTextview.text = questionModelList[currentQuestionIndex].question
            btn0.text = questionModelList[currentQuestionIndex].options[0]
            btn1.text = questionModelList[currentQuestionIndex].options[1]
            btn2.text = questionModelList[currentQuestionIndex].options[2]
            btn3.text = questionModelList[currentQuestionIndex].options[3]
        }
    }

    override fun onClick(view: View?) {
        binding.apply {
            btn0.setBackgroundColor(getColor(R.color.gray))
            btn1.setBackgroundColor(getColor(R.color.gray))
            btn2.setBackgroundColor(getColor(R.color.gray))
            btn3.setBackgroundColor(getColor(R.color.gray))
        }

        val clickedBtn = view as Button
        if (clickedBtn.id == R.id.next_btn) {
            if (selectedAnswer.isEmpty()) {
                Toast.makeText(applicationContext, "Pilih heula soalna!", Toast.LENGTH_SHORT).show()
                return
            }

            if (selectedAnswer == questionModelList[currentQuestionIndex].correct) {
                score++
            }

            currentQuestionIndex++
            loadQuestions()
        } else {
            selectedAnswer = clickedBtn.text.toString()
            clickedBtn.setBackgroundColor(getColor(R.color.orange))
        }
    }

    private fun finishQuiz() {
        // ⬇️ Berhentikan musik saat kuis selesai
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null

        val totalQuestions = questionModelList.size
        val percentage = ((score.toFloat() / totalQuestions.toFloat()) * 100).toInt()

        val dialogBinding = ScoreDialogBinding.inflate(layoutInflater)
        dialogBinding.apply {
            scoreProgressIndicator.progress = percentage
            scoreProgressText.text = "$percentage%"

            if (percentage >= 60) {
                scoreTitle.text = "Ngiring bingah! hade pisan"
                scoreTitle.setTextColor(Color.BLACK)

                val currentCoin = getCoin()
                val currentLevel = getLevel()
                val newCoin = currentCoin + 100
                val newLevel = currentLevel + 1
                saveCoinAndLevel(newCoin, newLevel)
            } else {
                scoreTitle.text = "Hayu diajar deui sing rajin"
                scoreTitle.setTextColor(Color.BLACK)
            }

            scoreSubtitle.text = "$score ti $totalQuestions jawaban anu bener"

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

    private fun getCoin(): Int {
        val sharedPref = getSharedPreferences("game_data", MODE_PRIVATE)
        return sharedPref.getInt("coin", 0)
    }

    private fun getLevel(): Int {
        val sharedPref = getSharedPreferences("game_data", MODE_PRIVATE)
        return sharedPref.getInt("level", 1)
    }

    private fun saveCoinAndLevel(coin: Int, level: Int) {
        val sharedPref = getSharedPreferences("game_data", MODE_PRIVATE)
        sharedPref.edit()
            .putInt("coin", coin)
            .putInt("level", level)
            .apply()
    }

    override fun onDestroy() {
        super.onDestroy()
        // ⬇️ Pastikan musik berhenti kalau user keluar dari activity
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}