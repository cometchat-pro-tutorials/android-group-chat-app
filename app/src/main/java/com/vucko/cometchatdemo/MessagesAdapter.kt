package com.vucko.cometchatdemo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cometchat.pro.core.CometChat
import com.cometchat.pro.models.TextMessage
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.my_message_layout.view.*


class MessagesAdapter(var messages: MutableList<TextMessage?>, val context: Context) :
    RecyclerView.Adapter<MessageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        if (viewType == GeneralConstants.MY_MESSAGE) {
            return MessageViewHolder(LayoutInflater.from(context).inflate(R.layout.my_message_layout, parent, false))
        }
        return MessageViewHolder(LayoutInflater.from(context).inflate(R.layout.others_message_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun getItemViewType(position: Int): Int {
        if (isCurrentUserMessage(messages[position])) {
            return GeneralConstants.MY_MESSAGE
        }
        return GeneralConstants.OTHERS_MESSAGE
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.messageTextView.text = messages[position]?.text
        // Check if the sender is the current user
        Glide.with(context).load(messages[position]?.sender?.avatar).into(holder.avatarImageView)
    }

    private fun isCurrentUserMessage(message: TextMessage?): Boolean {
        val currentUserId = CometChat.getLoggedInUser()?.uid
        return currentUserId!! == message?.sender?.uid
    }

    fun addMessage(message: TextMessage?) {
        messages.add(message)
        notifyDataSetChanged()
    }
}


class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val messageTextView: TextView = itemView.messageTextView
    val avatarImageView: CircleImageView = itemView.avatarImageView
}