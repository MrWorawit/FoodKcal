package com.example.FoodKcal

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.FoodKcal.R.*
import com.example.FoodKcal.R.id.*
import com.google.android.material.textfield.TextInputLayout

class MainActivity2 : AppCompatActivity() {


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main2)

        val genderRadioGroup: RadioGroup = findViewById(R.id.genderRadioGroup)
        val maleRadioButton: RadioButton = findViewById(R.id.maleRadioButton)
        val femaleRadioButton: RadioButton = findViewById(R.id.femaleRadioButton)
        val weightLayout: TextInputLayout = findViewById(R.id.weightLayout)
        val heightLayout: TextInputLayout = findViewById(R.id.heightLayout)
        val ageLayout: TextInputLayout = findViewById(R.id.ageLayout)
        val calculateButton: Button = findViewById(R.id.calculateButton)
        val resultTextView: TextView = findViewById(R.id.resultTextView)
        calculateButton.setOnClickListener {
            val gender = when {
                maleRadioButton.isChecked -> "male"
                femaleRadioButton.isChecked -> "female"
                else -> ""
            }

            val weight = weightLayout.editText?.text.toString().toDoubleOrNull()
            val height = heightLayout.editText?.text.toString().toDoubleOrNull()
            val age = ageLayout.editText?.text.toString().toIntOrNull()

            if (gender.isEmpty() || weight == null || height == null || age == null) {
                resultTextView.text = "Please fill in all fields"
            } else {
                val bmr = calculateBMR(gender, weight, height, age)
                resultTextView.text = "BMR: $bmr calories/day"
            }
        }
    }

    private fun calculateBMR(gender: String, weight: Double, height: Double, age: Int): Double {
        return if (gender.toLowerCase() == "male") {
            88.362 + (13.397 * weight) + (4.799 * height) - (5.677 * age)
        } else {
            447.593 + (9.247 * weight) + (3.098 * height) - (4.330 * age)
        }
    }
}
