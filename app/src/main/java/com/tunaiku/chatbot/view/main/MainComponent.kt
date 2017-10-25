package com.tunaiku.chatbot.view.main

import com.tunaiku.chatbot.di.scope.ActivityScope
import dagger.Subcomponent

/**
 * Created by ibnumuzzakkir on 10/23/17.
 */
@ActivityScope
@Subcomponent(modules = arrayOf(MainModule::class))
interface MainComponent {
    fun inject(mainActivity: MainActivity)
}