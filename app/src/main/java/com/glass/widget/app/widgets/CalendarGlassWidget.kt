package com.glass.widget.app.widgets

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import com.glass.widget.app.R
import java.time.LocalDate

class CalendarGlassWidget : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        for (widgetId in appWidgetIds) {
            val views = RemoteViews(context.packageName, R.layout.widget_calendar_glass)
            fillMonthGrid(views)
            appWidgetManager.updateAppWidget(widgetId, views)
        }
    }

    private fun fillMonthGrid(views: RemoteViews) {
        val today = LocalDate.now()
        val firstOfMonth = today.withDayOfMonth(1)
        val daysInMonth = today.lengthOfMonth()
        val firstDayColumn = (firstOfMonth.dayOfWeek.value + 6) % 7

        for (cellIndex in 1..42) {
            val cellId = CELL_IDS[cellIndex - 1]
            val dayNumber = cellIndex - firstDayColumn
            if (dayNumber in 1..daysInMonth) {
                views.setTextViewText(cellId, dayNumber.toString())
                if (dayNumber == today.dayOfMonth) {
                    views.setTextColor(cellId, 0xFFFFFFFF.toInt())
                    views.setInt(cellId, "setBackgroundResource", R.drawable.today_pill)
                } else {
                    views.setTextColor(cellId, 0xE6FFFFFF.toInt())
                    views.setInt(cellId, "setBackgroundResource", 0)
                }
            } else {
                views.setTextViewText(cellId, "")
            }
        }
    }

    companion object {
        private val CELL_IDS: IntArray by lazy {
            IntArray(42) { i ->
                val fieldName = "cell_${i + 1}"
                R.id::class.java.getField(fieldName).getInt(null)
            }
        }
    }
}
