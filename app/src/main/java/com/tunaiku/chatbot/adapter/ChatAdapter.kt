package com.tunaiku.chatbot.adapter

import android.os.CountDownTimer
import android.view.View
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.Query
import com.tunaiku.chatbot.R
import com.tunaiku.chatbot.vo.ChatMessage



/**
 * Chat Adapter
 * Extends FirebaseRecycleAdapter
 * Created by ibnumuzzakkir on 10/23/17.
 */
class ChatAdapter(query: Query) : FirebaseRecyclerAdapter<ChatMessage, ChatVH>(ChatMessage::class.java, R.layout.item_chat, ChatVH::class.java,query){
    override fun populateViewHolder(viewHolder: ChatVH?, model: ChatMessage?, position: Int) {
        if (model!!.msgUser.equals("user")) {
            viewHolder!!.rightText.setText(model.msgText)
            viewHolder!!.layoutRight.visibility = View.VISIBLE
            viewHolder!!.layoutLeft.visibility = View.GONE
            viewHolder!!.csImage.visibility = View.GONE
            viewHolder!!.mayaTyping.visibility = View.GONE
        } else {
            viewHolder!!.leftText.setText(model.msgText)
            viewHolder!!.layoutRight.visibility = View.GONE
            viewHolder!!.layoutLeft.visibility = View.GONE
            viewHolder!!.csImage.visibility = View.GONE
            viewHolder!!.mayaTyping.visibility = View.VISIBLE

            // Make maya is typing for 1.5 seconds
            if(position == itemCount - 1) {
                object : CountDownTimer(1500, 1500) {

                    override fun onTick(millisUntilFinished: Long) {}

                    override fun onFinish() {
                        viewHolder!!.layoutRight.visibility = View.GONE
                        viewHolder!!.layoutLeft.visibility = View.VISIBLE
                        viewHolder!!.csImage.visibility = View.VISIBLE
                        viewHolder!!.mayaTyping.visibility = View.GONE
                    }

                }.start()
            } else {
                viewHolder!!.layoutRight.visibility = View.GONE
                viewHolder!!.layoutLeft.visibility = View.VISIBLE
                viewHolder!!.csImage.visibility = View.VISIBLE
                viewHolder!!.mayaTyping.visibility = View.GONE
            }
        }
    }
}