package com.tunaiku.chatbot.storage;

import android.content.Context;
import android.text.TextUtils;

import com.tunaiku.chatbot.ChatbotApp;

/**
 * Created by ibnumuzzakkir on 10/23/17.
 */

public class LoginSession {
    public boolean isLogin;

    private final String PREF_TOKEN = "token";
    private final String PREF_USER_ID = "user_id";

    private Pref pref;

    public LoginSession() {
        this.isLogin = false;
        pref = new Pref();
        isLogin = !TextUtils.isEmpty(getLoginToken());
    }

    private class Pref extends BaseSharedPreferences {
        private Pref() {
            check();
        }

        @Override
        protected Context _getApplicationContext() {
            return ChatbotApp.getInstance();
        }


        @Override
        public String _getUserProfileName(){
            return "user_session";
        }
    }

    public String getLoginToken() {
        return pref._getString(PREF_TOKEN, "");
    }

    public String getUserID(){ return pref._getString(PREF_USER_ID, "");}

    public void setUserID(String userID){
        pref._setString(PREF_USER_ID, userID);
        isLogin = true;
    }
}
