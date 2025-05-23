package com.example.proyectoextraordinario.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.proyectoextraordinario.R;

public class NotificacionUtil {
    private static final String CHANNEL_ID = "canal_favoritos";

    public static void mostrarNotificacion(Context context, String titulo, String mensaje) {
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Crear canal si es Android 8+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel canal = new NotificationChannel(
                    CHANNEL_ID,
                    "Notificaciones de Favoritos",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            canal.setDescription("Notifica cuando no hay enlaces o videos favoritos");
            notificationManager.createNotificationChannel(canal);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notifications)  // Usa tu icono en drawable
                .setContentTitle(titulo)
                .setContentText(mensaje)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true); // se cierra al tocar

        notificationManager.notify(1001, builder.build());
    }
}
