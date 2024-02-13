package lapresse.nuglif.domain.models

import java.time.LocalDateTime

data class Article(
    val id: String,
    val title: String,
    val channelName: String,
    val textContent: String,
    val publicationDate: LocalDateTime,
    val modificationDate: LocalDateTime,
    val urlWebsite: String,
    val urlImage: String?,
)