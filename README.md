# Proyecto Extraordinario

Este proyecto es una aplicación Android diseñada para gestionar enlaces y videos favoritos, así como para sugerir contenido relevante desde la API de YouTube. La aplicación utiliza tecnologías modernas como Room, Retrofit y Android Jetpack para ofrecer una experiencia fluida y eficiente.

## Características

- **Gestión de Enlaces**: Permite agregar, listar y filtrar enlaces por temáticas.
- **Gestión de Videos**: Posibilidad de agregar videos favoritos y clasificarlos por categorías.
- **Sugerencias de Videos**: Obtiene videos sugeridos desde la API de YouTube basados en palabras clave.
- **Notificaciones**: Notifica al usuario cuando no hay enlaces o videos favoritos.
- **Persistencia de Datos**: Utiliza Room para almacenar enlaces y videos en una base de datos local.

## Tecnologías Utilizadas

- **Lenguaje**: Java
- **Base de Datos**: Room (SQLite)
- **Consumo de API**: Retrofit con Gson
- **Interfaz de Usuario**: Android Jetpack (RecyclerView, ConstraintLayout)
- **Notificaciones**: NotificationManager y NotificationCompat
- **YouTube Player**: Biblioteca `com.pierfrancescosoffritti.androidyoutubeplayer`

## Requisitos Previos

- **Android Studio**: Versión 2024.1.1 Patch 1 o superior.
- **JDK**: Versión 8 o superior.
- **Gradle**: Configurado en el proyecto.
- **API Key de YouTube**: Necesaria para realizar consultas a la API de YouTube.

## Instalación

1. Clona este repositorio en tu máquina local:
   ```bash
   git clone https://github.com/aracavic/PAD-2024-25-EXT-PROY1.git
   ```
2. Abre el proyecto en Android Studio.
3. Configura tu clave de API de YouTube en la clase `SugerenciasActivity`:
   ```java
   private static final String API_KEY = "TU_API_KEY";
   ```
4. Sincroniza las dependencias de Gradle.
5. Ejecuta la aplicación en un dispositivo o emulador Android.

## Estructura del Proyecto

- `activities/`: Contiene las actividades principales de la aplicación.
- `adapters/`: Adaptadores para gestionar las listas en RecyclerView.
- `database/`: Configuración de la base de datos y DAOs.
- `models/`: Modelos de datos utilizados en la aplicación.
- `utils/`: Clases utilitarias como la gestión de notificaciones.
- `res/`: Recursos de la aplicación (layouts, drawables, strings, etc.).

## Uso

1. **Gestión de Enlaces**:
   - Agrega enlaces desde la actividad correspondiente.
   - Filtra enlaces por temáticas usando el `Spinner`.

2. **Sugerencias de Videos**:
   - Consulta videos sugeridos desde la API de YouTube.
   - Agrega videos a favoritos para almacenarlos localmente.

3. **Notificaciones**:
   - Recibe notificaciones cuando no hay enlaces o videos favoritos.

## Dependencias

Las principales dependencias utilizadas en el proyecto son:

```kotlin
implementation("androidx.room:room-runtime:2.5.2")
implementation("com.squareup.retrofit2:retrofit:2.9.0")
implementation("com.squareup.retrofit2:converter-gson:2.9.0")
implementation("com.pierfrancescosoffritti.androidyoutubeplayer:core:11.1.0")
implementation("androidx.work:work-runtime:2.8.1")
```

