package com.example.proyectoextraordinario.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.proyectoextraordinario.R;

/**
 * Clase utilitaria para gestionar y mostrar notificaciones en la aplicación.
 */
public class NotificacionUtil {

    /**
     * Identificador único del canal de notificaciones.
     */
    private static final String CHANNEL_ID = "canal_favoritos";

    /**
     * Muestra una notificación al usuario.
     *
     * @param context Contexto de la aplicación.
     * @param titulo  Título de la notificación.
     * @param mensaje Mensaje de la notificación.
     */
    public static void mostrarNotificacion(Context context, String titulo, String mensaje) {
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Crear canal de notificaciones si la versión de Android es 8 (Oreo) o superior
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel canal = new NotificationChannel(
                    CHANNEL_ID,
                    "Notificaciones de Favoritos",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            canal.setDescription("Notifica cuando no hay enlaces o videos favoritos");
            notificationManager.createNotificationChannel(canal);
        }

        // Construir y mostrar la notificación
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notifications)  // Icono de la notificación
                .setContentTitle(titulo)                   // Título de la notificación
                .setContentText(mensaje)                   // Mensaje de la notificación
                .setPriority(NotificationCompat.PRIORITY_DEFAULT) // Prioridad de la notificación
                .setAutoCancel(true);                      // Cierra la notificación al tocarla

        notificationManager.notify(1001, builder.build());
    }
}