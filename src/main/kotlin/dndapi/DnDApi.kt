package dndapi

import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*

object DnDApi {
    private val client = HttpClient {
        install(JsonFeature) {
            serializer = GsonSerializer() {
                setPrettyPrinting()
                disableHtmlEscaping()
            }
        }
    }


    /**
     * This function searches for info of the monster with [monsterName] using the api of www.dnd5eapi.co
     * If the monster is found it is returned as a Monster object however if it can not be found null is returned instead.
     */
    suspend fun getMonster(monsterName: String): Monster? {

        //First search for monsters that contain [monsterName]
        val searchResult: ApiSearchResult =
            client.get("http://www.dnd5eapi.co/api/monsters/?name=$monsterName")

        searchResult.results?.firstOrNull() ?: return null

        return try {
            client.get("http://www.dnd5eapi.co/api/monsters/${searchResult.results.first().index}")
        } catch (ex: ClientRequestException) {
            null
        }
    }

    suspend fun getSpell(spellName: String): Spell? {
        //First search for monsters that contain [monsterName]
        val searchResult: ApiSearchResult =
            client.get("http://www.dnd5eapi.co/api/spells/?name=$spellName")

        searchResult.results?.firstOrNull() ?: return null

        return try {
            client.get("http://www.dnd5eapi.co/api/spells/${searchResult.results.first().index}")
        } catch (ex: ClientRequestException) {
            null
        }
    }

    data class ApiSearchResult(
        val count: Int?,
        val results: List<FoundItem>?
    ) {
        data class FoundItem(
            val index: String?,
            val name: String?,
            val url: String?
        )
    }

    data class Monster(
        val name: String?,
        val size: String?,
        val type: String?,
        val armor_class: Int?,
        val hit_points: Int?,

        val strength: Int?,
        val dexterity: Int?,
        val constitution: Int?,
        val intelligence: Int?,
        val wisdom: Int?,
        val charisma: Int?,

        val speed: Speed?,

        val damage_vulnerabilities: List<String>?,
        val damage_resistances: List<String>?,
        val damage_immunities: List<String>?,
        val condition_immunities: List<Condition>?,

        val special_abilities: List<SpecialAbility>?,

        val actions: List<Action>?
    ) {

        data class Speed(
            val walk: String?,
            val swim: String?
        )

        data class SpecialAbility(
            val name: String?,
            val desc: String?
        )

        data class Condition(val name: String?)

        data class Action(
            val name: String?,
            val desc: String?
        )
    }

    data class Spell(
        val name: String?,
        val desc: List<String>?,
        val higher_level: List<String>?,
        val range: String?,
        val components: List<String>?,
        val material: String?,
        val ritual: Boolean?,
        val duration: String?,
        val concentration: Boolean?,
        val casting_time: String?,
        val level: Int?,
        val attack_type: String?,
        val damage: Damage?,
        val school: School?,
        val classes: List<CharacterClass>?,

        ) {
        data class Damage(
            val damage_type: DamageType?,
            val damage_at_slot_level: DamageInSpellSlot?
        ) {
            data class DamageType(val name: String?)
            data class DamageInSpellSlot(
                val `2`: String?,
                val `3`: String?,
                val `4`: String?,
                val `5`: String?,
                val `6`: String?,
                val `7`: String?,
                val `8`: String?,
                val `9`: String?,
                val `10`: String?,
            ) {
                override fun toString(): String {
                    return buildString {
                        if (`2` != null) append("2: $`2`\n")
                        if (`3` != null) append("3: $`3`\n")
                        if (`4` != null) append("4: $`4`\n")
                        if (`5` != null) append("5: $`5`\n")
                        if (`6` != null) append("6: $`6`\n")
                        if (`7` != null) append("7: $`7`\n")
                        if (`8` != null) append("8: $`8`\n")
                        if (`9` != null) append("9: $`9`\n")
                        if (`10` != null) append("10: $`10`\n")
                    }
                }
            }
        }

        data class School(val name: String?)

        data class CharacterClass(val name: String?)
    }
}
