package com.tunaiku.chatbot.helper

import android.content.Context
import com.google.firebase.database.*
import com.tunaiku.chatbot.vo.User
import com.google.firebase.database.DataSnapshot

import com.tunaiku.chatbot.vo.ChatMessage


/**
 * Firebase DB Helper
 * All transaction about firebase db will be recorded here
 * Created by ibnumuzzakkir on 10/22/17.
 */
class FirebaseDB(context:Context) {

    var mFirebaseDatabase :FirebaseDatabase
    lateinit var mDatabaseReference : DatabaseReference
    var mHelper : Helper

    internal val TABLE_USER = "users"
    internal val TABLE_CHAT = "chats"

    /**
     * Initialize Firebase Datababase
     * And new instance Helper
     * */

    init{
        mFirebaseDatabase = FirebaseDatabase.getInstance()
        mHelper = Helper()
    }

    /**
     * Inserting New User Data to Firebase DB
     * Set inserting by date
     * @Param User VO
     * */

    fun registerUser(user:User){
        mDatabaseReference = mFirebaseDatabase.getReference(TABLE_USER)
        mDatabaseReference.keepSynced(true)

        //Compltly User Registration set User ID and registration Date
        user.userId = mDatabaseReference.push().key
        user.registrationDate = System.currentTimeMillis()

        mDatabaseReference.child(mDatabaseReference.push().key).setValue(user)
    }

    /**
     * Selecting User data from Firebase DB
     * @Param String username and password
     * @Return UserModel
     * */

    fun selectUser(username: String, password : String, firebaseDBCallback: FirebaseDBCallback){
        var tmpUser : User? = null
        mDatabaseReference = mFirebaseDatabase.getReference(TABLE_USER)
        mDatabaseReference.keepSynced(true)
        val query = mDatabaseReference.orderByChild("username").equalTo(username)
        if(query != null){
            query.addListenerForSingleValueEvent(object:ValueEventListener{
                override fun onDataChange(dataSnapshot: DataSnapshot?) {
                    if(dataSnapshot!!.exists()){
                        for (user in dataSnapshot!!.getChildren()) {
                            val currPassword = user.child("password").getValue()
                            if(currPassword!!.equals(password)){
                                tmpUser = User()
                                tmpUser!!.token = user.child("token").value.toString()
                                tmpUser!!.password = user.child("password").value.toString()
                                tmpUser!!.userId = user.child("userId").value.toString()
                                tmpUser!!.username = user.child("username").value.toString()
                                firebaseDBCallback.onSuccess(false,tmpUser)
                            }else{
                                firebaseDBCallback.onSuccess(true,tmpUser)
                            }
                        }
                    }else{
                        firebaseDBCallback.onSuccess(true,tmpUser)
                    }

                }

                override fun onCancelled(databaseError: DatabaseError?) {
                    firebaseDBCallback.onError(databaseError!!.toException().toString())
                }

            })
        }else{
            firebaseDBCallback.onError("Query Error")
        }
    }

    /**
     * Inserting chat message
     * @Param Chat Message object
     * */

    fun addChatMessage(chatMessage: ChatMessage){
        mDatabaseReference = FirebaseDatabase.getInstance().getReference(TABLE_CHAT)
        mDatabaseReference.keepSynced(true)
        mDatabaseReference.child(mDatabaseReference.push().key).setValue(chatMessage)
    }


    /**
     * Getting User Chat Message By Instance Id*/

    fun selectChatMessage(instanceid:String, firebaseDBCallback: FirebaseDBCallback){
//        mDatabaseReference = mFirebaseDatabase.getReference(TABLE_CHAT).child("room-ibnumuzzakkir")
//        mDatabaseReference.keepSynced(true)
//        val query = mDatabaseReference.orderByChild("username").equalTo(username)
//        if(query != null){
//            query.addListenerForSingleValueEvent(object:ValueEventListener{
//                override fun onDataChange(dataSnapshot: DataSnapshot?) {
//                    if(dataSnapshot!!.exists()){
//                        for (user in dataSnapshot!!.getChildren()) {
//                            val currPassword = user.child("password").getValue()
//                            if(currPassword!!.equals(password)){
//                                firebaseDBCallback.onSuccess(false,tmpUser)
//                            }else{
//                                firebaseDBCallback.onSuccess(true,tmpUser)
//                            }
//                        }
//                    }else{
//                        firebaseDBCallback.onSuccess(true,tmpUser)
//                    }
//
//                }
//
//                override fun onCancelled(databaseError: DatabaseError?) {
//                    firebaseDBCallback.onError(databaseError!!.toException().toString())
//                }
//
//            })
//        }else{
//            firebaseDBCallback.onError("Query Error")
//        }
    }


    /**
     * Callback for firebaseDB transaction
     * */

    interface FirebaseDBCallback{
        fun <T>onSuccess(isEmptyData:Boolean, data: T)
        fun onError(error : String)
    }
}