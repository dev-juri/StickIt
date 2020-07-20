package com.oluwafemi.stickit

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews

/**
 * Implementation of App Widget functionality.
 */
class StickItWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {

    val sharedPreferences = context.getSharedPreferences(context.getString(R.string.file_key), Context.MODE_PRIVATE)
    val presentNote = sharedPreferences.getString(context.getString(R.string.our_note), "")
    val widgetText = if (presentNote == "") context.getString(R.string.appwidget_text) else presentNote
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.stick_it_widget)
    views.setTextViewText(R.id.appwidget_text, widgetText)

    views.setOnClickPendingIntent(R.id.appwidget_text, getPendingIntent(context))

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}

private fun getPendingIntent(context: Context) : PendingIntent{
    val intent = Intent(context, MainActivity::class.java)
    return PendingIntent.getActivity(context, 200, intent, 0)
}
