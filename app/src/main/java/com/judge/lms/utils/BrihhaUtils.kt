package com.judge.lms.utils

import android.util.Patterns
import okhttp3.RequestBody
import java.util.regex.Pattern

/**
 * Created by Rohit on 17-09-2020.
 */
object BrihhaUtils {
    @JvmStatic
    fun emailValidation(emailId: String): Boolean? {
        val pattern: Pattern? = Patterns.EMAIL_ADDRESS
        return pattern?.matcher(emailId)?.matches()
    }

    fun getRequestBody(value: String?): RequestBody? {
        return RequestBody.create(okhttp3.MultipartBody.FORM, value)
    }

    fun getEducation(value: String?): String? {
        val map: HashMap<String, String> = HashMap()
        map.put("No formal education", "1")
        map.put("Primary education", "2")
        map.put("Secondary education or high school", "3")
        map.put("GED", "4")
        map.put("Vocational qualification", "5")
        map.put("Bachelor's degree", "6")
        map.put("Master's degree", "7")
        map.put("Doctorate or higher", "8")
        map.put("Other", "9")

        return map.get(value);

    }

}