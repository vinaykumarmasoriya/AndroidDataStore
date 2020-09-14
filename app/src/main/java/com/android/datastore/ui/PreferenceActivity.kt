package com.android.datastore.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.asLiveData
import androidx.lifecycle.observe
import com.android.datastore.R
import com.android.datastore.preference.PreferenceManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PreferenceActivity : AppCompatActivity() {

    private lateinit var preferenceManager: PreferenceManager
    private val tag = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        preferenceManager = PreferenceManager(this)
        initView()
    }

    private fun initView() {
        saveUserData()
        observeUserData()
    }

    private fun saveUserData() {
        save_btn.setOnClickListener {
            val username = userName.editText?.text.toString()
            val userAge = age.editText?.text.toString()
            val userLocation = location.editText?.text.toString()
            val isAccountActive = accountStatus.isChecked
            Log.d(
                tag,
                "username ->$username, userAge ->$userAge, userLocation-> $userLocation,isAccountActive ->$isAccountActive"
            )
            userName.error =""
            age.error =""
            location.error = ""

            when {
                TextUtils.isEmpty(userName.editText?.text.toString()) -> {
                    userName.error = "User name required"
                    return@setOnClickListener
                }
                TextUtils.isEmpty(age.editText?.text.toString()) -> age.error = "Age required"
                TextUtils.isEmpty(location.editText?.text.toString()) -> location.error =
                    "Location required"
                else -> {
                    CoroutineScope(Dispatchers.IO).launch {
                        preferenceManager.storeUserData(
                            username,
                            userAge.toInt(),
                            userLocation,
                            isAccountActive
                        )
                    }
                    userName.error =""
                    clearInputField()
                }
            }
        }
    }

    private fun clearInputField() {
        userName.editText?.text?.clear()
        age.editText?.text?.clear()
        location.editText?.text?.clear()
        accountStatus.isChecked = false
    }

    private fun observeUserData() {
        preferenceManager.userNameFlow.asLiveData().observe(this) {
            userNameData.text = it
        }

        preferenceManager.userAgeFlow.asLiveData().observe(this) {
            ageData.text = it.toString()
        }

        preferenceManager.userLocationFlow.asLiveData().observe(this) {
            locationData.text = it
        }

        preferenceManager.userAccountStatusFlow.asLiveData().observe(this) {
            if (it) accountStatusData.text = "Active" else "InActive"
        }
    }

}