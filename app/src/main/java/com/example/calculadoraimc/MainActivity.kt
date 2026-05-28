package com.example.calculadoraimc

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        val editWeight = findViewById<EditText>(R.id.edit_weight)
        val seekbarHeight = findViewById<SeekBar>(R.id.seekbar_height)
        val buttonClear = findViewById<Button>(R.id.button_clear)
        val buttonCalculate = findViewById<Button>(R.id.button_calculate)
        val textHeightValue = findViewById<TextView>(R.id.text_height_value)
        val textResult = findViewById<TextView>(R.id.text_result)
        val mainLayout = findViewById<ConstraintLayout>(R.id.main)


        seekbarHeight.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seekBar: SeekBar?,
                progress: Int,
                fromUser: Boolean
            ) {
                textHeightValue.text = "$progress cm"
                textHeightValue.visibility = View.VISIBLE
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        buttonCalculate.setOnClickListener {
            try {
                val weight = editWeight.text.toString().toDouble()
                val height = seekbarHeight.progress.toDouble() / 100

                if (height > 0) {
                    val imc = weight / (height * height)

                    val classification = when {
                        imc < 18.5 -> "Abaixo do peso"
                        imc < 25 -> "Peso ideal"
                        imc < 30 -> "Sobrepeso"
                        imc < 35 -> "Obesidade grau I"
                        imc < 40 -> "Obesidade grau II"
                        else -> "Obesidade grau III"
                    }
                    val backgroundColor = when {

                        imc < 18.5 -> ContextCompat.getColor(this, R.color.yellow)
                        imc < 25 -> ContextCompat.getColor(this, R.color.green)
                        imc < 30 -> ContextCompat.getColor(this, R.color.orange)
                        imc < 35 -> ContextCompat.getColor(this, R.color.red)
                        imc < 40 -> ContextCompat.getColor(this, R.color.purple)

                        else -> ContextCompat.getColor(this, R.color.wine)
                    }

                    mainLayout.setBackgroundColor(backgroundColor)

                    textResult.text = "IMC: %.2f\n%s".format(imc, classification)
                    textResult.visibility = View.VISIBLE

                } else {
                    Toast.makeText(this, R.string.msg_valid_height, Toast.LENGTH_SHORT).show()
                }
            } catch (e: NumberFormatException) {
                Toast.makeText(this, R.string.msg_valid_weight, Toast.LENGTH_SHORT).show()
            }

        }

        buttonClear.setOnClickListener {
            editWeight.setText("")
            seekbarHeight.progress = 0
            textHeightValue.visibility = View.GONE
            textResult.visibility = View.GONE
            mainLayout.setBackgroundResource(R.drawable.background)
        }
    }
}



