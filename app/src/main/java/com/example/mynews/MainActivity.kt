package com.example.mynews

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.*
import androidx.work.*
import com.example.mynews.data.provider.TimerProviderImpl
import com.example.mynews.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit
import com.example.mynews.data.work.NOTIFICATION_TAG
import com.example.mynews.data.work.NotificationWorker
import com.example.mynews.data.work.NotificationWorker.Companion.NOTIFICATION_ID
import java.util.*
import android.content.SharedPreferences

import android.content.Intent
import androidx.preference.PreferenceManager
import com.example.mynews.ui.intro.CustomOnboarder


class MainActivity: AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var timerProviderImpl: TimerProviderImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        timerProviderImpl = TimerProviderImpl
        timerProviderImpl.create(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_top, R.id.nav_everything, R.id.nav_bookmarks, R.id.settingsFragment
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val FIRST_START = "FIRST_START"

        val getPrefs: SharedPreferences = PreferenceManager
            .getDefaultSharedPreferences(baseContext)

        val isFirstStart = getPrefs.getBoolean(FIRST_START, true)

        if (isFirstStart) {
            val i = Intent(this@MainActivity, CustomOnboarder::class.java)
            startActivity(i)
            val e = getPrefs.edit()
            e.putBoolean(FIRST_START, false)
            e.apply()
        }

    }

    override fun onResume() {
        timerProviderImpl.start()
        super.onResume()
    }

    override fun onPause() {
        timerProviderImpl.stop()
        super.onPause()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return NavigationUI.onNavDestinationSelected(item, navController)
                || super.onOptionsItemSelected(item)
    }

}