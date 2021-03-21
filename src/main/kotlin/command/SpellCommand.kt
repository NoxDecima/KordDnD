package command

import TEST_SERVER_ID
import com.kotlindiscord.kord.extensions.commands.converters.string
import com.kotlindiscord.kord.extensions.commands.parser.Arguments
import dev.kord.common.annotation.KordPreview
import dndapi.DnDApi


@KordPreview
suspend fun CommandsExtension.spell() {
    slashCommand(::SpellCommandArguments) {
        name = "spell"
        description = "Get information about a specific spell"
        showSource = true

        action {
            with(arguments) {

                val spell = DnDApi.getSpell(spellName)

                if (spell == null) {
                    followUp("No spell with name **$spellName** could be found.")
                } else {
                    followUp {
                        embed {
                            title = spell.name

                            field {
                                inline=true
                                name="Level"
                                value=spell.level.toString()
                            }

                            field {
                                inline=true
                                name="Casting Time"
                                value= spell.casting_time.toString()
                            }

                            field {
                                inline=true
                                name="Range"
                                value= spell.range.toString()
                            }

                            field {
                                inline=true
                                name="Components"
                                value= spell.components?.joinToString().toString()
                            }

                            field {
                                inline=true
                                name="Duration"
                                value= spell.duration.toString()
                            }

                            field {
                                inline=true
                                name="School"
                                value= spell.school?.name.toString()
                            }

                            field {
                                name = "Description"
                                value = spell.desc?.joinToString(separator = "\n").toString().take(1024)
                            }

                            if (spell.damage != null) {
                                field {
                                    name = "Damage"
                                    value = spell.damage.damage_type?.name + "\n" +
                                            spell.damage.damage_at_slot_level.toString()
                                }
                            }

                            field {
                                inline=true
                                name="Ritual"
                                value=spell.ritual.toString()
                            }
                            field {
                                inline=true
                                name="Concentration"
                                value=spell.concentration.toString()
                            }
                        }
                    }
                }

            }
        }
    }
}

@OptIn(KordPreview::class)
class SpellCommandArguments : Arguments() {
    val spellName by string(
        displayName = "name",
        description = "The name of the Spell in question (or part of it)"
    )
}



