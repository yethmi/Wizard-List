package com.example.game

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class GameOver : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)

        val message = intent.getStringExtra("EXTRA_MESSAGE")
        val ifHighScore = intent.getStringExtra("EXTRA_HIGH_SCORE")
        val score = intent.getIntExtra("EXTRA_SCORE", 0)

        findViewById<TextView>(R.id.messageTextView).text = message
        findViewById<TextView>(R.id.yourHighScore).text = ifHighScore
        findViewById<TextView>(R.id.score).text = "Score: $score"
    }

    fun play(view: View) {
        val intent = Intent(this, GameView::class.java)
        startActivity(intent)
    }

    fun exit(view: View) {
        finish()
    }
}