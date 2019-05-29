package com.vucko.cometchatdemo

import android.content.Context
import android.preference.PreferenceManager
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.cometchat.pro.core.CometChat
import com.cometchat.pro.models.TextMessage
import kotlinx.android.synthetic.main.message_layout.view.*
import java.text.SimpleDateFormat
import java.util.*


class MessagesAdapter(var messages: MutableList<TextMessage?>, val context: Context) : RecyclerView.Adapter<MessageViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return MessageViewHolder(LayoutInflater.from(context).inflate(R.layout.message_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.messageTextView.text = messages[position]?.text
        holder.senderTextView.text = messages[position]?.sender?.name
        // x1000 due to this being seconds rather than milliseconds
        val timeMilliseconds = messages[position]?.sentAt?.times(1000)
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        holder.timeTextView.text = timeFormat.format(Date(timeMilliseconds!!))
        // Check if the sender is the current user
        val currentUserId = CometChat.getLoggedInUser()?.uid

        // If this is the current user's message, shift it to the right and paint it one color
        // If it's another user's message, shift left and use another color
        if(currentUserId!! == messages[position]?.sender?.uid){
            holder.container.layoutParams = FrameLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT, Gravity.RIGHT)
            holder.container.setCardBackgroundColor(context.resources.getColor(R.color.my_message_color))
        } else {
            holder.container.layoutParams = FrameLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT, Gravity.LEFT)
            holder.container.setCardBackgroundColor(context.resources.getColor(R.color.other_message_color))
        }
    }

    public fun addMessage(message: TextMessage?){
        messages.add(message)
        notifyDataSetChanged()
    }
}


class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val messageTextView: TextView = itemView.messageTextView
    val senderTextView: TextView = itemView.senderTextView
    val timeTextView: TextView = itemView.timeTextView
    val container: CardView = itemView.container
}