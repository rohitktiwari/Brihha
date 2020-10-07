package com.judge.lms.activity

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.judge.lms.R
import com.judge.lms.model.RegisterRequest
import com.judge.lms.utils.BrihhaUtils.emailValidation
import com.judge.lms.utils.RetrofitClient
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Rohit on 18-09-2020.
 */


class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        btn_create_account.setOnClickListener {
            if (checkValidations()) {
                registerAction()
                //val registerRequest: RegisterRequest = RegisterRequest(et_firstName.text.toString(), et_lastName.text.toString())
                Toast.makeText(this@RegisterActivity, "Success", Toast.LENGTH_SHORT).show()
            }
        }

        et_username.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_NAVIGATE_NEXT) {
                //Perform Code
                et_dob_day.requestFocus()
                return@OnKeyListener true
            }
            false
        })
        et_dob_day.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_NAVIGATE_NEXT && event.action == KeyEvent.KEYCODE_NAVIGATE_NEXT) {
                //Perform Code
                et_dob_month.requestFocus()
                return@OnKeyListener true
            }
            false
        })
        et_dob_month.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_NAVIGATE_NEXT) {
                //Perform Code
                et_dob_year.requestFocus()
                return@OnKeyListener true
            }
            false
        })
        et_dob_year.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_NAVIGATE_NEXT) {
                //Perform Code
                selectExperienceLevel()
                getDOB()
                return@OnKeyListener true
            }
            false
        })

        et_education_level.setOnClickListener {
            selectEducationLevel()
        }

        et_registering_as.setOnClickListener {
            selectRole()
        }

        et_experience.setOnClickListener {
            selectExperienceLevel()
        }

        et_dob_day.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if (et_dob_day.text.toString().length == 2)
                    et_dob_month.requestFocus()
            }
        })

        et_dob_month.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if (et_dob_month.text.toString().length == 2)
                    et_dob_year.requestFocus()
            }
        })

        iv_calendar_right.setOnClickListener {
            val dpd = DatePickerDialog(this@RegisterActivity, dateSetListener,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH))
            dpd.datePicker.maxDate = Date().time
            dpd.show()
        }

        /*et_dob_year.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if(et_dob_year.text.toString().length==4)
                    selectExperienceLevel()
            }
        })*/

        gender_rg.setOnCheckedChangeListener { radioGroup, i ->
            val radioButton: RadioButton = findViewById(i)
            if (radioButton.text.equals("Male"))
                gender = "2"
            else if (radioButton.text.equals("Female"))
                gender = "1"
        }
    }

    val cal = Calendar.getInstance()
    val year = cal.get(Calendar.YEAR)
    val month = cal.get(Calendar.MONTH)
    val day = cal.get(Calendar.DAY_OF_MONTH)


    val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
        cal.set(Calendar.YEAR, year)
        cal.set(Calendar.MONTH, monthOfYear)
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

        val myFormat = "dd-MM-yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        et_dob_day.setText(dayOfMonth.toString())
        if (monthOfYear.toString().length == 1) {
            et_dob_month.setText("0" + monthOfYear.toString())
        } else
            et_dob_month.setText(monthOfYear.toString())
        et_dob_year.setText(year.toString())


    }

    private fun registerAction() {
        getDOB()
        val registerRequest = RegisterRequest(
                et_firstName.text.toString(),
                et_lastName.text.toString(),
                role, et_organization.text.toString(),
                et_email.text.toString(),
                et_confirm_email.text.toString(),
                et_phone.text.toString(),
                gender, et_username.text.toString(),
                dob, experience,
                et_skills.text.toString(),
                education, et_password.text.toString(),
                et_confirm_password.text.toString()
        )
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val register = RetrofitClient.apiCall.register(registerRequest)
                if (register.isSuccessful) {
                    Toast.makeText(this@RegisterActivity, "Register Successful", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this@RegisterActivity, register.code().toString() + " " + register.message(), Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@RegisterActivity, e.message, Toast.LENGTH_LONG).show()
            }
        }

    }


    private fun selectExperienceLevel() {
        val builderSingle: AlertDialog.Builder = AlertDialog.Builder(this@RegisterActivity)
        val experiences = resources.getStringArray(R.array.experience)
        with(builderSingle) {
            setItems(experiences) { dialog, which ->
                experience = Integer.parseInt(experiences[which])
                et_experience.setText(experiences[which])
            }
            show()
        }
    }

    lateinit var dob: String

    @SuppressLint("SimpleDateFormat")
    fun getDOB() {
        val day = et_dob_day.text.toString()
        val month = et_dob_month.text.toString()
        val year = et_dob_year.text.toString()
        if (day.isEmpty() || month.isEmpty() || year.isEmpty())
            return
        else
            dob = day + "-" + month + "-" + year

        val date = SimpleDateFormat("dd-mm-yyyy").parse(dob)

    }

    lateinit var gender: String
    var role: Int = 0
    var education: Int = 0
    var experience: Int = 0
    fun selectEducationLevel() {
        val builderSingle: AlertDialog.Builder = AlertDialog.Builder(this@RegisterActivity)
        val education_level = resources.getStringArray(R.array.education)
        with(builderSingle) {
            setItems(education_level) { dialog, which ->
                education = which + 1
                et_education_level.setText(education_level[which])
            }
            show()
        }
    }

    fun selectRole() {
        val builderSingle: AlertDialog.Builder = AlertDialog.Builder(this@RegisterActivity)
        val roles = resources.getStringArray(R.array.roles)
        with(builderSingle) {
            setItems(roles) { dialog, which ->
                if (roles[which].equals("Trainer"))
                    role = 3
                else if (roles[which].equals("Learner"))
                    role = 4
                et_registering_as.setText(roles[which])
            }
            show()
        }
    }


    fun loginAction(view: View?) {
        //if(checkValidations()) {
        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_left, R.anim.stay)
        //}
    }

    fun checkValidations(): Boolean {
        if (et_firstName!!.text.toString().isEmpty()) {
            et_firstName!!.requestFocus()
            et_firstName!!.error = "Field cannot be blank"
            return false
        } else if (et_lastName!!.text.toString().isEmpty()) {
            et_lastName!!.requestFocus()
            et_lastName!!.error = "Field cannot be blank"
            return false
        } else if (et_registering_as!!.text.toString().isEmpty()) {
            et_registering_as!!.requestFocus()
            et_registering_as!!.error = "Field cannot be blank"
            return false
        } else if (et_organization!!.text.toString().isEmpty()) {
            et_organization!!.requestFocus()
            et_organization!!.error = "Field cannot be blank"
            return false
        } else if (et_email!!.text.toString().isEmpty()) {
            et_email!!.requestFocus()
            et_email!!.error = "Field cannot be blank"
            return false
        } else if (et_confirm_email!!.text.toString().isEmpty()) {
            et_confirm_email!!.requestFocus()
            et_confirm_email!!.error = "Field cannot be blank"
            return false
        } else if (et_confirm_password!!.text.toString().isEmpty()) {
            et_confirm_password!!.requestFocus()
            et_confirm_password!!.error = "Field cannot be blank"
            return false
        } else if (et_password!!.text.toString().isEmpty()) {
            et_password!!.requestFocus()
            et_password!!.error = "Field cannot be blank"
            return false
        } else if (et_phone!!.text.toString().isEmpty()) {
            et_phone!!.requestFocus()
            et_phone!!.error = "Field cannot be blank"
            return false
        } else if (et_username!!.text.toString().isEmpty()) {
            et_username!!.requestFocus()
            et_username!!.error = "Field cannot be blank"
            return false
        } else if (et_experience!!.text.toString().isEmpty()) {
            et_experience!!.requestFocus()
            et_experience!!.error = "Field cannot be blank"
            return false
        } else if (et_education_level!!.text.toString().isEmpty()) {
            et_education_level!!.requestFocus()
            et_education_level!!.error = "Field cannot be blank"
            return false
        } else if (et_dob_day!!.text.toString().isEmpty()) {
            et_dob_day!!.requestFocus()
            return false
        } else if (et_dob_month!!.text.toString().isEmpty()) {
            et_dob_month!!.requestFocus()
            return false
        } else if (et_dob_year!!.text.toString().isEmpty()) {
            et_dob_year!!.requestFocus()
            return false
        } else if (!emailValidation(et_email!!.text.toString())!!) {
            et_email!!.requestFocus()
            et_email!!.error = "Invalid Email"
            return false
        } else if (!emailValidation(et_confirm_email!!.text.toString())!!) {
            et_confirm_email!!.requestFocus()
            et_confirm_email!!.error = "Invalid email"
            return false
        } else if (et_email!!.text.toString() != et_confirm_email!!.text.toString()) {
            et_email!!.requestFocus()
            et_email!!.error = "Email do not match"
            return false
        }
        return true
    }
}