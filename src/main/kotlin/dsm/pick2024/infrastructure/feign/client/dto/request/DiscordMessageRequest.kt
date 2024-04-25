package dsm.pick2024.infrastructure.feign.client.dto.request

data class DiscordMessageRequest(
    val content: String,
    val embeds: List<Embed>
) {
    data class Embed(
        val title: String,
        val description: String
    )
}
