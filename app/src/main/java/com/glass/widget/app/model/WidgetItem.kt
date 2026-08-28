package com.glass.widget.app.model

import android.content.ComponentName

/**
 * Описание одного виджета в галерее приложения.
 *
 * @param title заголовок карточки
 * @param subtitle короткое описание
 * @param provider ComponentName AppWidgetProvider'а, который нужно
 *   запинить на рабочий стол
 */
data class WidgetItem(
    val title: String,
    val subtitle: String,
    val provider: ComponentName
)
