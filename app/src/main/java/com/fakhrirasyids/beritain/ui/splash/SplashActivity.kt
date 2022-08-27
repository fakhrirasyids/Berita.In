package com.fakhrirasyids.beritain.ui.splash

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.fakhrirasyids.beritain.databinding.ActivitySplashBinding
import com.fakhrirasyids.beritain.ui.main.MainActivity
import com.fakhrirasyids.beritain.ui.main.ui.settings.SettingPreferences
import com.fakhrirasyids.beritain.ui.main.ui.settings.SettingViewModel
import com.fakhrirasyids.beritain.ui.main.ui.settings.SettingViewModelFactory
import com.fakhrirasyids.beritain.utils.Constants

class SplashActivity : AppCompatActivity() {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private lateinit var binding: ActivitySplashBinding
    private lateinit var settingViewModel: SettingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSetting()
        checkDarkMode()

        Handler(Looper.getMainLooper()).postDelayed({
            val iMain = Intent(this, MainActivity::class.java)
            iMain.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(iMain)
            finish()
        }, Constants.SPLASH_TIME_OUT)
    }

    private fun setSetting() {
        val pref = SettingPreferences.getInstance(dataStore)
        settingViewModel = ViewModelProvider(
            this,
            SettingViewModelFactory(pref)
        )[SettingViewModel::class.java]
    }

    private fun checkDarkMode() {
        settingViewModel.getThemeSettings().observe(this) { isDarkModeActive ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }
}