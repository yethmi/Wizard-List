package com.example.game

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import kotlin.random.Random

class GameView : AppCompatActivity() {
    private val viewModel: GameViewModel by viewModels()
    public var gameActive = true
    private lateinit var gameLayout: FrameLayout
    private lateinit var scoreTextView: TextView
    private lateinit var itemCountView: TextView
    private val handler = Handler(Looper.getMainLooper())
    private val prefsManager by lazy { SharedPreferenceManager(this) }
    private var maxItems = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_view)
        gameLayout = findViewById(R.id.gameLayout)
        scoreTextView = findViewById(R.id.scoreTextView)
        itemCountView = findViewById(R.id.itemCountView)

        // Set level and max items from shared preference
        viewModel.level = prefsManager.level
        maxItems = viewModel.level * 5

        setupGame()
        updateUI()
    }

    private fun setupGame() {
        handler.postDelayed(object : Runnable {
            override fun run() {
                val item = FallingObject(this@GameView, Random.nextBoolean(), gameLayout, viewModel)
                gameLayout.addView(item.view)
                item.fall()
                if (viewModel.itemsCount < maxItems) {
                    handler.postDelayed(this, viewModel.delayTime)
                }
            }
        }, 1000)
    }

    fun incrementMagicalItems() {
        viewModel.incrementScore(10)
        viewModel.incrementItemsCount()
        updateUI()
        if (viewModel.itemsCount >= maxItems) {
            winGame()
        }
    }

    fun incrementScore(points: Int) {
        viewModel.incrementScore(points)
        updateUI()
    }

    private fun updateUI() {
        runOnUiThread {
            scoreTextView.text = "Score: ${viewModel.score}"
            itemCountView.text = "Items: ${viewModel.itemsCount} / $maxItems"
        }
    }

    fun gameOver(reason: String) {
        gameActive = false
        handler.removeCallbacksAndMessages(null)
        val intent = Intent(this, GameOver::class.java).apply {
            putExtra("EXTRA_MESSAGE", reason)
            putExtra("EXTRA_SCORE", viewModel.score)
            if (viewModel.score > prefsManager.highScore) {
                prefsManager.highScore = viewModel.score
                putExtra("EXTRA_HIGH_SCORE", "New High Score")
            }
        }
        startActivity(intent)
        finish()
    }

    fun winGame() {
        gameActive = false
        handler.removeCallbacksAndMessages(null)
        prefsManager.level = viewModel.level + 1 // Increment level in shared preferences
        val intent = Intent(this, GameWon::class.java).apply {
            putExtra("EXTRA_MESSAGE", "You Won!")
            putExtra("EXTRA_SCORE", viewModel.score)
        }
        startActivity(intent)
    }
}
