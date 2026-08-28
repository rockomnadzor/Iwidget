package com.glass.widget.app

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.glass.widget.app.model.WidgetItem
import com.glass.widget.app.widgets.ClockGlassWidget
import com.glass.widget.app.widgets.InfoGlassWidget

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                WidgetGalleryScreen(context = this)
            }
        }
    }
}

@Composable
fun WidgetGalleryScreen(context: Context) {
    // Список всех доступных стеклянных виджетов приложения.
    // Чтобы добавить новый виджет — создайте provider + layout
    // (по образцу Clock/InfoGlassWidget) и добавьте сюда пункт.
    val widgets = listOf(
        WidgetItem(
            title = "Часы",
            subtitle = "Аналоговые и цифровые часы на стеклянном фоне",
            provider = ComponentName(context, ClockGlassWidget::class.java)
        ),
        WidgetItem(
            title = "Дата",
            subtitle = "Компактный стеклянный виджет с числом и днём недели",
            provider = ComponentName(context, InfoGlassWidget::class.java)
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF2B3A67), Color(0xFF89A7C2), Color(0xFFE8B9AA))
                )
            )
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                text = "Iwidget",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(24.dp)
            )
            Text(
                text = "Стеклянные виджеты для рабочего стола",
                fontSize = 14.sp,
                color = Color.White.copy(alpha = 0.8f),
                modifier = Modifier.padding(horizontal = 24.dp)
            )
            Spacer(Modifier.height(12.dp))
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                items(widgets) { widget ->
                    WidgetCard(context, widget)
                }
            }
        }
    }
}

@Composable
fun WidgetCard(context: Context, widget: WidgetItem) {
    // Сама карточка тоже стеклянная — полупрозрачная заливка +
    // скруглённые углы, чтобы превью соответствовало реальному
    // виду виджета на рабочем столе.
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(28.dp))
            .background(Color.White.copy(alpha = 0.15f))
            .padding(20.dp)
    ) {
        Text(widget.title, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
        Spacer(Modifier.height(6.dp))
        Text(widget.subtitle, fontSize = 14.sp, color = Color.White.copy(alpha = 0.85f))
        Spacer(Modifier.height(16.dp))
        Button(
            onClick = { pinWidget(context, widget.provider) },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White.copy(alpha = 0.25f),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Добавить на рабочий стол")
        }
    }
}

/**
 * Запрашивает у системного лаунчера закрепление виджета на
 * рабочем столе (Android 8.0+, API 26+). Если лаунчер не
 * поддерживает такой пин напрямую — сообщаем пользователю
 * добавить виджет вручную через стандартное меню виджетов.
 */
fun pinWidget(context: Context, provider: ComponentName) {
    val appWidgetManager = AppWidgetManager.getInstance(context)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        if (appWidgetManager.isRequestPinAppWidgetSupported) {
            appWidgetManager.requestPinAppWidget(provider, null, null)
        } else {
            Toast.makeText(
                context,
                "Ваш лаунчер не поддерживает быстрое добавление. Добавьте виджет вручную через меню виджетов рабочего стола.",
                Toast.LENGTH_LONG
            ).show()
        }
    } else {
        Toast.makeText(
            context,
            "Добавьте виджет вручную через меню виджетов рабочего стола.",
            Toast.LENGTH_LONG
        ).show()
    }
}
