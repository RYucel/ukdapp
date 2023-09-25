package com.example.kksfukd

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kksfukd.R
import com.example.kksfukd.databinding.ActivityMainBinding
import java.text.NumberFormat
import kotlin.math.pow
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.calculateButton.setOnClickListener { calculateRating() }
    }

    private fun calculateRating() {
        val stringInTextField = binding.oppukd.text.toString()
        val opukd = stringInTextField.toIntOrNull()
        if (opukd == null || opukd !in 1200..2500) {
            binding.ratingResult.text = ""
            return
        }

        val string2InTextField = binding.ratingUser.text.toString()
        val myukd = string2InTextField.toIntOrNull()
        if (myukd == null || myukd !in 1200..2500) {
            binding.ratingResult.text = ""
            return
        }

        // Use a when expression to declare and assign a variable K based on the range of myRating
        val k = when (myukd) {
            in 1200..1299 -> 30 // If myRating is between 1200 and 1299, set K to 30
            in 1300..1599 -> 25 // If myRating is between 1300 and 1599, set K to 25
            in 1600..1999 -> 20 // If myRating is between 1600 and 1999, set K to 20
            in 2000..2500 -> 15 // If myRating is between 2000 and 2500, set K to 15
            else -> 0 // If myRating is not in any of the above ranges, set K to 0
        }

        val gameResult = when (binding.gameOptions.checkedRadioButtonId) {
            R.id.option_win -> 1.0
            R.id.option_draw -> 0.5
            else -> 0.0
        }

        // The expected score for the player based on the ratings difference
        val expectedScore = 1.0 / (1.0 + 10.0.pow((opukd - myukd) / 400.0))

        // The new rating for the player based on the actual score and the expected score
        var newRating = myukd + (k * (gameResult - expectedScore)).toInt()

        if (binding.roundUpSwitch.isChecked) {
            newRating = ceil(newRating.toDouble()).toInt()
        }

        // Format the new rating as an integer
        val formattedRating = NumberFormat.getIntegerInstance().format(newRating)

        // Display the new rating in the text view
        binding.ratingResult.text = getString(R.string.rating_amount, formattedRating)
    }
}
