package lapresse.nuglif.domain.repositories

import kotlinx.coroutines.flow.SharedFlow

/**
 *  FireworksRepository
 *  Responsible to deal with the trigger of the answer to the Ultimate Question of Life,
 *  the Universe, and Everything"
 *  Emits a boolean when it is triggered
 */
interface FireworksRepository {

    fun setConfetti(value: Boolean)

    fun subscribeToConfetti(): SharedFlow<Boolean>

}