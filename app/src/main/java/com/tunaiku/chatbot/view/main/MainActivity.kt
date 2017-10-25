package com.tunaiku.chatbot.view.main

import ai.api.AIDataService
import ai.api.android.AIConfiguration
import ai.api.model.AIRequest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.tunaiku.chatbot.ChatbotApp
import com.tunaiku.chatbot.R
import com.tunaiku.chatbot.adapter.ChatAdapter
import com.tunaiku.chatbot.helper.FirebaseDB
import com.tunaiku.chatbot.storage.LoginSession
import com.tunaiku.chatbot.vo.ChatMessage
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : AppCompatActivity(){

    @Inject
    lateinit var mLoginSession :LoginSession

    lateinit var mAiService : AIDataService
    lateinit var mAiRequest : AIRequest
    lateinit var mDatabaseRef : DatabaseReference
    lateinit var mChatAdapter : ChatAdapter
    var mFirebaseDB : FirebaseDB = FirebaseDB(this)

    val linearLayoutManager = LinearLayoutManager(this)

    val ACCESS_CLIENT_ID_DIALOG_FLOW  = "10ac523029dd4036b76adcfd69c0a768"

    companion object {
        fun newIntent(context: Context): Intent {
            val intent = Intent(context, MainActivity::class.java)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupActivityComponent()

        //DialogFlow Setup
        val mAiConfig = AIConfiguration(ACCESS_CLIENT_ID_DIALOG_FLOW,
                ai.api.AIConfiguration.SupportedLanguages.English,
                AIConfiguration.RecognitionEngine.System)
        mAiService = AIDataService(mAiConfig)

        recyclerView.setHasFixedSize(true)
        linearLayoutManager.stackFromEnd = true
        recyclerView.layoutManager = linearLayoutManager

        addBtn.setOnClickListener { view ->
            val message = editText.text.toString().trim { it <= ' ' }
            val chatMessageUser = ChatMessage()
            chatMessageUser.msgText = message
            chatMessageUser.msgUser = "user"
            chatMessageUser.userId = mLoginSession.userID
            chatMessageUser.msgSubmittedAt = System.currentTimeMillis()
            mFirebaseDB.addChatMessage(chatMessageUser)
            if (message != "") {
                Flowable.fromCallable {
                    mAiRequest = AIRequest()
                    mAiRequest.setQuery(message)
                    mAiService.request(mAiRequest)
                }  .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({data ->
                            val result = data.getResult()
                            val reply = result.getFulfillment().getSpeech()
                            val chatMessageBot = ChatMessage()
                            chatMessageBot.msgText  = reply
                            chatMessageBot.msgUser = "bot"
                            chatMessageBot.userId = mLoginSession.userID
                            chatMessageBot.msgSubmittedAt = System.currentTimeMillis()
                            mFirebaseDB.addChatMessage(chatMessageBot)
                        },{ error ->
                            Log.e(javaClass.name, error.message)
                        })
            }else{

            }
            editText.setText("")
        }
        initializeChatAdapter()
    }

    /**
     * Initialize First Bot Chat
     * */

    internal fun initializeFirstChatBot(){
        var chatMessage = ChatMessage()
        chatMessage.msgText  = "Hi, Perkenalkan aku maya. apa yang maya bisa bantu untuk kamu ?"
        chatMessage.msgUser = "bot"
        mDatabaseRef.child("chat").push().setValue(chatMessage)
    }



    /**
     * Initialize Chat adapter
     * Setting up firebase chat adapter and set to recycleview
     * */

    internal fun initializeChatAdapter(){
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("chats")
        mChatAdapter = ChatAdapter(mDatabaseRef.orderByChild("userId").equalTo(mLoginSession.userID))
        Log.d(javaClass.name, mChatAdapter.itemCount.toString());
        mChatAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)

                val msgCount = mChatAdapter.getItemCount()
                val lastVisiblePosition = linearLayoutManager.findLastCompletelyVisibleItemPosition()

                if (lastVisiblePosition == -1 || positionStart >= msgCount - 1 && lastVisiblePosition == positionStart - 1) {
                    recyclerView.scrollToPosition(positionStart)

                }

            }
        })

        recyclerView.setAdapter(mChatAdapter)

    }

    fun setupActivityComponent() {
        ChatbotApp.appComponent
                .plus(MainModule(this))
                .inject(this)
    }
}
