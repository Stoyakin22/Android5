package ru.user.playlist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MediaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val layoutRes = getLayoutForTheme()
        super.onCreate(savedInstanceState)
        setContentView(layoutRes)

    }
    private fun getLayoutForTheme(): Int {
        val isDarkMode = getSharedPreferences("AppPrefs", MODE_PRIVATE).getBoolean("DARK_MODE", false)
        return if (isDarkMode) R.layout.media_dark else R.layout.activity_media
    }
}