package dsm.pick2024.global.discord

data class DiscordMessage(
    val content: String,
    val embeds: List<Embed>
) {
    data class Embed(
        val title: String,
        val description: String
    )
}
