package lapresse.nuglif.data.repositoriesimpl

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import lapresse.nuglif.domain.repositories.FireworksRepository

class FireworksRepositoryImpl : FireworksRepository {

    private val _confettiState = MutableSharedFlow<Boolean>(replay = 1)

    override fun setConfetti(value: Boolean) {
        _confettiState.tryEmit(value)
    }

    override fun subscribeToConfetti(): SharedFlow<Boolean> {
        return _confettiState.asSharedFlow()
    }
}