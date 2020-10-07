package com.judge.lms.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.judge.lms.R
import kotlinx.android.synthetic.main.activity_login.*

/**
 * Created by Rohit on 18-09-2020.
 */

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btn_next.setOnClickListener {
            if (checkValidations()) {
                val intent = Intent(this@LoginActivity, LoginPasswordActivity::class.java)
                intent.putExtra("domain", et_domain.text.toString())
                intent.putExtra("username", et_user.text.toString())
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.stay)
            }
        }

    }

    fun registerAction(view: View?) {
        val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.stay)
    }

    fun checkValidations(): Boolean {
        if (et_domain!!.text.toString().isEmpty()) {
            et_domain!!.requestFocus()
            et_domain!!.error = "Please enter Domain."
            return false
        } else if (et_user!!.text.toString().isEmpty()) {
            et_user!!.requestFocus()
            et_user!!.error = "Please enter username"
            return false
        } /*else if (!emailValidation(et_user!!.text.toString())!!) {
            et_user!!.requestFocus()
            et_user!!.error = "Invalid Email"
            return false
        }*/
        return true
    }
}