package com.vucko.cometchatdemo

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.cometchat.pro.constants.CometChatConstants.Params.UID
import com.cometchat.pro.core.CometChat
import com.cometchat.pro.exceptions.CometChatException
import com.cometchat.pro.models.User

class LoginActivity : AppCompatActivity() {

    lateinit var usernameEditText: EditText
    lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        findViewById<ImageButton>(R.id.backArrowImageButton).setOnClickListener { onBackPressed() }
        loginButton = findViewById(R.id.logInButton)
        loginButton.setOnClickListener { attemptLogin() }
        usernameEditText = findViewById(R.id.usernameEditText)
        usernameEditText.setOnEditorActionListener {
                _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_DONE){
                attemptLogin()
                true
            } else {
                false
            }
        }
    }

    private fun attemptLogin() {
        loggingInButtonState()
        val UID = usernameEditText.text.toString()
        CometChat.login(UID, GeneralConstants.API_KEY, object : CometChat.CallbackListener<User>() {
            override fun onSuccess(user: User?) {
                redirectToMainScreen()
            }

            override fun onError(p0: CometChatException?) {
                normalButtonState()
                Toast.makeText(this@LoginActivity, p0?.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun loggingInButtonState() {
        loginButton.text = getString(R.string.logging_in)
        loginButton.isEnabled = false
    }

    private fun normalButtonState() {
        loginButton.text = getString(R.string.log_in)
        loginButton.isEnabled = true
    }

    private fun redirectToMainScreen() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}

