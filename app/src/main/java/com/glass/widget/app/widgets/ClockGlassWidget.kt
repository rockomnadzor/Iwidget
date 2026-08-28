package com.glass.widget.app.widgets

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import com.glass.widget.app.R

/**
 * Стеклянный виджет "Часы": аналоговые + цифровые часы на
 * полупрозрачном фоне с обводкой-бликом. Время рендерится
 * системными View AnalogClock/TextClock, поэтому отдельного
 * onUpdate-наполнения данными не требуется — они сами
 * тикают в реальном времени.
 */
class ClockGlassWidget : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        for (widgetId in appWidgetIds) {
            val views = RemoteViews(context.packageName, R.layout.widget_clock_glass)
            appWidgetManager.updateAppWidget(widgetId, views)
        }
    }
}
