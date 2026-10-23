package net.jim.ui.routes.stats

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.jim.ui.components.MainRouteScaffold

@Composable
fun StatsView(
    viewModel: StatsViewModel
) {
    MainRouteScaffold(
        root = viewModel.root
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) {
            Text(
                text = "Stats"
            )
        }
        Row(
            modifier = Modifier.fillMaxSize().padding(8.dp)
        ) {
            // TODO: some nice charts
        }
    }
}