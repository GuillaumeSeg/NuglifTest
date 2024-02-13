package lapresse.nuglif.presentation.views.details

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import lapresse.nuglif.domain.models.ui.ArticleDetails
import lapresse.nuglif.domain.repositories.FeedRepository
import lapresse.nuglif.presentation.navigation.NavigationManager
import java.time.LocalDateTime
import java.time.Month
import java.time.format.DateTimeFormatter
import javax.inject.Inject


@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val feedRepository: FeedRepository,
    private val navigationManager: NavigationManager
) : ViewModel() {

    // ◣◥◤◢◣◥◤◢◣◥◤◢◣◥◤◢ States ◣◥◤◢◣◥◤◢◣◥◤◢◣◥◤◢

    private val _articleDetailsState = mutableStateOf<ArticleDetails?>(null)
    val articleDetailsState: MutableState<ArticleDetails?> = _articleDetailsState

    init {
        viewModelScope.launch {
            feedRepository.subscribeToSelectedArticle().collectLatest { article ->
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
                val formattedPublicationDate = article.publicationDate.format(formatter)
                val formattedModificationDate = article.modificationDate.format(formatter)

                val articleUi = ArticleDetails(
                    title = article.title,
                    channelName = article.channelName,
                    textContent = article.textContent,
                    publicationDate = formattedPublicationDate,
                    modificationDate = formattedModificationDate,
                    urlWebsite = article.urlWebsite,
                    urlImg = article.urlImage
                )
                _articleDetailsState.value = articleUi
                Log.d(TAG, "$articleUi")
            }
        }
    }

    // ◣◥◤◢◣◥◤◢◣◥◤◢◣◥◤◢ Actions ◣◥◤◢◣◥◤◢◣◥◤◢◣◥◤◢

    fun onBackPressed() {
        navigationManager.popBack()
    }

    companion object {
        const val TAG = "DetailsViewModel"
    }
}