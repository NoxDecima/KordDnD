/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
import com.kotlindiscord.kord.extensions.ExtensibleBot
import command.CommandsExtension

@Suppress("UnderscoresInNumericLiterals")  // It's an ID
const val TEST_SERVER_ID = 192344794754711564  // Change this to your test server's ID

private val TOKEN = System.getenv("TOKEN")

suspend fun main() {
    val bot = ExtensibleBot(TOKEN) {
        commands {
            slashCommands = true
        }

        extensions {
            add(::CommandsExtension)
        }
    }

    bot.start()
}
