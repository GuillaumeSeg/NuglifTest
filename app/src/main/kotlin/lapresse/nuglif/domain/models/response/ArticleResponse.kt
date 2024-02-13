package lapresse.nuglif.domain.models.response

import com.squareup.moshi.JsonClass
import java.time.LocalDateTime

@JsonClass(generateAdapter = true)
data class ArticleResponse(
    val id: String,
    val channelName: String,
    val title: String,
    val lead: String,
    val type: String,
    val visual: List<VisualResponse>,
    val dataUrl: String,
    val modificationDate: LocalDateTime,
    val publicationDate: LocalDateTime,
    val sharedId: String,
    val className: String
)