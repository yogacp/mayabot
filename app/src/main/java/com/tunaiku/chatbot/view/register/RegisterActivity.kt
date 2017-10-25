package com.tunaiku.chatbot.view.register

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.tunaiku.chatbot.R
import android.content.Intent
import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.tunaiku.chatbot.helper.FirebaseDB
import com.tunaiku.chatbot.vo.User
import kotlinx.android.synthetic.main.activity_signup.*


/**
 * Created by ibnumuzzakkir on 10/22/17.
 */
class RegisterActivity:AppCompatActivity(){
    var mFirebaseDB : FirebaseDB

    init {
        mFirebaseDB = FirebaseDB(this)
    }

    companion object {
        fun newIntent(context: Context): Intent {
            val intent = Intent(context, RegisterActivity::class.java)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        btnSignUp.setOnClickListener {
            submitUserRegistration()
        }
    }

    internal fun submitUserRegistration(){
        var fullname = edSignUpFullName.text
        var email = edSignUpEmail.text
        var username = edSignUpUsername.text
        var password = edSignUpPassword.text
        var user = User()
        user.email = email.toString()
        user.fullname = fullname.toString()
        user.username = username.toString()
        user.password = password.toString()
        user.token = FirebaseInstanceId.getInstance().token
        mFirebaseDB.registerUser(user!!)
        Log.d(javaClass.name, "Berhasil Registrasi user baru")
    }

}