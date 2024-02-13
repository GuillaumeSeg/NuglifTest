package lapresse.nuglif.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.collectAsState
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import lapresse.nuglif.domain.repositories.FeedRepository
import lapresse.nuglif.presentation.navigation.AppNavigation
import lapresse.nuglif.presentation.navigation.MainDirections
import lapresse.nuglif.presentation.navigation.NavigationManager
import lapresse.nuglif.presentation.theme.NugTheme
import lapresse.nuglif.tools.ConfusingRandomLogger
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var feedRepository: FeedRepository
    @Inject
    lateinit var navigationManager: NavigationManager
    @Inject
    lateinit var confusingRandomLogger: ConfusingRandomLogger

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        CoroutineScope(Dispatchers.IO).launch {
            feedRepository.fetchFeed()
        }

        setContent {
            NugTheme(isDarkTheme = false) {
                val navController = rememberNavController()
                navigationManager.initNavMan(navController)
                AppNavigation(navController = navController, startDest = MainDirections.feed)

                navigationManager.commands.collectAsState().value.also { command ->
                    if (command.destination.isNotEmpty()) {
                        navController.navigate(command.destination)
                    }
                }
            }
        }

        confusingRandomLogger.startLogging()
    }

    override fun onDestroy() {
        confusingRandomLogger.stopLogging()
        super.onDestroy()
    }
}
