package lapresse.nuglif.domain.models.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VisualResponse(
    val id: String,
    val className: String,
    val urlPattern: String
)