package lapresse.nuglif.domain.repositories

import kotlinx.coroutines.flow.SharedFlow

interface FireworksRepository {

    fun setConfetti(value: Boolean)

    fun subscribeToConfetti(): SharedFlow<Boolean>

}