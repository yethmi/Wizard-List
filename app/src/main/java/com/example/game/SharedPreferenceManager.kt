package com.example.game

import android.content.Context

class SharedPreferenceManager(context: Context) {

    private val highScoreSharedPref = context.getSharedPreferences("highScore", Context.MODE_PRIVATE)
    private val levelSharedPref = context.getSharedPreferences("level", Context.MODE_PRIVATE)

    var highScore: Int
        get() = highScoreSharedPref.getInt("HIGH_SCORE", 0)
        set(value) = highScoreSharedPref.edit().putInt("HIGH_SCORE", value).apply()

    var level: Int
        get() = levelSharedPref.getInt("LEVEL", 1)
        set(value) = levelSharedPref.edit().putInt("LEVEL", value).apply()
}
