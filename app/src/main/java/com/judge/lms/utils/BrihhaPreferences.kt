package com.judge.lms.utils

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.judge.lms.R

/**
 * Created by Rohit on 19-09-2020.
 */
class BrihhaPreferences(private val mContext: Context) {


    var pref: SharedPreferences
    var editor: SharedPreferences.Editor

    /**
     * save data a/c to key name
     * @param keyName
     * @param value
     */
    fun saveStringObject(keyName: String?, value: String?) {
        editor.putString(keyName, value)
        editor.commit()
    }

    fun setIntData(keyName: String?, value: Int) {
        editor.putInt(keyName, value)
        editor.commit()
    }

    fun setLongData(keName: String?, value: Long) {
        editor.putLong(keName, value)
        editor.commit()
    }

    fun getLongData(key_name: String?): Long {
        return pref.getLong(key_name, 0)
    }

    fun getIntData(key_name: String?): Int {
        return pref.getInt(key_name, -1)
    }

    fun setBooleanData(keyName: String?, value: Boolean) {
        editor.putBoolean(keyName, value)
        editor.commit()
    }

    fun getBooleanData(keyName: String?): Boolean {
        return pref.getBoolean(keyName, false)
    }

    /**
     * get data a/c to
     * key name
     * @return
     */
    fun getData(key_name: String?): String? {
        return pref.getString(key_name, null)
    }

    /**
     * remove data
     * a/c to key name
     * @param keyName
     */
    fun deleteData(keyName: String?) {
        editor.remove(keyName)
        editor.commit()
    }

    /**
     * Clear preference
     */
    fun clearData() {
        editor.clear()
        editor.commit()
    }

    init {
        pref = mContext.getSharedPreferences(mContext.resources.getString(R.string.app_name), 0)
        editor = pref.edit()
        editor.apply()
    }
}
