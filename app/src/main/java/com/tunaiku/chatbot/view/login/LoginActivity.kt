package com.tunaiku.chatbot.view.login

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.tunaiku.chatbot.ChatbotApp
import com.tunaiku.chatbot.helper.FirebaseDB
import com.tunaiku.chatbot.R
import com.tunaiku.chatbot.storage.LoginSession
import com.tunaiku.chatbot.view.main.MainActivity
import com.tunaiku.chatbot.view.register.RegisterActivity
import com.tunaiku.chatbot.vo.User
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

/**
 * Created by ibnumuzzakkir on 10/22/17.
 */
class LoginActivity : AppCompatActivity(){

    @Inject
    lateinit var mLoginSession:LoginSession

    var mFirebaseDB : FirebaseDB

    init {
        mFirebaseDB = FirebaseDB(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //Setup Activity Component
        setupActivityComponent()

        btnLogin.setOnClickListener {
            view ->
            mFirebaseDB.selectUser(edUsername.text.toString(), edPassword.text.toString(), object : FirebaseDB.FirebaseDBCallback{
                override fun <T> onSuccess(isEmptyData: Boolean, data: T) {
                    if(!isEmptyData){
                        var dataLogin = data as User
                        mLoginSession.setUserID(dataLogin.userId)
                        goToMainActivity()
                    }else{
                        Toast.makeText(applicationContext, "Akun tidak terdaftar", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onError(error: String) {
                    Toast.makeText(applicationContext, "Akun tidak terdaftar", Toast.LENGTH_SHORT).show()
                }
            })
        }

        edSignUp.setOnClickListener {
            startActivity(RegisterActivity.newIntent(this))
        }

    }

    internal fun goToMainActivity(){
        startActivity(MainActivity.newIntent(this))
    }

    fun setupActivityComponent() {
        ChatbotApp.appComponent
                .plus(LoginModule(this))
                .inject(this)
    }

}