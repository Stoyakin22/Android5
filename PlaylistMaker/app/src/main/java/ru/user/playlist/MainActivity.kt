package ru.user.playlist

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val layoutRes = getLayoutForTheme()
        super.onCreate(savedInstanceState)
        setContentView(layoutRes)

        setupUI()

        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                setContentView(getLayoutForTheme())
                setupUI()
            }
        }
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(receiver, IntentFilter("com.example.UPDATE_THEME"))

}
    private fun setupUI(){
        supportActionBar?.hide()
        val settingsButton = findViewById<Button>(R.id.button)
        val buttonClickListener : View.OnClickListener = object : View.OnClickListener{
            override fun onClick(v:View?){
                val settingsIntent = Intent(this@MainActivity, SettingsActivity::class.java)
                startActivity(settingsIntent)
            }
        }

        settingsButton.setOnClickListener(buttonClickListener)

        val mediaButton = findViewById<Button>(R.id.button2)
        mediaButton.setOnClickListener{
            val mediaIntent = Intent(this@MainActivity, MediaActivity::class.java)
            startActivity(mediaIntent)
        }

        val searchButton = findViewById<Button>(R.id.button3)
        searchButton.setOnClickListener{
            val searchIntent = Intent(this@MainActivity, SearchActivity::class.java)
            startActivity(searchIntent)
        }
    }


    private fun getLayoutForTheme(): Int {
        val isDarkMode = getSharedPreferences("AppPrefs", MODE_PRIVATE).getBoolean("DARK_MODE", false)
        return if (isDarkMode) R.layout.main_dark else R.layout.activity_main
    }

}