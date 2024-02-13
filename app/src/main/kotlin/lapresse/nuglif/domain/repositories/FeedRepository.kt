package lapresse.nuglif.domain.repositories

import kotlinx.coroutines.flow.SharedFlow
import lapresse.nuglif.domain.models.Article

/**
 *  FeedRepository
 *  Responsible to deal with the articles data received from the DataSource (json).
 *  Emits the list of 'channels', the list of articles, and the selected article.
 *
 *  The user can filter the articles by a 'channel' name and also sort articles by date or
 *  by 'channel'
 */
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