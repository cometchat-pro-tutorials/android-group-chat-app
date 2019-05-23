package com.vucko.cometchatdemo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.cometchat.pro.core.CometChat
import com.cometchat.pro.core.GroupsRequest
import com.cometchat.pro.exceptions.CometChatException
import com.cometchat.pro.models.Group
import com.cometchat.pro.models.User

class MainActivity : AppCompatActivity() {

    private val CREATE_GROUP = 1

    val TAG = "MainActivity"

    lateinit var createGroupButton: Button
    lateinit var groupsRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createGroupButton = findViewById(R.id.createGroupButton)
        groupsRecyclerView = findViewById(R.id.groupsRecyclerView)
        createGroupButton.setOnClickListener { openCreateGroupScreen() }
        initCometChat()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == CREATE_GROUP) {
            // This happens if we've just created another group
            // Then we should refresh groups
            // Could've used onResume but then it would always refresh
            refreshGroupList()
        }
    }

    private fun refreshGroupList() {
        // Get all the groups visible to this user
        // Since we're working with public groups only, all users should see all of them
        var groupRequest: GroupsRequest? = GroupsRequest.GroupsRequestBuilder().build()

        groupRequest?.fetchNext(object : CometChat.CallbackListener<List<Group>>() {
            override fun onSuccess(p0: List<Group>?) {
                updateUI(p0)
            }

            override fun onError(p0: CometChatException?) {
                Toast.makeText(this@MainActivity, p0?.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateUI(groups: List<Group>?) {
        val adapter = GroupsAdapter(groups, this)
        groupsRecyclerView.adapter = adapter
    }

    private fun openCreateGroupScreen() {
        val intent = Intent(this, CreateGroupActivity::class.java)
        startActivityForResult(intent, CREATE_GROUP)
    }

    private fun initCometChat() {
        // Initializes CometChat with the APP_ID from the dashboard

        CometChat.init(this, GeneralConstants.APP_ID, object : CometChat.CallbackListener<String>() {
            override fun onSuccess(p0: String?) {
                Log.d(TAG, "Initialization completed successfully")
                // Upon success, login the dummy user, this is only for demo purposes
                // In the real app, we would have a log in screen etc.
                loginUser()
            }

            override fun onError(p0: CometChatException?) {
                Log.d(TAG, "Initialization failed with exception: " + p0?.message)
            }
        })
    }

    private fun loginUser() {
        // This is where we can change which user gets logged in, again, in the real app or in some other version of this
        // we might have a login screen or something, for now it's just using pre-created CometChat users
        val UID = "SUPERHERO5"
        CometChat.login(UID, GeneralConstants.API_KEY, object : CometChat.CallbackListener<User>() {
            override fun onSuccess(user: User?) {
                refreshGroupList()
            }

            override fun onError(p0: CometChatException?) {
                Log.d(TAG, "Login failed with exception: " + p0?.message)
            }

        })
    }
}
