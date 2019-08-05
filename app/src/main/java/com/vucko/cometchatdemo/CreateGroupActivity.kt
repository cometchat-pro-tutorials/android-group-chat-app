package com.vucko.cometchatdemo

import android.app.Activity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cometchat.pro.constants.CometChatConstants
import com.cometchat.pro.core.CometChat
import com.cometchat.pro.exceptions.CometChatException
import com.cometchat.pro.models.Group

class CreateGroupActivity : AppCompatActivity() {

    lateinit var progressBar: ProgressBar
    lateinit var saveButton: Button
    lateinit var groupNameEditText: EditText
    lateinit var groupDescriptionEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_group)

        progressBar = findViewById(R.id.progressBar)
        saveButton = findViewById(R.id.saveButton)
        groupNameEditText = findViewById(R.id.groupNameEditText)
        groupDescriptionEditText = findViewById(R.id.groupDescriptionEditText)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.create_new_group)

        saveButton.setOnClickListener({ createGroup() })
    }

    private fun createGroup() {
        savingButtonState()
        val groupName = groupNameEditText.text.toString()
        // Let's just assume group ID is going to be it's name for demo purposes
        // But without all characters that are not allowed
        val re = Regex("[^A-Za-z0-9]")
        val guid = re.replace(groupName, "")
        val description = groupDescriptionEditText.text.toString()
        val group = Group(guid, groupName, CometChatConstants.GROUP_TYPE_PUBLIC, "", "", description)

        CometChat.createGroup(group, object : CometChat.CallbackListener<Group>() {
            override fun onSuccess(p0: Group?) {
                // If the group is successfully created, return result "OK" so MainActivity can refresh the group list
                Toast.makeText(this@CreateGroupActivity, "Successfully created group", Toast.LENGTH_SHORT).show()
                setResult(Activity.RESULT_OK)
                finish()
            }

            override fun onError(p0: CometChatException?) {
                Toast.makeText(this@CreateGroupActivity, p0?.message, Toast.LENGTH_SHORT).show()
                normalButtonState()
            }
        })
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

    private fun savingButtonState() {
        saveButton.text = getString(R.string.saving_group)
        saveButton.isEnabled = false
    }

    private fun normalButtonState() {
        saveButton.text = getString(R.string.save_group)
        saveButton.isEnabled = true
    }

}
