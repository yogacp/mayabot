package com.tunaiku.chatbot.helper

import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService

/**
 * Firebase Handling to get token id in the first time
 * Created by ibnumuzzakkir on 10/21/17.
 */
class MyFirebaseInstanceIDService : FirebaseInstanceIdService() {
    private val TAG = "MyFirebaseIIDService"

    override fun onTokenRefresh() {
        super.onTokenRefresh()
        val refreshedToken = FirebaseInstanceId.getInstance().token
        Log.d(TAG, "Refreshed token: " + refreshedToken!!)

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken)
    }

    private fun sendRegistrationToServer(token: String) {
        // TODO: Implement this method to send token to your app server.

    }

}