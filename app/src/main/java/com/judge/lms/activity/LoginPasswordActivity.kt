package com.judge.lms.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.judge.lms.R
import com.judge.lms.constants.AppConstants
import com.judge.lms.model.LoginRequest
import com.judge.lms.utils.BrihhaPreferences
import com.judge.lms.utils.RetrofitClient
import kotlinx.android.synthetic.main.activity_login.tv_welcome_desc
import kotlinx.android.synthetic.main.activity_login_password.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by Rohit on 19-09-2020.
 */
class LoginPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_password)
        val username = intent.getStringExtra("username")
        val domain = intent.getStringExtra("domain")
        tv_welcome_desc.setText(getString(R.string.enter_your_password_to_sign_in_with) + "\n " + username)

        btn_signin.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val loginRequest = LoginRequest(domain.toString(), username.toString(), et_password.text.toString())
                    val login = RetrofitClient.apiCall.login(loginRequest)
                    if (login.isSuccessful) {
                        val authToken: String? = login.body()?.auth_token
                        BrihhaPreferences(this@LoginPasswordActivity).saveStringObject(AppConstants.AUTH_TOKEN, authToken)
                        Toast.makeText(this@LoginPasswordActivity, "Login Success", Toast.LENGTH_LONG).show()
                    } else{
                        Toast.makeText(this@LoginPasswordActivity, login.code().toString()+ " "+login.message(), Toast.LENGTH_LONG).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this@LoginPasswordActivity, e.message, Toast.LENGTH_LONG).show()
                    e.stackTrace
                }
            }
        }
    }
}