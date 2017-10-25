package com.tunaiku.chatbot.view.login

import com.tunaiku.chatbot.di.scope.ActivityScope
import dagger.Module
import dagger.Provides

/**
 * Created by ibnumuzzakkir on 10/23/17.
 */
@Module
class LoginModule(val loginActivity: LoginActivity){
    @Provides
    @ActivityScope
    fun provideLoginActivity(): LoginActivity {
        return loginActivity
    }

}