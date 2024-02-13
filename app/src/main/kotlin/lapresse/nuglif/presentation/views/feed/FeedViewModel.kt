package lapresse.nuglif.presentation.views.feed

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import lapresse.nuglif.domain.models.ui.ArticleHeadline
import lapresse.nuglif.domain.repositories.FeedRepository
import lapresse.nuglif.domain.repositories.FireworksRepository
import lapresse.nuglif.domain.repositories.SettingsRepository
import lapresse.nuglif.domain.repositories.SortingMethod
import lapresse.nuglif.presentation.navigation.MainDirections
import lapresse.nuglif.presentation.navigation.NavigationManager
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.emitter.Emitter
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val feedRepository: FeedRepository,
    private val settingsRepository: SettingsRepository,
    private val navigationManager: NavigationManager,
    private val fireworksRepository: FireworksRepository
) : ViewModel() {

    // ◣◥◤◢◣◥◤◢◣◥◤◢◣◥◤◢ States ◣◥◤◢◣◥◤◢◣◥◤◢◣◥◤◢

    private val _articleHeadlineStateList = mutableStateListOf<ArticleHeadline>()
    val articleHeadlineStateList: List<ArticleHeadline> = _articleHeadlineStateList

    private val _sortedMethodState = mutableStateOf(SortingMethod.UNSPECIFIED)
    val sortedMethodState: MutableState<SortingMethod> = _sortedMethodState

    private val _channelsState = mutableStateListOf<String>()
    val channels: List<String> = _channelsState

    private val _partyState = mutableStateOf<List<Party>?>(null)
    val partyState: MutableState<List<Party>?> = _partyState

    init {
        val currentSortingMethod = settingsRepository.retrieveSortingMethod()
        _sortedMethodState.value = currentSortingMethod

        viewModelScope.launch {
            feedRepository.subscribeToArticles().collectLatest { articles ->
                _articleHeadlineStateList.clear()
                articles.map {
                    _articleHeadlineStateList.add(
                        ArticleHeadline(
                            id = it.id,
                            title = it.title,
                            channelName = it.channelName,
                            publicationDate = it.publicationDate
                        )
                    )
                }
                Log.d(TAG, "Feed = ${_articleHeadlineStateList.toList()}")
            }
        }

        viewModelScope.launch {
            feedRepository.subscribeToChannels().collectLatest {
                _channelsState.clear()
                _channelsState.addAll(it)
            }
        }

        viewModelScope.launch {
            fireworksRepository.subscribeToConfetti().collectLatest { answerOfLife ->
                if (answerOfLife) {
                    val yolo = Party(
                        speed = 0f,
                        maxSpeed = 30f,
                        damping = 0.9f,
                        spread = 360,
                        colors = listOf(0xfce18a, 0xff726d, 0xf4306d, 0xb48def),
                        emitter = Emitter(duration = 100, TimeUnit.MILLISECONDS).max(100),
                        position = Position.Relative(0.5, 0.3)
                    )
                    _partyState.value = listOf(yolo)
                } else {
                    _partyState.value = null
                }
            }
        }
    }

    // ◣◥◤◢◣◥◤◢◣◥◤◢◣◥◤◢ Actions ◣◥◤◢◣◥◤◢◣◥◤◢◣◥◤◢

    fun onItemClicked(id: String) {
        feedRepository.setSelectedItem(id)
        navigateToDetails()
    }

    fun onSortByDateClicked() {
        _sortedMethodState.value = SortingMethod.BY_DATE
        settingsRepository.saveSortingMethod(SortingMethod.BY_DATE)
        feedRepository.sortByDate()
    }

    fun onSortByChannelClicked() {
        _sortedMethodState.value = SortingMethod.BY_CHANNEL
        settingsRepository.saveSortingMethod(SortingMethod.BY_CHANNEL)
        feedRepository.sortByChannel()
    }

    fun onChannelClicked(channel: String) {
        feedRepository.filterByChannel(channel)
    }

    private fun navigateToDetails() {
        navigationManager.navigate(MainDirections.details)
    }

    companion object {
        const val TAG = "FeedViewModel"
    }
}