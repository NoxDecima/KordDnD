package command

import com.kotlindiscord.kord.extensions.ExtensibleBot
import com.kotlindiscord.kord.extensions.extensions.Extension
import dev.kord.common.annotation.KordPreview

class CommandsExtension(bot: ExtensibleBot) : Extension(bot) {
    override val name = "SlashCommands"

    @KordPreview
    override suspend fun setup() {
        monster()
        spell()
    }
}
