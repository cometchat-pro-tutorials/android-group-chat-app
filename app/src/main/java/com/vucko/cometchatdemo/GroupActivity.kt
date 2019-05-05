package com.vucko.cometchatdemo

import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cometchat.pro.constants.CometChatConstants
import com.cometchat.pro.core.CometChat
import com.cometchat.pro.exceptions.CometChatException
import com.cometchat.pro.models.Group
import com.cometchat.pro.models.MediaMessage
import com.cometchat.pro.models.TextMessage

class GroupActivity : AppCompatActivity() {

    lateinit var messagesRecyclerView: RecyclerView
    lateinit var messageEditText: EditText
    lateinit var sendButton: ImageButton
    var group: Group? = null

    lateinit var messagesAdapter: MessagesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group)
        messagesRecyclerView = findViewById(R.id.messagesRecyclerView)
        messageEditText = findViewById(R.id.messageEditText)
        sendButton = findViewById(R.id.sendButton)

        sendButton.setOnClickListener {
            attemptSendMessage()
        }

        getGroupDetails()
        messagesAdapter = MessagesAdapter(ArrayList(), this)
        messagesRecyclerView.adapter = messagesAdapter
        messagesRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun getGroupDetails() {
        // Get the details of the group, such as name, members and other data that may be used in the app later
        // For now only the group name is used

        val groupId = intent.getStringExtra("group_id")
        CometChat.getGroup(groupId, object : CometChat.CallbackListener<Group>() {
            override fun onSuccess(p0: Group?) {
                group = p0
                updateUI()
            }

            override fun onError(p0: CometChatException?) {

            }
        })

    }

    private fun updateUI() {
        supportActionBar?.title = group?.name
    }

    private fun attemptSendMessage() {
        // Attempts to send the message to the group by current user
        val text = messageEditText.text.toString()
        if (!TextUtils.isEmpty(text)) {
            messageEditText.text.clear()
            val receiverID: String = group!!.guid
            val messageType: String = CometChatConstants.MESSAGE_TYPE_TEXT
            val receiverType: String = CometChatConstants.RECEIVER_TYPE_GROUP

            val textMessage = TextMessage(receiverID, text, messageType, receiverType)

            CometChat.sendMessage(textMessage, object : CometChat.CallbackListener<TextMessage>() {
                override fun onSuccess(p0: TextMessage?) {
                    addMessage(p0)
                }

                override fun onError(p0: CometChatException?) {

                }

            })
        }

    }

    private fun addMessage(message: TextMessage?) {
        messagesAdapter.addMessage(message)
        messagesRecyclerView.smoothScrollToPosition(messagesAdapter.itemCount - 1)
    }

    // Some random ID for the listener for now
    private val listenerID: String = "1234"

    override fun onResume() {
        super.onResume()
        // Add the listener to listen for incoming messages in this screen
        CometChat.addMessageListener(listenerID,object :CometChat.MessageListener(){

            override fun onTextMessageReceived(message: TextMessage?) {
                addMessage(message)
            }

            override fun onMediaMessageReceived(message: MediaMessage?) {

            }

        })
    }

    override fun onPause() {
        super.onPause()
        CometChat.removeMessageListener(listenerID)
    }
}
