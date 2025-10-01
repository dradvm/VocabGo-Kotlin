import android.app.Activity
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
import androidx.compose.ui.platform.LocalView
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


object MyColors {
    val Polar = Color(0xFFf7f7f7)
    val Swan = Color (0xffe5e5e5)
    val Hare = Color(0xffafafaf)
    val Wolf = Color(0xff777777)
    val Eel = Color(0xff4b4b4b)
    val Squid = Color(0xffebe3e3)
    val WalkingFish = Color(0xffffdfe0)
    val Flamingo = Color(0xffffb2b2)
    val Pig = Color(0xfff5a4a4)
    val Crab = Color(0xffff7878)
    val Cardinal = Color(0xffff4b4b)
    val FireAnt = Color(0xffea2b2b)
    val Canary = Color(0xfffff5d3)
    val Duck = Color(0xfffbe56d)
    val Bee = Color(0xffffc800)
    val Lion = Color(0xffffb100)
    val Fox = Color(0xffff9600)
    val Cheetah = Color(0xffffce8e)
    val Monkey = Color(0xffe5a259)
    val Camel = Color(0xffe7a601)
    val GuineaPig = Color(0xffcd7900)
    val Grizzly = Color(0xffa56644)
    val SeaSponge = Color(0xffd7ffb8)
    val Turtle = Color(0xffa5ed6e)
    val Owl = Color(0xff58cc02)
    val TreeFrog = Color(0xff58a700)
    val Iguana = Color(0xffddf4ff)
    val Anchovy = Color(0xffd2e4e8)
    val Beluga = Color(0xffbbf2ff)
    val MoonJelly = Color(0xff7af0f2)
    val BlueJay = Color(0xff84d8ff)
    val Macaw = Color(0xff1cb0f6)
    val Whale = Color(0xff1899d6)
    val Humpback = Color(0xff2b70c9)
    val Narwhal = Color(0xff1453a3)
    val Starfish = Color(0xffffaade)
    val Beetle = Color(0xffce82ff)
    val Betta = Color(0xff9069cd)
    val Butterfly = Color(0xff6f4ea1)
}
