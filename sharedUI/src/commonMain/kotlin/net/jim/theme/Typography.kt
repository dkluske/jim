package net.jim.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import jim.sharedui.generated.resources.JetBrainsMono_VariableFont_wght
import jim.sharedui.generated.resources.Res
import org.jetbrains.compose.resources.Font

val JetbrainsMono
    @Composable get() = FontFamily(
        Font(
            resource = Res.font.JetBrainsMono_VariableFont_wght,
            weight = FontWeight.Normal,
        ),
        Font(
            resource = Res.font.JetBrainsMono_VariableFont_wght,
            weight = FontWeight.Bold,
        )
    )

val typography: Typography
    @Composable get() = Typography(
        bodyLarge = TextStyle(
            fontFamily = JetbrainsMono,
            fontWeight = FontWeight.Normal,
            fontSize = 20.sp
        ),
        bodyMedium = TextStyle(
            fontFamily = JetbrainsMono,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        ),
        bodySmall = TextStyle(
            fontFamily = JetbrainsMono,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp
        ),
        headlineLarge = TextStyle(
            fontFamily = JetbrainsMono,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        ),
        headlineMedium = TextStyle(
            fontFamily = JetbrainsMono,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        ),
        headlineSmall = TextStyle(
            fontFamily = JetbrainsMono,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp
        ),
        displayLarge = TextStyle(
            fontFamily = JetbrainsMono,
            fontWeight = FontWeight.Normal,
            fontSize = 20.sp
        ),
        displayMedium = TextStyle(
            fontFamily = JetbrainsMono,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        ),
        displaySmall = TextStyle(
            fontFamily = JetbrainsMono,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp
        )
    )