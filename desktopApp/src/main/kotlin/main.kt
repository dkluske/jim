import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import net.jim.App
import net.jim.data.DatabaseDriverManager
import java.awt.Dimension

fun main() = application {
    Window(
        title = "jim",
        state = rememberWindowState(width = 400.dp, height = 700.dp),
        onCloseRequest = ::exitApplication,
    ) {
        window.minimumSize = Dimension(350, 600)
        window.isResizable = false
        App(
            databaseDriverManager = DatabaseDriverManager()
        )
    }
}

