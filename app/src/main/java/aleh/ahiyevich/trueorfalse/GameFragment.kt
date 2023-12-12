package aleh.ahiyevich.trueorfalse

import android.app.AlertDialog
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import java.util.*


class GameFragment : Fragment() {
    private val data = ArrayList<DataBase>()

    private lateinit var health: TextView
    private lateinit var questionText: TextView
    private lateinit var commentText: TextView
    private lateinit var timerView: TextView
    private lateinit var btnTrue: Button
    private lateinit var btnFalse: Button
    private lateinit var btnNext: Button
    private lateinit var btnCloseComment: Button
    private var answer: Boolean = false


    private var counterHealth = 3
    private var counterQuestions = 0

    private var timerRunning = false
    private var timer: CountDownTimer? = null
    private var timeToFinish = 10800000L
    private var endTimeToClose = 0L


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_game, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addData()

        btnTrue = requireActivity().findViewById(R.id.btn_true)
        btnFalse = requireActivity().findViewById(R.id.btn_false)
        health = requireActivity().findViewById(R.id.counter_health)
        questionText = requireActivity().findViewById(R.id.question)
        questionText.text = data[0].question
        health.text = counterHealth.toString()


        btnTrue.setOnClickListener {
            answer = true
            checkAnswer(answer)
        }

        btnFalse.setOnClickListener {
            answer = false
            checkAnswer(answer)
        }
    }


    private fun checkAnswer(answer: Boolean) {
        if (counterHealth > 0) {
            if (answer == data[counterQuestions].answer) {
                checkSizeQuestions()
            } else {
                counterHealth--
                health.text = counterHealth.toString()
                Toast.makeText(requireContext(), "Health = $counterHealth", Toast.LENGTH_SHORT)
                    .show()
                if (counterHealth == 0) {
                    counterHealth = 0
                    health.text = counterHealth.toString()
                    openDialogTimer()
                }
            }
        } else {
            counterHealth = 0
            health.text = counterHealth.toString()
            openDialogTimer()
        }
    }


    private fun checkSizeQuestions() {

        if (counterQuestions != data.size - 1) {
            openDialogComment()
            commentText.text = data[counterQuestions].comment
        } else {
            openDialogComment()
            btnNext.visibility = View.GONE
            btnCloseComment.visibility = View.VISIBLE
            commentText.text = data[counterQuestions].comment
            Toast.makeText(requireContext(), "last question", Toast.LENGTH_SHORT).show()
        }
    }


    private fun openDialogTimer() {
        val view = View.inflate(requireContext(), R.layout.dialog_timer_layout, null)
        val builder = AlertDialog.Builder(this.context)
        builder.setView(view)
        val dialog = builder.create()
        dialog.setCancelable(false)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        timer(view)
        dialog.show()


        view.findViewById<ImageView>(R.id.btn_close).setOnClickListener {
            dialog.dismiss()
        }

        view.findViewById<Button>(R.id.play_ads).setOnClickListener {
            Toast.makeText(requireContext(), "play ADS 15 sec", Toast.LENGTH_SHORT).show()
            counterHealth++
            health.text = counterHealth.toString()
        }
    }

    private fun openDialogComment() {
        val view = View.inflate(requireContext(), R.layout.dialog_comment_layout, null)
        val builder = AlertDialog.Builder(this.context)
        builder.setView(view)
        val dialog = builder.create()
        dialog.setCancelable(false)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()
        commentText = view.findViewById(R.id.dialog_comment)
        btnNext = view.findViewById(R.id.btn_next)

        btnNext.setOnClickListener {
            counterQuestions++
            questionText.text = data[counterQuestions].question
            dialog.dismiss()
        }

        btnCloseComment = view.findViewById(R.id.btn_exit_comment)

        btnCloseComment.setOnClickListener {
            dialog.dismiss()
        }
    }

    private fun timer(view: View) {
        endTimeToClose = System.currentTimeMillis() + timeToFinish

        timer = object : CountDownTimer(timeToFinish, 1000) {
            override fun onTick(millisToFinish: Long) {
                timeToFinish = millisToFinish
                updateTimer(view)
            }

            override fun onFinish() {
                timerRunning = false
                Toast.makeText(requireContext(), "Timer Finished", Toast.LENGTH_SHORT).show()
            }

        }.start()
        timerRunning = true
    }


    private fun updateTimer(view: View){
        timerView = view.findViewById(R.id.timer) // Новое добавил по таймеру

        val seconds = (timeToFinish / 1000) % 60
        val minutes = (timeToFinish / 1000) / 60 / 3
        val hours = ((timeToFinish / 1000) / 60 ) / 60

        val formattedTime =
            String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds)

        timerView.text = formattedTime
    }

    private fun addData() {

        data.add(
            DataBase(
                "Это 1?",
                true,
                "Да, действительно, это 1"
            )
        )

        data.add(
            DataBase(
                "Это 2?",
                true,
                "Да, действительно, это 2"
            )
        )

        data.add(
            DataBase(
                "Это 3?",
                true,
                "Да, действительно, это 3"
            )
        )
    }

    override fun onStop() {
        super.onStop()
        // Сохраняю данные о жизнях и позиции прошедшего вопроса
        val sharedPreferences =
            requireActivity().getSharedPreferences("prefs", AppCompatActivity.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putInt("countHealth", counterHealth)
        editor.putInt("counterQuestion", counterQuestions)

        editor.putLong("timeToFinish",timeToFinish)
        editor.putLong("endTimeToClose",endTimeToClose)
        editor.putBoolean("timerRunning",timerRunning)

        editor.apply()

        if (timer != null){
            timer?.cancel()
        }
    }

    override fun onStart() {
        super.onStart()
        // Воспроизвожу данные о жизнях и позиции, где закончил играть игрок
        val sharedPreferences =
            requireActivity().getSharedPreferences("prefs", AppCompatActivity.MODE_PRIVATE)

        counterHealth = sharedPreferences.getInt("countHealth", 3)
        health.text = counterHealth.toString()
        counterQuestions = sharedPreferences.getInt("counterQuestion", 0)
        questionText.text = data[counterQuestions].question
        timerRunning = sharedPreferences.getBoolean("timerRunning", false)

        if (timerRunning){
            endTimeToClose = sharedPreferences.getLong("endTimeToClose", 0)
            timeToFinish = endTimeToClose - System.currentTimeMillis()
        } else {
            Toast.makeText(requireContext(), "Timer not started", Toast.LENGTH_SHORT).show()
        }

    }

}