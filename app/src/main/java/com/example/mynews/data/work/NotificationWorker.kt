package com.example.mynews.data.work

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.mynews.R
import com.example.mynews.MainActivity
import com.example.mynews.data.entities.Article
import com.example.mynews.data.entities.ArticleData
import com.example.mynews.data.network.NewsApiService
import com.example.mynews.data.provider.LocaleProvider
import com.example.mynews.data.provider.LocaleProviderImpl
import com.example.mynews.data.repository.NewsDataSourceImpl
import com.example.mynews.data.repository.NewsRepository
import com.example.mynews.data.repository.NewsRepositoryImpl
import com.example.mynews.data.repository.NewsRepositoryImpl.Companion.TOP
import com.google.android.gms.tasks.Tasks.await
import kotlinx.coroutines.*


const val NOTIFICATION_TAG = "Notification"


class NotificationWorker(val context: Context, params: WorkerParameters):
    Worker(context, params) {

    override fun doWork(): Result {
        try{
            createNotificationChannel()
            val id = inputData.getLong(NOTIFICATION_ID, 0).toInt()
            sendNotification(id)
            Log.i(NOTIFICATION_TAG,"Work success")
            return Result.success()
        } catch (e: Exception){
            Log.e(NOTIFICATION_TAG,"Error work ${e.message}")
            return Result.failure()
        }

    }


    val notificationManager: NotificationManager =
        applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    private fun sendNotification(id: Int) {
        GlobalScope.launch {
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK
            intent.putExtra(NOTIFICATION_ID, id)

            val pendingIntent = getActivity(applicationContext, 0, intent, 0)

            var text: String? = "news"

            val notification = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle("textTitle")
                //.setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent).setAutoCancel(true)


            val localeProvider = LocaleProviderImpl(context)
            val newsApiService = NewsApiService
            val newsDataSourceImpl = NewsDataSourceImpl(NewsApiService.invoke())
            newsDataSourceImpl.fetchNews(localeProvider.getLocale())
            Log.i("Notification", "fetch news")
            delay(300)

            val textDef: Deferred<String?> =
                GlobalScope.async { newsDataSourceImpl.downloadedNews.value?.articles?.get(1)?.title }
            Log.i(
                "Notification",
                "fetched news ${newsDataSourceImpl.downloadedNews.value?.articles?.get(1)?.title}"
            )
            text = textDef.await()
            notification.setContentText(text)


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notification.setChannelId(CHANNEL_ID)
            }

            with(NotificationManagerCompat.from(context)) {
                notify(id, notification.build())
            }
            Log.i(NOTIFICATION_TAG, "Notification Manager notify")

        }

    }



    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.resources.getString(R.string.channel_name)
            val descriptionText = context.resources.getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system

            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        const val NOTIFICATION_ID = "1"
        const val NOTIFICATION_NAME = "MyNews"
        const val CHANNEL_ID = "MyNews_channel_01"
        const val NOTIFICATION_WORK = "MyNews_notification_work"
    }


}
