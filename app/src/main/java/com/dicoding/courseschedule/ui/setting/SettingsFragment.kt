package com.dicoding.courseschedule.ui.setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.notification.DailyReminder

class SettingsFragment : PreferenceFragmentCompat() {

    private lateinit var dailyReminder: DailyReminder

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        dailyReminder = DailyReminder()
        //TODO 10 : Update theme based on value in ListPreference
        val theme = findPreference<ListPreference>(getString(R.string.pref_key_dark))
        val darkModeValues = resources.getStringArray(R.array.dark_mode_value)
        theme?.setOnPreferenceChangeListener { preference, newValue ->
            if (newValue == darkModeValues[0]){
                updateTheme(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }
            else if (newValue == darkModeValues[1]){
                updateTheme(AppCompatDelegate.MODE_NIGHT_YES)
            }
            else{
                updateTheme(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
        //TODO 11 : Schedule and cancel notification in DailyReminder based on SwitchPreference
        val prefNotification = findPreference<SwitchPreference>(getString(R.string.pref_key_notify))
        prefNotification?.setOnPreferenceChangeListener { preference, newValue ->
            if (newValue == true){
                context?.let { dailyReminder.setDailyReminder(it) }

                }
            else{
                context?.let { dailyReminder.cancelAlarm(it) }
            }
            true
        }
    }

    private fun updateTheme(nightMode: Int): Boolean {
        AppCompatDelegate.setDefaultNightMode(nightMode)
        requireActivity().recreate()
        return true
    }

}