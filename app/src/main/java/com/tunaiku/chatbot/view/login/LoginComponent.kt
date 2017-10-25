package com.tunaiku.chatbot.view.login

import com.tunaiku.chatbot.di.scope.ActivityScope
import dagger.Subcomponent

/**
 * Created by ibnumuzzakkir on 10/23/17.
 */
@ActivityScope
@Subcomponent(modules = arrayOf(LoginModule::class))
interface LoginComponent {
    fun inject(loginActivity: LoginActivity)
}