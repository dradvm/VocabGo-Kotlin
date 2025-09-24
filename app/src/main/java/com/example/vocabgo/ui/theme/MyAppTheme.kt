import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.ColorScheme
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.example.vocabgo.R

// Light theme
val LightColorScheme: ColorScheme = lightColorScheme(
    primary = Color(121, 158, 255),
    onPrimary = Color(66,113,210),
    tertiary = Color(117,117,117),
    background = Color(255, 255, 255),
)

val Nunito = FontFamily(
    Font(R.font.nunito_light, FontWeight.Light),
    Font(R.font.nunito_regular, FontWeight.Normal),
    Font(R.font.nunito_medium, FontWeight.Medium),
    Font(R.font.nunito_semibold, FontWeight.SemiBold),
    Font(R.font.nunito_bold, FontWeight.Bold),
    Font(R.font.nunito_extrabold, FontWeight.ExtraBold)
)
// 2. Typography
private val MyTypography = Typography(
    titleLarge = TextStyle(fontFamily = Nunito, fontSize = 32.sp, fontWeight = FontWeight.Bold),
    titleMedium = TextStyle(fontFamily = Nunito, fontSize = 18.sp, fontWeight = FontWeight.SemiBold),
    bodyMedium = TextStyle(fontFamily = Nunito, fontSize = 16.sp),

)

// 3. Shapes
private val MyShapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(12.dp),
    large = RoundedCornerShape(24.dp)
)

// 4. Theme wrapper

@Composable
fun MyAppTheme(darkTheme: Boolean = false, content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = MyTypography,
        shapes = MyShapes,
        content = content
    )
}
