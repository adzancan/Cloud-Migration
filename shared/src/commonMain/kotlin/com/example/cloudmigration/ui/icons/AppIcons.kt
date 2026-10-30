package com.example.cloudmigration.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

object AppIcons {

    val Cloud: ImageVector by lazy {
        ImageVector.Builder(
            name = "Cloud",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).path(fill = SolidColor(Color.White)) {
            moveTo(19.35f, 10.04f)
            curveTo(18.67f, 6.59f, 15.64f, 4f, 12f, 4f)
            curveTo(9.11f, 4f, 6.6f, 5.64f, 5.35f, 8.04f)
            curveTo(2.34f, 8.36f, 0f, 10.91f, 0f, 14f)
            curveTo(0f, 17.31f, 2.69f, 20f, 6f, 20f)
            lineTo(19f, 20f)
            curveTo(21.76f, 20f, 24f, 17.76f, 24f, 15f)
            curveTo(24f, 12.36f, 21.95f, 10.22f, 19.35f, 10.04f)
            close()
        }.build()
    }

    val Wifi: ImageVector by lazy {
        ImageVector.Builder(
            name = "Wifi",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).path(fill = SolidColor(Color.White)) {
            moveTo(12f, 3f)
            curveTo(7.03f, 3f, 2.57f, 5.1f, 0f, 8.44f)
            lineTo(12f, 21f)
            lineTo(24f, 8.44f)
            curveTo(21.43f, 5.1f, 16.97f, 3f, 12f, 3f)
            close()
        }.build()
    }

    val Router: ImageVector by lazy {
        ImageVector.Builder(
            name = "Router",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).path(fill = SolidColor(Color.White)) {
            moveTo(19f, 13f)
            horizontalLineTo(5f)
            curveTo(3.34f, 13f, 2f, 14.34f, 2f, 16f)
            verticalLineTo(18f)
            curveTo(2f, 19.66f, 3.34f, 21f, 5f, 21f)
            horizontalLineTo(19f)
            curveTo(20.66f, 21f, 22f, 19.66f, 22f, 18f)
            verticalLineTo(16f)
            curveTo(22f, 14.34f, 20.66f, 13f, 19f, 13f)
            close()
            moveTo(6f, 18f)
            curveTo(5.45f, 18f, 5f, 17.55f, 5f, 17f)
            curveTo(5f, 16.45f, 5.45f, 16f, 6f, 16f)
            curveTo(6.55f, 16f, 7f, 16.45f, 7f, 17f)
            curveTo(7f, 17.55f, 6.55f, 18f, 6f, 18f)
            close()
            moveTo(9.5f, 18f)
            curveTo(8.95f, 18f, 8.5f, 17.55f, 8.5f, 17f)
            curveTo(8.5f, 16.45f, 8.95f, 16f, 9.5f, 16f)
            curveTo(10.05f, 16f, 10.5f, 16.45f, 10.5f, 17f)
            curveTo(10.5f, 17.55f, 10.05f, 18f, 9.5f, 18f)
            close()
            moveTo(13f, 18f)
            curveTo(12.45f, 18f, 12f, 17.55f, 12f, 17f)
            curveTo(12f, 16.45f, 12.45f, 16f, 13f, 16f)
            curveTo(13.55f, 16f, 14f, 16.45f, 14f, 17f)
            curveTo(14f, 17.55f, 13.55f, 18f, 13f, 18f)
            close()
            moveTo(6f, 3f)
            verticalLineTo(11f)
            horizontalLineTo(8f)
            verticalLineTo(3f)
            horizontalLineTo(6f)
            close()
            moveTo(16f, 3f)
            verticalLineTo(11f)
            horizontalLineTo(18f)
            verticalLineTo(3f)
            horizontalLineTo(16f)
            close()
        }.build()
    }

    val Switch: ImageVector by lazy {
        ImageVector.Builder(
            name = "Switch",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).path(fill = SolidColor(Color.White)) {
            moveTo(4f, 6f)
            horizontalLineTo(20f)
            curveTo(21.1f, 6f, 22f, 6.9f, 22f, 8f)
            verticalLineTo(16f)
            curveTo(22f, 17.1f, 21.1f, 18f, 20f, 18f)
            horizontalLineTo(4f)
            curveTo(2.9f, 18f, 2f, 17.1f, 2f, 16f)
            verticalLineTo(8f)
            curveTo(2f, 6.9f, 2.9f, 6f, 4f, 6f)
            close()
            moveTo(6f, 10f)
            horizontalLineTo(8f)
            verticalLineTo(14f)
            horizontalLineTo(6f)
            verticalLineTo(10f)
            close()
            moveTo(10f, 10f)
            horizontalLineTo(12f)
            verticalLineTo(14f)
            horizontalLineTo(10f)
            verticalLineTo(10f)
            close()
            moveTo(14f, 10f)
            horizontalLineTo(16f)
            verticalLineTo(14f)
            horizontalLineTo(14f)
            verticalLineTo(10f)
            close()
            moveTo(18f, 10f)
            horizontalLineTo(19.5f)
            verticalLineTo(14f)
            horizontalLineTo(18f)
            verticalLineTo(10f)
            close()
        }.build()
    }

    val Server: ImageVector by lazy {
        ImageVector.Builder(
            name = "Server",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).path(fill = SolidColor(Color.White)) {
            moveTo(4f, 4f)
            horizontalLineTo(20f)
            curveTo(21.1f, 4f, 22f, 4.9f, 22f, 6f)
            verticalLineTo(9f)
            curveTo(22f, 10.1f, 21.1f, 11f, 20f, 11f)
            horizontalLineTo(4f)
            curveTo(2.9f, 11f, 2f, 10.1f, 2f, 9f)
            verticalLineTo(6f)
            curveTo(2f, 4.9f, 2.9f, 4f, 4f, 4f)
            close()
            moveTo(4f, 13f)
            horizontalLineTo(20f)
            curveTo(21.1f, 13f, 22f, 13.9f, 22f, 15f)
            verticalLineTo(18f)
            curveTo(22f, 19.1f, 21.1f, 20f, 20f, 20f)
            horizontalLineTo(4f)
            curveTo(2.9f, 20f, 2f, 19.1f, 2f, 18f)
            verticalLineTo(15f)
            curveTo(2f, 13.9f, 2.9f, 13f, 4f, 13f)
            close()
            moveTo(6f, 7.5f)
            curveTo(6.83f, 7.5f, 7.5f, 6.83f, 7.5f, 6f)
            curveTo(7.5f, 5.17f, 6.83f, 4.5f, 6f, 4.5f)
            curveTo(5.17f, 4.5f, 4.5f, 5.17f, 4.5f, 6f)
            curveTo(4.5f, 6.83f, 5.17f, 7.5f, 6f, 7.5f)
            close()
            moveTo(6f, 16.5f)
            curveTo(6.83f, 16.5f, 7.5f, 15.83f, 7.5f, 15f)
            curveTo(7.5f, 14.17f, 6.83f, 13.5f, 6f, 13.5f)
            curveTo(5.17f, 13.5f, 4.5f, 14.17f, 4.5f, 15f)
            curveTo(4.5f, 15.83f, 5.17f, 16.5f, 6f, 16.5f)
            close()
        }.build()
    }

    val Camera: ImageVector by lazy {
        ImageVector.Builder(
            name = "Camera",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).path(fill = SolidColor(Color.White)) {
            moveTo(17f, 10.5f)
            verticalLineTo(7f)
            curveTo(17f, 6.45f, 16.55f, 6f, 16f, 6f)
            horizontalLineTo(4f)
            curveTo(3.45f, 6f, 3f, 6.45f, 3f, 7f)
            verticalLineTo(17f)
            curveTo(3f, 17.55f, 3.45f, 18f, 4f, 18f)
            horizontalLineTo(16f)
            curveTo(16.55f, 18f, 17f, 17.55f, 17f, 17f)
            verticalLineTo(13.5f)
            lineTo(21f, 17.5f)
            verticalLineTo(6.5f)
            lineTo(17f, 10.5f)
            close()
        }.build()
    }

    val Refresh: ImageVector by lazy {
        ImageVector.Builder(
            name = "Refresh",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).path(fill = SolidColor(Color.White)) {
            moveTo(17.65f, 6.35f)
            curveTo(16.2f, 4.9f, 14.21f, 4f, 12f, 4f)
            curveTo(7.58f, 4f, 4.01f, 7.58f, 4.01f, 12f)
            curveTo(4.01f, 16.42f, 7.58f, 20f, 12f, 20f)
            curveTo(15.73f, 20f, 18.84f, 17.45f, 19.73f, 14f)
            horizontalLineTo(17.65f)
            curveTo(16.83f, 16.33f, 14.61f, 18f, 12f, 18f)
            curveTo(8.69f, 18f, 6f, 15.31f, 6f, 12f)
            curveTo(6f, 8.69f, 8.69f, 6f, 12f, 6f)
            curveTo(13.66f, 6f, 15.14f, 6.69f, 16.22f, 7.78f)
            lineTo(13f, 11f)
            horizontalLineTo(20f)
            verticalLineTo(4f)
            lineTo(17.65f, 6.35f)
            close()
        }.build()
    }

    val Settings: ImageVector by lazy {
        ImageVector.Builder(
            name = "Settings",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).path(fill = SolidColor(Color.White)) {
            moveTo(19.14f, 12.94f)
            curveTo(19.18f, 12.63f, 19.2f, 12.32f, 19.2f, 12f)
            curveTo(19.2f, 11.68f, 19.18f, 11.37f, 19.14f, 11.06f)
            lineTo(21.16f, 9.48f)
            curveTo(21.34f, 9.34f, 21.39f, 9.08f, 21.28f, 8.87f)
            lineTo(19.37f, 5.56f)
            curveTo(19.25f, 5.35f, 18.99f, 5.27f, 18.78f, 5.35f)
            lineTo(16.4f, 6.31f)
            curveTo(15.91f, 5.93f, 15.37f, 5.62f, 14.8f, 5.38f)
            lineTo(14.44f, 2.85f)
            curveTo(14.41f, 2.62f, 14.21f, 2.45f, 13.98f, 2.45f)
            horizontalLineTo(10.18f)
            curveTo(9.95f, 2.45f, 9.75f, 2.62f, 9.72f, 2.85f)
            lineTo(9.36f, 5.38f)
            curveTo(8.79f, 5.62f, 8.25f, 5.93f, 7.76f, 6.31f)
            lineTo(5.38f, 5.35f)
            curveTo(5.17f, 5.27f, 4.91f, 5.35f, 4.79f, 5.56f)
            lineTo(2.88f, 8.87f)
            curveTo(2.77f, 9.08f, 2.82f, 9.34f, 3f, 9.48f)
            lineTo(5.02f, 11.06f)
            curveTo(4.98f, 11.37f, 4.96f, 11.68f, 4.96f, 12f)
            curveTo(4.96f, 12.32f, 4.98f, 12.63f, 5.02f, 12.94f)
            lineTo(3f, 14.52f)
            curveTo(2.82f, 14.66f, 2.77f, 14.92f, 2.88f, 15.13f)
            lineTo(4.79f, 18.44f)
            curveTo(4.91f, 18.65f, 5.17f, 18.73f, 5.38f, 18.65f)
            lineTo(7.76f, 17.69f)
            curveTo(8.25f, 18.07f, 8.79f, 18.38f, 9.36f, 18.62f)
            lineTo(9.72f, 21.15f)
            curveTo(9.75f, 21.38f, 9.95f, 21.55f, 10.18f, 21.55f)
            horizontalLineTo(13.98f)
            curveTo(14.21f, 21.55f, 14.41f, 21.38f, 14.44f, 21.15f)
            lineTo(14.8f, 18.62f)
            curveTo(15.37f, 18.38f, 15.91f, 18.07f, 16.4f, 17.69f)
            lineTo(18.78f, 18.65f)
            curveTo(18.99f, 18.73f, 19.25f, 18.65f, 19.37f, 18.44f)
            lineTo(21.28f, 15.13f)
            curveTo(21.39f, 14.92f, 21.34f, 14.66f, 21.16f, 14.52f)
            lineTo(19.14f, 12.94f)
            close()
            moveTo(12f, 15.5f)
            curveTo(10.07f, 15.5f, 8.5f, 13.93f, 8.5f, 12f)
            curveTo(8.5f, 10.07f, 10.07f, 8.5f, 12f, 8.5f)
            curveTo(13.93f, 8.5f, 15.5f, 10.07f, 15.5f, 12f)
            curveTo(15.5f, 13.93f, 13.93f, 15.5f, 12f, 15.5f)
            close()
        }.build()
    }

    val Share: ImageVector by lazy {
        ImageVector.Builder(
            name = "Share",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).path(fill = SolidColor(Color.White)) {
            moveTo(18f, 16.08f)
            curveTo(17.24f, 16.08f, 16.56f, 16.38f, 16.04f, 16.85f)
            lineTo(8.91f, 12.7f)
            curveTo(8.96f, 12.47f, 9f, 12.24f, 9f, 12f)
            curveTo(9f, 11.76f, 8.96f, 11.53f, 8.91f, 11.3f)
            lineTo(15.96f, 7.19f)
            curveTo(16.5f, 7.69f, 17.21f, 8f, 18f, 8f)
            curveTo(19.66f, 8f, 21f, 6.66f, 21f, 5f)
            curveTo(21f, 3.34f, 19.66f, 2f, 18f, 2f)
            curveTo(16.34f, 2f, 15f, 3.34f, 15f, 5f)
            curveTo(15f, 5.24f, 15.04f, 5.47f, 15.09f, 5.7f)
            lineTo(8.04f, 9.81f)
            curveTo(7.5f, 9.31f, 6.79f, 9f, 6f, 9f)
            curveTo(4.34f, 9f, 3f, 10.34f, 3f, 12f)
            curveTo(3f, 13.66f, 4.34f, 15f, 6f, 15f)
            curveTo(6.79f, 15f, 7.5f, 14.69f, 8.04f, 14.19f)
            lineTo(15.16f, 18.34f)
            curveTo(15.11f, 18.55f, 15.08f, 18.77f, 15.08f, 19f)
            curveTo(15.08f, 20.61f, 16.39f, 21.92f, 18f, 21.92f)
            curveTo(19.61f, 21.92f, 20.92f, 20.61f, 20.92f, 19f)
            curveTo(20.92f, 17.39f, 19.61f, 16.08f, 18f, 16.08f)
            close()
        }.build()
    }

    val Edit: ImageVector by lazy {
        ImageVector.Builder(
            name = "Edit",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).path(fill = SolidColor(Color.White)) {
            moveTo(3f, 17.25f)
            verticalLineTo(21f)
            horizontalLineTo(6.75f)
            lineTo(17.81f, 9.94f)
            lineTo(14.06f, 6.19f)
            lineTo(3f, 17.25f)
            close()
            moveTo(20.71f, 7.04f)
            curveTo(21.1f, 6.65f, 21.1f, 6.02f, 20.71f, 5.63f)
            lineTo(18.37f, 3.29f)
            curveTo(17.98f, 2.9f, 17.35f, 2.9f, 16.96f, 3.29f)
            lineTo(15.13f, 5.12f)
            lineTo(18.88f, 8.87f)
            lineTo(20.71f, 7.04f)
            close()
        }.build()
    }

    val Copy: ImageVector by lazy {
        ImageVector.Builder(
            name = "Copy",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).path(fill = SolidColor(Color.White)) {
            moveTo(16f, 1f)
            horizontalLineTo(4f)
            curveTo(2.9f, 1f, 2f, 1.9f, 2f, 3f)
            verticalLineTo(15f)
            horizontalLineTo(4f)
            verticalLineTo(3f)
            horizontalLineTo(16f)
            verticalLineTo(1f)
            close()
            moveTo(19f, 5f)
            horizontalLineTo(8f)
            curveTo(6.9f, 5f, 6f, 5.9f, 6f, 7f)
            verticalLineTo(21f)
            curveTo(6f, 22.1f, 6.9f, 23f, 8f, 23f)
            horizontalLineTo(19f)
            curveTo(20.1f, 23f, 21f, 22.1f, 21f, 21f)
            verticalLineTo(7f)
            curveTo(21f, 5.9f, 20.1f, 5f, 19f, 5f)
            close()
            moveTo(19f, 21f)
            horizontalLineTo(8f)
            verticalLineTo(7f)
            horizontalLineTo(19f)
            verticalLineTo(21f)
            close()
        }.build()
    }

    val Flask: ImageVector by lazy {
        ImageVector.Builder(
            name = "Flask",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).path(fill = SolidColor(Color.White)) {
            moveTo(6f, 22f)
            horizontalLineTo(18f)
            curveTo(19.1f, 22f, 19.8f, 20.9f, 19.3f, 19.9f)
            lineTo(15f, 12f)
            verticalLineTo(5f)
            horizontalLineTo(16f)
            curveTo(16.55f, 5f, 17f, 4.55f, 17f, 4f)
            curveTo(17f, 3.45f, 16.55f, 3f, 16f, 3f)
            horizontalLineTo(8f)
            curveTo(7.45f, 3f, 7f, 3.45f, 7f, 4f)
            curveTo(7f, 4.55f, 7.45f, 5f, 8f, 5f)
            horizontalLineTo(9f)
            verticalLineTo(12f)
            lineTo(4.7f, 19.9f)
            curveTo(4.2f, 20.9f, 4.9f, 22f, 6f, 22f)
            close()
        }.build()
    }

    val Check: ImageVector by lazy {
        ImageVector.Builder(
            name = "Check",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).path(fill = SolidColor(Color.White)) {
            moveTo(9f, 16.17f)
            lineTo(4.83f, 12f)
            lineTo(3.41f, 13.41f)
            lineTo(9f, 19f)
            lineTo(21f, 7f)
            lineTo(19.59f, 5.59f)
            lineTo(9f, 16.17f)
            close()
        }.build()
    }
}
