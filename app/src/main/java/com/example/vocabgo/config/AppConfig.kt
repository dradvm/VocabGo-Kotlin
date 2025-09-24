import io.github.cdimascio.dotenv.dotenv

object AppConfig {
    private val dotenv = dotenv {
        directory = "/assets"
        filename = "env"
    }

    val BASE_URL: String = dotenv["BASE_URL"] ?: "http://localhost:3000"
    val WEB_CLIENT_ID: String = dotenv["WEB_CLIENT_ID"] ?: ""
}
