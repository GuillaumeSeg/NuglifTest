package lapresse.nuglif.domain.models.ui

data class ArticleDetails(
    val title: String,
    val channelName: String,
    val textContent: String,
    val urlImg: String?,
    val urlWebsite: String,
    val publicationDate: String,
    val modificationDate: String
)
