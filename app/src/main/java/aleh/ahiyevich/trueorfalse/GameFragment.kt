package aleh.ahiyevich.trueorfalse

import android.app.AlertDialog
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import java.util.*
import kotlin.collections.ArrayList


class GameFragment : Fragment() {
    private val data = ArrayList<DataBase>()

    private lateinit var health: TextView
    private lateinit var questionText: TextView
    private lateinit var commentText: TextView

    private var counterHealth = 3
    private var counterQuestions = 0

    private var timerRunning = false
    private var endTime = 0L
    private var timeLeftInMillis = 0L


    private var countDownTimer: CountDownTimer? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_game, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addData()

        var answer: Boolean
        val btnTrue = requireActivity().findViewById<Button>(R.id.btn_true)
        val btnFalse = requireActivity().findViewById<Button>(R.id.btn_false)
        health = requireActivity().findViewById(R.id.counter_health)
        questionText = requireActivity().findViewById<TextView>(R.id.question)
        questionText.text = data[0].question
        health.text = counterHealth.toString()

        btnTrue.setOnClickListener {
            answer = true
            checkButton(answer)
        }


        btnFalse.setOnClickListener {
            answer = false
            checkButton(answer)
        }
    }


    private fun checkButton(answer: Boolean) {
        if (answer == data[counterQuestions].answer) {
            Toast.makeText(requireContext(), "true", Toast.LENGTH_SHORT).show()
            checkSizeQuestions()
        } else {
            checkHealth()
        }
    }

    private fun checkSizeQuestions() {
        val view = View.inflate(requireContext(), R.layout.dialog_comment_layout, null)
        val dialog = addDialog(view)
        commentText = view.findViewById(R.id.dialog_comment)

        if (counterQuestions == data.size - 1) {
            dialog.show()
            view.findViewById<Button>(R.id.btn_next).visibility = View.GONE
            view.findViewById<Button>(R.id.btn_exit_comment).visibility = View.VISIBLE
            commentText.text = data[counterQuestions].comment
            view.findViewById<Button>(R.id.btn_exit_comment).setOnClickListener {
                dialog.dismiss()
            }
            Toast.makeText(requireContext(), "last question", Toast.LENGTH_SHORT).show()
        } else {
            dialog.show()
            commentText.text = data[counterQuestions].comment
            view.findViewById<Button>(R.id.btn_next).setOnClickListener {
                dialog.dismiss()
                counterQuestions++
                questionText.text = data[counterQuestions].question
            }
        }
    }

    private fun checkHealth() {
        val view = View.inflate(requireContext(), R.layout.dialog_timer_layout, null)
        val dialog = addDialog(view)

        if (counterHealth > 1) {
            Toast.makeText(requireContext(), "false, answer false - 1 life", Toast.LENGTH_SHORT)
                .show()
            counterHealth--
            health.text = counterHealth.toString()
        } else {
            //Если закончились жизни, то открывается диалог с тайером
            Toast.makeText(requireContext(), "open dialog", Toast.LENGTH_SHORT).show()
            counterHealth = 0
            health.text = counterHealth.toString()
            dialog.show()
            dialogDismissIfHealthZero(dialog, view)
            playAds(view)
        }
    }

    private fun dialogDismissIfHealthZero(dialog: AlertDialog, view: View) {
        val btnClose = view.findViewById<ImageView>(R.id.btn_close)
        val btnTrue = requireActivity().findViewById<Button>(R.id.btn_true)
        val btnFalse = requireActivity().findViewById<Button>(R.id.btn_false)

        btnTrue.setOnClickListener {
            dialog.show()
        }

        btnFalse.setOnClickListener {
            dialog.show()
        }

        btnClose.setOnClickListener {
            dialog.dismiss()
        }
    }

    private fun playAds(view: View){
        val btnTrue = requireActivity().findViewById<Button>(R.id.btn_true)
        val btnFalse = requireActivity().findViewById<Button>(R.id.btn_false)
        val playAds = view.findViewById<Button>(R.id.play_ads)

        playAds.setOnClickListener {
            Toast.makeText(requireContext(), "play ads 15 sec", Toast.LENGTH_SHORT).show()
            counterHealth++
            health.text = counterHealth.toString()
            if (counterHealth > 0){
                var answer: Boolean
                btnTrue.setOnClickListener {
                    answer = true
                    checkButton(answer)
                }

                btnFalse.setOnClickListener {
                    answer = false
                    checkButton(answer)
                }
            }
        }
    }


    private fun addDialog(view: View): AlertDialog {
        val builder = AlertDialog.Builder(this.context)
        builder.setView(view)
        val dialog = builder.create()
        dialog.setCancelable(false)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        return dialog
    }


    private fun addData() {
        data.add(
            DataBase(
                "Действительно ли в году 12 месяцев?",
                true,
                "Да, действительно, в году 12 месяцев Да, действительно, в году 12 месяцевДа, действительно, в году 12 месяцевДа, действительно, в году 12 месяцевДа, действительно, в году 12 месяцевДа, действительно, в году 12 месяцевДа, действительно, в году 12 месяцевДа, действительно, в году 12 месяцевДа, действительно, в году 12 месяцевДа, действительно, в году 12 месяцевДа, действительно, в году 12 месяцевДа, действительно, в году 12 месяцевДа, действительно, в году 12 месяцевДа, действительно, в году 12 месяцевДа, действительно, в году 12 месяцевДа, действительно, в году 12 месяцевДа, действительно, в году 12 месяцевДа, действительно, в году 12 месяцевДа, действительно, в году 12 месяцевДа, действительно, в году 12 месяцевДа, действительно, в году 12 месяцевДа, действительно, в году 12 месяцевДа, действительно, в году 12 месяцевДа, действительно, в году 12 месяцевДа, действительно, в году 12 месяцевДа, действительно, в году 12 месяцевДа, действительно, в году 12 месяцевДа, действительно, в году 12 месяцевДа, действительно, в году 12 месяцевДа, действительно, в году 12 месяцевДа, действительно, в году 12 месяцевДа, действительно, в году 12 месяцевДа, действительно, в году 12 месяцевДа, действительно, в году 12 месяцевДа, действительно, в году 12 месяцевДа, действительно, в году 12 месяцевДа, действительно, в году 12 месяцевДа, действительно, в году 12 месяцевДа, действительно, в году 12 месяцевДа, действительно, в году 12 месяцевДа, действительно, в году 12 месяцевДа, действительно, в году 12 месяцевДа, действительно, в году 12 месяцевДа, действительно, в году 12 месяцевДа, действительно, в году 12 месяцевДа, действительно, в году 12 месяцевДа, действительно, в году 12 месяцевДа, действительно, в году 12 месяцевДа, действительно, в году 12 месяцевДа, действительно, в году 12 месяцевДа, действительно, в году 12 месяцевДа, действительно, в году 12 месяцевДа, действительно, в году 12 месяцевДа, действительно, в году 12 месяцевДа, действительно, в году 12 месяцевДа, действительно, в году 12 месяцевДа, действителДа, действительно, в году 12 месяцевДа, действительно, в году 12 месяцевДа, действительно, в году 12 месяцевДа, действительно, в году 12 месяцевДа, действительно, в году 12 месяцевДа, действительно, в году 12 месяцевДа, действительно, в году 12 месяцевДа, действительно, в году 12 месяцевДа, действительно, в году 12 месяцевДа, действительно, в году 12 месяцевДа, действительно, в году 12 месяцевДа, действительно, в году 12 месяцевДа, действительно, в году 12 месяцевДа, действительно, в году 12 месяцевДа, действительно, в году 12 месяцевДа, действительно, в году 12 месяцевДа, действительно, в году 12 месяцевДа, действительно, в году 12 месяцевьно, в году 12 месяцевДа, действительно,vdsvsdvsdvв году 12 месяцевьно, в году 12 месяцевДа, действительно,vdsvsdvsdvв году 12 месяцевьно, в году 12 месяцевДа, действительно,vdsvsdvsdvв году 12 месяцевьно, в году 12 месяцевДа, действительно,vdsvsdvsdvв году 12 месяцевьно, в году 12 месяцевДа, действительно,vdsvsdvsdvв году 12 месяцевьно, в году 12 месяцевДа, действительно,vdsvsdvsdvв году 12 месяцевьно, в году 12 месяцевДа, действительно,vdsvsdvsdvв году 12 месяцевьно, в году 12 месяцевДа, действительно,vdsvsdvsdvв году 12 месяцевьно, в году 12 месяцевДа, действительно,vdsvsdvsdvв году 12 месяцевьно, в году 12 месяцевДа, действительно,vdsvsdvsdvв году 12 месяцевьно, в году 12 месяцевДа, действительно,vdsvsdvsdvв году 12 месяцевьно, в году 12 месяцевДа, действительно,vdsvsdvsdv"
            )
        )
        data.add(
            DataBase(
                "Действительно ли солнце желтое?",
                true,
                "Да, действительно, солнце желтое"
            )
        )

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

        data.add(
            DataBase(
                "Это 4?",
                true,
                "Да, действительно, это 4"
            )
        )
    }


}
