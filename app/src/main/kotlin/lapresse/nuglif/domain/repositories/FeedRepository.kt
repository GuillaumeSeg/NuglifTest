package lapresse.nuglif.domain.repositories

import kotlinx.coroutines.flow.SharedFlow
import lapresse.nuglif.domain.models.Article

interface FeedRepository {

    fun fetchFeed()

    fun subscribeToArticles(): SharedFlow<List<Article>>

    fun subscribeToSelectedArticle(): SharedFlow<Article>

    fun subscribeToChannels(): SharedFlow<List<String>>

    fun sortByDate()

    fun sortByChannel()

    fun filterByChannel(channel: String)

    fun setSelectedItem(id: String)
}