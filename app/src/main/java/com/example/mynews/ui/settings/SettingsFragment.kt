package com.example.mynews.ui.settings

import android.content.Context
import android.content.SharedPreferences
import android.content.pm.ApplicationInfo
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.preference.PreferenceScreen
import com.example.mynews.BuildConfig
import com.example.mynews.R
import com.example.mynews.data.provider.FIRST_START
import com.example.mynews.data.provider.LAST_SCREEN_TIME
import com.example.mynews.data.provider.TimerProviderImpl
import com.example.mynews.data.provider.TimerProviderImpl.timeToString
import com.example.mynews.data.provider.USE_STATIC_API_PREFERENCE_KEY

class SettingsFragment : PreferenceFragmentCompat() {

    private val preferences: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(context?.applicationContext)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val staticApiPreference: Preference? = findPreference(USE_STATIC_API_PREFERENCE_KEY)
        val introPreference: Preference? = findPreference(FIRST_START)
        val preferenceScreen: PreferenceScreen = preferenceScreen
        if (!BuildConfig.DEBUG) {
            staticApiPreference?.isVisible = false
            introPreference?.isVisible = false
        }
        else Log.i("debuggable","preferenceScreen")


        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onResume() {
        val timerProviderImpl =  TimerProviderImpl
        val screenTimePreference: Preference? = findPreference(LAST_SCREEN_TIME)
        screenTimePreference?.summary = timerProviderImpl?.getTime()?.timeToString()
        super.onResume()
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.removeItem(R.id.action_search)
        menu.removeItem(R.id.action_share)
        menu.removeItem(R.id.action_bookmark)
        menu.removeItem(R.id.sources_menu_item)
    }


}