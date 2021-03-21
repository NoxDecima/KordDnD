package command

import TEST_SERVER_ID
import com.kotlindiscord.kord.extensions.commands.converters.string
import com.kotlindiscord.kord.extensions.commands.parser.Arguments
import dev.kord.common.annotation.KordPreview
import dndapi.DnDApi


@KordPreview
suspend fun CommandsExtension.monster() {
    slashCommand(::EnemyCommandArguments) {
        name = "monster"
        description = "Get information about a specific enemy"
        showSource = true

        action {
            with(arguments) {

                val monster = DnDApi.getMonster(monsterName)

                if (monster == null) {
                    followUp("No monster with name **$monsterName** could be found.")
                } else {
                    followUp {
                        embed {
                            title = monster.name

                            description = "Size: ${monster.size}\nType: ${monster.type}"

                            field {
                                inline = true
                                name = "HP"
                                value = monster.hit_points.toString()
                            }

                            field {
                                inline = true
                                name = "AC"
                                value = monster.armor_class.toString()
                            }

                            if (monster.speed?.walk != null) {
                                field {
                                    inline = true
                                    name = "Speed"
                                    value = monster.speed.walk
                                }
                            }


                            if (monster.actions?.isNotEmpty() == true) {
                                field {
                                    inline = false
                                    name = "Actions"
                                    value = monster.actions
                                        .map { "**${it.name}**: ${it.desc}" }
                                        .joinToString(separator = "\n")
                                        .take(1024)

                                }
                            }

                            for (ability in monster.special_abilities ?: listOf()) {
                                field {
                                    inline = false
                                    name = ability.name.toString()
                                    value = ability.desc.toString()
                                }
                            }

                            field {
                                inline = true
                                name = "Str"
                                value = monster.strength.toString()
                            }

                            field {
                                inline = true
                                name = "Dex"
                                value = monster.dexterity.toString()
                            }

                            field {
                                inline = true
                                name = "Con"
                                value = monster.constitution.toString()
                            }

                            field {
                                inline = true
                                name = "Int"
                                value = monster.intelligence.toString()
                            }

                            field {
                                inline = true
                                name = "Wis"
                                value = monster.wisdom.toString()
                            }

                            field {
                                inline = true
                                name = "Cha"
                                value = monster.charisma.toString()
                            }
                        }
                    }
                }

            }
        }
    }
}

@OptIn(KordPreview::class)
class EnemyCommandArguments : Arguments() {
    val monsterName by string(
        displayName = "name",
        description = "The name of the Enemy in question (or part of it)"
    )
}



