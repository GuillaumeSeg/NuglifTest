package lapresse.nuglif.data.repositoriesimpl

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import lapresse.nuglif.data.remote.IFeedRemoteDataSource
import lapresse.nuglif.domain.models.Article
import lapresse.nuglif.domain.repositories.FeedRepository
import lapresse.nuglif.domain.repositories.SettingsRepository
import lapresse.nuglif.domain.repositories.SortingMethod

class FeedRepositoryImpl(
    private val feedRemoteDataSource: IFeedRemoteDataSource,
    private val settingsRepository: SettingsRepository
) : FeedRepository {

    var allArticles = listOf<Article>()
    val articles = MutableSharedFlow<List<Article>>(replay = 1)

    private val _selectedItem = MutableSharedFlow<Article>(replay = 1)

    private val _mapChannel: MutableMap<String, MutableList<String>> = mutableMapOf()

    internal fun updateMapChannel(newMap: Map<String, MutableList<String>>) {
        _mapChannel.clear()
        _mapChannel.putAll(newMap)
    }

    private val _channels = MutableSharedFlow<List<String>>(replay = 1)

    override fun fetchFeed() {
        // Get articles from the source (json)
        val results = feedRemoteDataSource.fetchFeed()

        val allArticles = mutableListOf<Article>()
        results.map { remoteArticle ->
            // retrieve the visual image of the article
            val visual = if (remoteArticle.visual.isNotEmpty()) {
                remoteArticle.visual[0]
            } else { null }

            allArticles.add(
                // Create and add the articles in a list
                Article(
                    id = remoteArticle.id,
                    title = remoteArticle.title,
                    textContent = remoteArticle.lead,
                    channelName = remoteArticle.channelName,
                    publicationDate = remoteArticle.publicationDate,
                    modificationDate = remoteArticle.modificationDate,
                    urlWebsite = remoteArticle.dataUrl,
                    urlImage = visual?.urlPattern
                )
            )
            this.allArticles = allArticles

            // Create the map of channel and ids
            _mapChannel[remoteArticle.channelName]?.add(remoteArticle.id) ?: run {
                _mapChannel[remoteArticle.channelName] = mutableListOf(remoteArticle.id)
            }
            val channels = _mapChannel.keys.toMutableList()
            channels.add(ALL)
            _channels.tryEmit(channels)
        }
        if (allArticles.isNotEmpty()) {
            // Sort at the beginning according to the choice of the user
            // And emits the results.
            when (settingsRepository.retrieveSortingMethod()) {
                SortingMethod.BY_DATE -> {
                    val newList = allArticles.sortedByDescending { it.publicationDate }
                    articles.tryEmit(newList)
                }
                SortingMethod.BY_CHANNEL -> {
                    val newList = allArticles.sortedBy { it.channelName }
                    articles.tryEmit(newList)
                }
                SortingMethod.UNSPECIFIED -> {
                    articles.tryEmit(allArticles)
                }
            }
        }
    }

    override fun subscribeToArticles(): SharedFlow<List<Article>> {
        return articles.asSharedFlow()
    }

    override fun subscribeToSelectedArticle(): SharedFlow<Article> {
        return _selectedItem.asSharedFlow()
    }

    override fun subscribeToChannels(): SharedFlow<List<String>> {
        return _channels.asSharedFlow()
    }


    override fun sortByDate() {
        if (articles.replayCache.isNotEmpty()) {
            val listToSort = articles.replayCache[0]
            val newList = listToSort.sortedByDescending { it.publicationDate }
            articles.tryEmit(newList)
        }
    }

    override fun sortByChannel() {
        if (articles.replayCache.isNotEmpty()) {
            val listToSort = articles.replayCache[0]
            val newList = listToSort.sortedBy { it.channelName }
            articles.tryEmit(newList)
        }
    }

    override fun filterByChannel(channel: String) {
        if (channel == ALL) {
            // If the user choose ALL, re-emit all articles
            when (settingsRepository.retrieveSortingMethod()) {
                SortingMethod.BY_DATE -> {
                    val newList = allArticles.sortedByDescending { it.publicationDate }
                    articles.tryEmit(newList)
                }
                SortingMethod.BY_CHANNEL -> {
                    val newList = allArticles.sortedBy { it.channelName }
                    articles.tryEmit(newList)
                }
                SortingMethod.UNSPECIFIED -> {
                    articles.tryEmit(allArticles)
                }
            }
        } else {
            // Otherwise filter by a 'channel'
            val listToFilter = allArticles
            val listIdsArticles = _mapChannel[channel]
            val newList = mutableListOf<Article>()
            listIdsArticles?.let {
                listToFilter.map {
                    if (listIdsArticles.contains(it.id)) {
                        newList.add(it)
                    }
                }
            }
            articles.tryEmit(newList)
        }
    }

    override fun setSelectedItem(id: String) {
        if (articles.replayCache.isNotEmpty()) {
            val selectedArticle = articles.replayCache[0].find { it.id == id }
            selectedArticle?.let {
                _selectedItem.tryEmit(selectedArticle)
            }
        }
    }

    companion object {
        const val ALL = "Tous"
    }
}