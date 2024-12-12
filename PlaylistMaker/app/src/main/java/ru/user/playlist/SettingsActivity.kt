package ru.user.playlist

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class SettingsActivity : AppCompatActivity() {
    @SuppressLint("UseSwitchCompatOrMaterialCode", "WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        val layoutRes = getLayoutForTheme()
        super.onCreate(savedInstanceState)
        setContentView(layoutRes)

        val themeSwitch = findViewById<Switch>(R.id.themeSwitch)
        val sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE)
        val isDarkMode = sharedPreferences.getBoolean("DARK_MODE", false)

        themeSwitch.isChecked = isDarkMode

        themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            saveThemePreference(isChecked)
            recreate()
        }

        val intent = Intent("com.example.UPDATE_THEME")
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)

        setupUI()
    }

    @SuppressLint("WrongViewCast")
    private fun setupUI(){

        val backButton = findViewById<ImageButton>(R.id.backButton)
        backButton.setOnClickListener{
            finish()
        }

        val shareAppButton = findViewById<Button>(R.id.share)
        shareAppButton.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Группа ИС-211")
            startActivity(Intent.createChooser(shareIntent, "Поделиться приложением через"))
        }


        val contactSupportButton = findViewById<Button>(R.id.sendToSupport)
        contactSupportButton.setOnClickListener {
            val supportEmail = "Stoyakin.andrej.02@mail.ru"
            val subject = "Сообщение разработчикам Playlist Maker"
            val body = "Работает!"

            val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:$supportEmail")
                putExtra(Intent.EXTRA_TEXT, subject)
                putExtra(Intent.EXTRA_TEXT, body)
            }

            if (emailIntent.resolveActivity(packageManager) != null) {
                startActivity(emailIntent)
            } else {
                Toast.makeText(this, "Не найден почтовый клиент", Toast.LENGTH_SHORT).show()
            }
        }


        val userAgreementButton = findViewById<Button>(R.id.viewUserAgreement)
        userAgreementButton.setOnClickListener {
            val agreementUrl = Uri.parse("https://vivt.ru")
            val browserIntent = Intent(Intent.ACTION_VIEW, agreementUrl)
            startActivity(browserIntent)
        }

    }


    private fun getLayoutForTheme(): Int {
        val isDarkMode = getSharedPreferences("AppPrefs", MODE_PRIVATE).getBoolean("DARK_MODE", false)
        return if (isDarkMode) R.layout.settings_dark else R.layout.settings
    }

    private fun saveThemePreference(isDarkMode: Boolean) {
        val editor = getSharedPreferences("AppPrefs", MODE_PRIVATE).edit()
        editor.putBoolean("DARK_MODE", isDarkMode)
        editor.apply()
    }
}