package com.example.game

import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    private var baseFallingTime: Long = 4000
    private var baseDelayTime: Long = 800
    var score: Int = 0
    var itemsCount: Int = 0
    var level: Int = 1

    val fallingTime: Long
        get() = (baseFallingTime - (level * 100 + itemsCount * 100 )).coerceAtLeast(1000)

    val delayTime: Long
        get() = (baseDelayTime - ( level * 100 + itemsCount * 100 )).coerceAtLeast(500)

    fun incrementScore(points: Int) {
        score += points
    }

     fun incrementItemsCount() {
        itemsCount++
    }
}
