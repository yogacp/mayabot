package com.tunaiku.chatbot.helper

import java.text.SimpleDateFormat
import java.util.*

/**
 * Helper for handling common requeirment function
 * Created by ibnumuzzakkir on 10/22/17.
 */
class Helper{
    /**
     * Getting Current date
     * @Return String
     * */

    fun gettingCurrentDate():String{
        val dateFormat = SimpleDateFormat("dd-MM-yyyy")
        val date = Date()
        return dateFormat.format(date)
    }
}