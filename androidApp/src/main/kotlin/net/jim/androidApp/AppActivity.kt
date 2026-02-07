package net.jim.androidApp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import net.jim.App
import net.jim.data.DatabaseDriverManager

class AppActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            App(
                databaseDriverManager = DatabaseDriverManager(
                    context = applicationContext,
                )
            )
        }
    }
}