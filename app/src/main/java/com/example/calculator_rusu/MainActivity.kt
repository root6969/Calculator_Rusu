package com.example.calculator_rusu


import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.widget.RadioGroup
import androidx.core.content.ContextCompat

// Change this to your actual package name
class MainActivity : AppCompatActivity() {
    private var tvDisplay: TextView? = null
    private var firstValue = Double.NaN
    private var secondValue = 0.0
    private var currentAction = 0.toChar()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvDisplay = findViewById<TextView>(R.id.tvDisplay)
        setNumberButtonListeners()
        setOperationButtonListeners()
        setupGenderRadioButtons()
    }

    private fun setNumberButtonListeners() {
        val numberIds = intArrayOf(
            R.id.btnZero, R.id.btnOne, R.id.btnTwo, R.id.btnThree, R.id.btnFour,
            R.id.btnFive, R.id.btnSix, R.id.btnSeven, R.id.btnEight, R.id.btnNine
        )
        val numberButtonClickListener = View.OnClickListener { v ->
            val button = v as Button
            val currentText = tvDisplay?.text.toString()

            if (currentText == "0") {
                tvDisplay?.text = button.text
            } else {
                tvDisplay?.append(button.text)
            }
        }
        for (id in numberIds) {
            findViewById<Button>(id).setOnClickListener(numberButtonClickListener)
        }
        findViewById<Button>(R.id.btnDecimal).setOnClickListener {
            val currentText = tvDisplay?.text.toString()
            if (!currentText.contains(".")) {
                if (currentText.isEmpty() || currentText == "0") {
                    tvDisplay?.text = "0."
                } else {
                    tvDisplay?.append(".")
                }
            }
        }
    }

    private fun setupGenderRadioButtons() {
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroupGender)
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radioFemale -> tvDisplay?.setTextColor(ContextCompat.getColor(this, R.color.colorFemale))
                R.id.radioMale -> tvDisplay?.setTextColor(ContextCompat.getColor(this, R.color.colorMale))
            }
        }
    }



    private fun setOperationButtonListeners() {
        val operationIds = intArrayOf(
            R.id.btnAdd, R.id.btnSubtract, R.id.btnMultiply, R.id.btnDivide
        )
        val operationButtonClickListener =
            View.OnClickListener { v ->
                val button = v as Button
                if (!java.lang.Double.isNaN(firstValue)) {
                    secondValue = tvDisplay!!.text.toString().toDouble()
                    tvDisplay!!.text = ""
                    calculateResult()
                    firstValue = Double.NaN
                } else {
                    try {
                        firstValue = tvDisplay!!.text.toString().toDouble()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                currentAction = button.text[0]
                tvDisplay!!.text = ""
            }
        for (id in operationIds) {
            findViewById<View>(id).setOnClickListener(operationButtonClickListener)
        }
        findViewById<View>(R.id.btnEquals).setOnClickListener {
            if (!java.lang.Double.isNaN(firstValue)) {
                secondValue = tvDisplay!!.text.toString().toDouble()
                tvDisplay!!.text = ""
                calculateResult()
                firstValue = Double.NaN
            }
        }
        findViewById<View>(R.id.btnClear).setOnClickListener {
            if (tvDisplay!!.text.length > 0) {
                val currentText = tvDisplay!!.text
                tvDisplay!!.text = currentText.subSequence(0, currentText.length - 1)
            } else {
                firstValue = Double.NaN
                secondValue = Double.NaN
                tvDisplay!!.text = ""
            }
        }
    }

    private fun calculateResult() {
        var result = 0.0
        when (currentAction) {
            '+' -> result = firstValue + secondValue
            '-' -> result = firstValue - secondValue
            '*' -> result = firstValue * secondValue
            '/' -> result = firstValue / secondValue
        }
        tvDisplay!!.text = result.toString()
    }
}