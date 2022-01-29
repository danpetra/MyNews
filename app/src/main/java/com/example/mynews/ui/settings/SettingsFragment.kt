package com.example.mynews.ui.settings

import android.content.pm.ApplicationInfo
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceScreen
import com.example.mynews.R
import com.example.mynews.data.provider.USE_STATIC_API_PREFERENCE_KEY

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val staticApiPreference: Preference? = findPreference(USE_STATIC_API_PREFERENCE_KEY)
        val preferenceScreen: PreferenceScreen = preferenceScreen
        if (ApplicationInfo.FLAG_DEBUGGABLE==0) preferenceScreen.removePreference(staticApiPreference)
        else Log.i("debuggable","preferenceScreen")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.removeItem(R.id.action_search)
        menu.removeItem(R.id.action_share)
        menu.removeItem(R.id.action_bookmark)
    }


}