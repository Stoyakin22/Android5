package ru.user.playlist

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity


class SearchActivity : AppCompatActivity() {

    private var searchText: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        val layoutRes = getLayoutForTheme()
        super.onCreate(savedInstanceState)
        setContentView(layoutRes)

        val searchInput = findViewById<EditText>(R.id.searchInput)
        val clearButton = findViewById<ImageButton>(R.id.clearButton)

        searchInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchText = s.toString()
                if (s.isNullOrEmpty()) {
                    clearButton.visibility = View.INVISIBLE
                } else {
                    clearButton.visibility = View.VISIBLE
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        clearButton.setOnClickListener {
            searchInput.text.clear()
            hideKeyboard(searchInput)
        }

        val backButton = findViewById<ImageButton>(R.id.backButton)
        backButton.setOnClickListener{
            finish()
        }
    }

    private fun getLayoutForTheme(): Int {
        val isDarkMode = getSharedPreferences("AppPrefs", MODE_PRIVATE).getBoolean("DARK_MODE", false)
        return if (isDarkMode) R.layout.search_dark else R.layout.activity_search
    }

    private fun hideKeyboard(view: View) {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("SEARCH_TEXT", searchText)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val restoredText = savedInstanceState.getString("SEARCH_TEXT", "")
        findViewById<EditText>(R.id.searchInput).setText(restoredText)
    }
}