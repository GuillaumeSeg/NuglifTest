package lapresse.nuglif.tools

import android.os.Handler
import android.os.Looper
import android.util.Log
import lapresse.nuglif.domain.repositories.FireworksRepository
import kotlin.random.Random

class ConfusingRandomLogger(private val fireworksRepository: FireworksRepository) {

    private val handler = Handler(Looper.getMainLooper())
    private val random = Random.Default
    private val delayRange = 500..5000L

    private val logRunnable = object : Runnable {
        override fun run() {
            val randomNumber = random.nextInt(1, 100)
            Log.d("ConfusingRandomLogger", "Random number: $randomNumber")

            if (randomNumber == 42) {
                fireworksRepository.setConfetti(true)
                Log.d("ConfusingRandomLogger", "The answer to the Ultimate Question of Life, the Universe, and Everything")
            } else {
                fireworksRepository.setConfetti(false)
            }

            val randomDelay = random.nextLong(delayRange.first, delayRange.last)
            handler.postDelayed(this, randomDelay)
        }
    }

    fun startLogging() {
        handler.post(logRunnable)
    }

    fun stopLogging() {
        handler.removeCallbacks(logRunnable)
    }
}