package com.vucko.cometchatdemo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cometchat.pro.constants.CometChatConstants.Params.UID
import com.cometchat.pro.core.CometChat
import com.cometchat.pro.exceptions.CometChatException
import com.cometchat.pro.models.User

class LoginActivity : AppCompatActivity() {

    lateinit var usernameEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        findViewById<ImageButton>(R.id.backArrowImageButton).setOnClickListener { onBackPressed() }
        findViewById<Button>(R.id.logInButton).setOnClickListener { attemptLogin() }
        usernameEditText = findViewById(R.id.usernameEditText)
    }

    private fun attemptLogin() {
        val UID = usernameEditText.text.toString()
        CometChat.login(UID, GeneralConstants.API_KEY, object : CometChat.CallbackListener<User>() {
            override fun onSuccess(user: User?) {
                redirectToMainScreen()
            }

            override fun onError(p0: CometChatException?) {
                Toast.makeText(this@LoginActivity, p0?.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun redirectToMainScreen() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}

