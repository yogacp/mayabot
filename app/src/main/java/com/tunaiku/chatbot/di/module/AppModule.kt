package com.tunaiku.chatbot.di.module

import android.app.Application
import android.content.Context
import com.tunaiku.chatbot.storage.LoginSession
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by ibnumuzzakkir on 10/23/17.
 */
@Module
class AppModule(val application: Application){
    @Singleton
    @Provides
    internal fun provideContext(): Context {
        return application
    }

    @Singleton
    @Provides
    internal fun provideLoginSession(): LoginSession {
        return LoginSession()
    }
}