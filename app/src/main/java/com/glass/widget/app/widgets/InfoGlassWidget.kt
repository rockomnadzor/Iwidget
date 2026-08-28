package com.glass.widget.app.widgets

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import com.glass.widget.app.R

/**
 * Стеклянный квадратный виджет "Дата": день недели, число,
 * месяц — на полупрозрачном фоне, как ярлыки на скриншотах
 * пользователя.
 */
class InfoGlassWidget : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        for (widgetId in appWidgetIds) {
            val views = RemoteViews(context.packageName, R.layout.widget_info_glass)
            appWidgetManager.updateAppWidget(widgetId, views)
        }
    }
}
