package com.tunaiku.chatbot.view.main

import com.tunaiku.chatbot.di.scope.ActivityScope
import com.tunaiku.chatbot.view.login.LoginActivity
import dagger.Module
import dagger.Provides

/**
 * Created by ibnumuzzakkir on 10/23/17.
 */
@Module
class MainModule(val mainActivity: MainActivity){
    @Provides
    @ActivityScope
    fun provideMainActivity(): MainActivity {
        return mainActivity
    }
}