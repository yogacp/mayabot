package com.tunaiku.chatbot

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.google.firebase.database.FirebaseDatabase
import com.tunaiku.chatbot.di.component.AppComponent
import com.tunaiku.chatbot.di.component.DaggerAppComponent
import com.tunaiku.chatbot.di.module.AppModule

/**
 * Created by ibnumuzzakkir on 10/20/17.
 */
class ChatbotApp :Application() {
    companion object {
        @JvmStatic lateinit var appComponent: AppComponent
        @JvmStatic lateinit var instance: ChatbotApp
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)

        //Initialize Multidex for prevent limit over 80k method
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = createComponent()
        instance = this
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
    }

    fun createComponent(): AppComponent {
        val appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
        return appComponent
    }

}