package com.tunaiku.chatbot.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.tunaiku.chatbot.R

/**
 * Chat View Holder
 * Item Chat
 * Created by ibnumuzzakkir on 10/20/17.
 */
class ChatVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var leftText: TextView
    var rightText: TextView
    var layoutLeft: LinearLayout
    var layoutRight: LinearLayout
    var mayaTyping: LinearLayout
    var csImage: ImageView

    init {
        leftText = itemView.findViewById<TextView>(R.id.leftText) as TextView
        rightText = itemView.findViewById<TextView>(R.id.rightText) as TextView
        layoutLeft = itemView.findViewById<LinearLayout>(R.id.layoutLeft) as LinearLayout
        layoutRight = itemView.findViewById<LinearLayout>(R.id.layoutRight) as LinearLayout
        mayaTyping = itemView.findViewById<LinearLayout>(R.id.maya_typing) as LinearLayout
        csImage = itemView.findViewById<ImageView>(R.id.imageView3) as ImageView
    }
}