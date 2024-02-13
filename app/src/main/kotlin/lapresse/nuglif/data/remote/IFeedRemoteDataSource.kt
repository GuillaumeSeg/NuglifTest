package lapresse.nuglif.data.remote

import lapresse.nuglif.domain.models.response.ArticleResponse

interface IFeedRemoteDataSource {
    fun fetchFeed(): List<ArticleResponse>
}