package com.vucko.cometchatdemo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.cometchat.pro.core.CometChat
import com.cometchat.pro.core.GroupsRequest
import com.cometchat.pro.exceptions.CometChatException
import com.cometchat.pro.models.Group


class MainActivity : AppCompatActivity() {

    private val CREATE_GROUP = 1

//    lateinit var createGroupButton: Button
    lateinit var groupsRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

//        createGroupButton = findViewById(R.id.createGroupButton)
        groupsRecyclerView = findViewById(R.id.groupsRecyclerView)
//        createGroupButton.setOnClickListener { openCreateGroupScreen() }
        refreshGroupList()
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            // Respond to the action bar's Up/Home button
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
