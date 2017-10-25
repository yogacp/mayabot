package com.tunaiku.chatbot.storage

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by ibnumuzzakkir on 10/23/17.
 */
abstract class BaseSharedPreferences{
    protected var m_settings: SharedPreferences? = null

    protected abstract fun _getApplicationContext(): Context


    fun check() {
        if (m_settings == null) {
            val context = _getApplicationContext()
            m_settings = context.getSharedPreferences(_getUserProfileName(), 0)
        }
    }

    open fun _getUserProfileName(): String {
        return ""
    }

    fun _unset(tag: String) {
        val editor = m_settings!!.edit()
        editor.remove(tag)
        editor.apply()
    }

    fun _getBoolean(tag: String, b: Boolean): Boolean {
        return m_settings!!.getBoolean(tag, b)
    }

    fun _setBoolean(tag: String, b: Boolean) {
        val editor = m_settings!!.edit()
        editor.putBoolean(tag, b)
        editor.apply()
    }

    fun _getFloat(tag: String, def: Float): Float {
        return m_settings!!.getFloat(tag, def)
    }

    fun _setFloat(tag: String, f: Float) {
        val editor = m_settings!!.edit()
        editor.putFloat(tag, f)
        editor.apply()
    }

    fun _getString(tag: String, defaultStr: String): String? {
        return m_settings!!.getString(tag, defaultStr)
    }

    fun _setString(tag: String, valueStr: String) {
        val editor = m_settings!!.edit()
        editor.putString(tag, valueStr)
        editor.apply()
    }

    fun _getInt(tag: String, defaultVal: Int): Int {
        return m_settings!!.getInt(tag, defaultVal)
    }


    fun _setInt(tag: String, data: Int) {
        val editor = m_settings!!.edit()
        editor.putInt(tag, data)
        editor.apply()
    }

    fun _getLong(tag: String, defaultVal: Long): Long {
        return m_settings!!.getLong(tag, defaultVal)
    }


    fun _setLong(tag: String, data: Long) {
        val editor = m_settings!!.edit()
        editor.putLong(tag, data)
        editor.apply()
    }

    fun _clear() {
        val editor = m_settings!!.edit()
        editor.clear()
        editor.apply()
    }

}