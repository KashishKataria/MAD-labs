package com.example.numberguessinggame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlin.random.Random
var randomInt = Random.nextInt(0,100)
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myButton: Button = findViewById(R.id.button)


        myButton.setOnClickListener {
            // Code to be executed when the button is clicked

            Validate()
        }
    }
    fun Validate(){

        val inputText : EditText = findViewById(R.id.Text)
        val editText = findViewById<TextView>(R.id.TextVeiw)
        val input= Integer.parseInt(inputText.text.toString())

        if (input > randomInt){
            editText.text="Too HIgh"

        }
        else if (input < randomInt)
            editText.text="Low"
        else{
            editText.text="Your Guess is Correct"
            randomInt = Random.nextInt()
        }

    }
}