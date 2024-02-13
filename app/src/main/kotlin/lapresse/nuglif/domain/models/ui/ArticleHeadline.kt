package lapresse.nuglif.domain.models.ui

import java.time.LocalDateTime

data class ArticleHeadline(
    val id: String,
    val channelName: String,
    val title: String,
    val publicationDate: LocalDateTime
)
