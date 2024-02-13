package lapresse.nuglif.data.remote

import android.content.Context
import android.util.Log
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import lapresse.nuglif.R
import lapresse.nuglif.domain.models.response.ArticleResponse
import lapresse.nuglif.tools.LocalDateTimeAdapter

class FeedRemoteDataSource(context: Context) : IFeedRemoteDataSource {

    private val moshi: Moshi = Moshi.Builder().add(LocalDateTimeAdapter()).build()

    private val rawArticles: String?

    init {
        rawArticles = context.resources.openRawResource(R.raw.articles)
            .bufferedReader().use { it.readText() }
    }

    @OptIn(ExperimentalStdlibApi::class)
    override fun fetchFeed(): List<ArticleResponse> {
        return rawArticles?.let { rawArticlesSafe ->
            val adapter: JsonAdapter<List<ArticleResponse>> = moshi.adapter<List<ArticleResponse>>()
            val articles = adapter.fromJson(rawArticlesSafe)
            return articles ?: run { emptyList() }
        } ?: run {
            Log.d(TAG, "raw articles is null / can't be read")
            emptyList()
        }
    }

    companion object {
        const val TAG = "FeedRemoteDataSource"
    }
}
