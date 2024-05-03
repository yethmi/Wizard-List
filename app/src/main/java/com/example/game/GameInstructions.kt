package com.example.game

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class GameInstructions : AppCompatActivity() {

    private val prefsManager by lazy { SharedPreferenceManager(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_instructions)

        val levelTextView = findViewById<TextView>(R.id.level)
        levelTextView.text = "Level " + prefsManager.level.toString()
    }

    fun play(view: View) {
        val intent = Intent(this, GameView::class.java)
        startActivity(intent)
    }

    fun exit(view: View) {
        finish()
    }
}
