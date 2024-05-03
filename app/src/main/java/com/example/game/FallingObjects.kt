package com.example.game

import android.content.Context
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import kotlin.random.Random

class FallingObject(private val context: Context, val isMagicalItem: Boolean, private val gameLayout: FrameLayout, private val viewModel: GameViewModel) {

    val view: ImageView = ImageView(context).apply {
        val imageResId = if (isMagicalItem) R.drawable.magic_wand else R.drawable.rubber_duck
        setImageResource(imageResId)

        val layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        )
        this.layoutParams = layoutParams

        this.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        val objectWidth = this.measuredWidth
        val maxPositionX = gameLayout.width - objectWidth
        x = Random.nextFloat() * maxPositionX

        y = -100f
    }

    init {
        view.setOnClickListener {
            if (isMagicalItem) {
                gameLayout.removeView(view)  // Destroy the wand on click
                (context as GameView).incrementMagicalItems()
            }else {
                (context as GameView).gameOver("You don't need a duck")
            }
        }
    }

    fun fall() {
        view.animate()
            .y(gameLayout.height.toFloat())
            .setDuration(viewModel.fallingTime)
            .withEndAction {
                if (view.parent != null && (context as GameView).gameActive) {
                    gameLayout.removeView(view)
                    if (isMagicalItem) {
                        (context as GameView).gameOver("You missed a wand")
                    } else {
                        (context as GameView).incrementScore(5)
                    }
                }
            }.start()
    }

}
