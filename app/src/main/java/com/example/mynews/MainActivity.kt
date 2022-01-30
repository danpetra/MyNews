package com.example.mynews

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
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

        val data = Data.Builder().putInt(NOTIFICATION_ID, 0).build()

        //val notificationsSettingsProviderImpl = NotificationsSettingsProviderImpl(this)
        //if(notificationsSettingsProviderImpl.isSendingNotifications())
          //  scheduleNotification(calculateDelay(15),data)

       /* Log.i("debuggable","Check debuggable")
        if (ApplicationInfo.FLAG_DEBUGGABLE!=0) Log.i("debuggable","Is debuggable")
        else Log.i("debuggable","Is not debuggable")*/
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

    /** Shcedule Notifications */

    private fun calculateDelay(hourOfDay: Int): Long{
        val now = Date()
        val calendar = GregorianCalendar()
        Log.i(NOTIFICATION_TAG, "Time is set ${calendar.time}")
        //var hourPlus = if (calendar.get(Calendar.HOUR_OF_DAY) < 12) hourOfDay else hourOfDay - 12
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, 40)
        calendar.set(Calendar.SECOND, 0)
        Log.i(NOTIFICATION_TAG, "Time is set ${calendar.time}")
        var date = calendar.time
        if (date.before(now)) {
            calendar.add(Calendar.DATE, 1)
            date = calendar.time
            Log.i(NOTIFICATION_TAG,"Added")
        }
        Log.i(NOTIFICATION_TAG, "Time is set ${calendar.time}")
        Log.i(NOTIFICATION_TAG, "Time is set ${calendar.get(Calendar.DATE)}, ${calendar.get(Calendar.HOUR)}, ${calendar.get(Calendar.MINUTE)}")
        val timeDifference = date.time - now.time
        Log.i(NOTIFICATION_TAG, "Time diff ${timeDifference}")
        return timeDifference
    }

    private fun scheduleNotification(delay: Long, data: Data) {

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .build()

        val notificationWork = OneTimeWorkRequest.Builder(NotificationWorker::class.java)
            .setInitialDelay(60000, TimeUnit.MILLISECONDS)
            .setInputData(data)
            .addTag("Notification_Work_Tag")
            .setConstraints(constraints)
            .build()

        val instanceWorkManager = WorkManager.getInstance(this)
        instanceWorkManager.enqueue(notificationWork).state
            .observe(this) { state ->
                Log.i(NOTIFICATION_TAG, "ForegroundWorker: $state")
            }
        Log.i(NOTIFICATION_TAG, "Notification is scheduled with delay ${delay} sec")
    }
}