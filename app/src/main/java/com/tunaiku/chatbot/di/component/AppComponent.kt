package com.tunaiku.chatbot.di.component

import com.tunaiku.chatbot.di.module.AppModule
import com.tunaiku.chatbot.view.login.LoginComponent
import com.tunaiku.chatbot.view.login.LoginModule
import com.tunaiku.chatbot.view.main.MainComponent
import com.tunaiku.chatbot.view.main.MainModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by ibnumuzzakkir on 10/23/17.
 */
@Singleton
@Component(
        modules = arrayOf(
                AppModule::class)
)
interface AppComponent{
    fun plus(loginModule: LoginModule): LoginComponent
    fun plus(mainModule: MainModule): MainComponent
}