package lapresse.nuglif.presentation.navigation

import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.MutableStateFlow
import lapresse.nuglif.presentation.navigation.MainDirections.Default

class NavigationManager {

    private lateinit var navController: NavHostController
    var commands = MutableStateFlow(Default)

    fun initNavMan(navHostController: NavHostController) {
        navController = navHostController
    }

    fun popBack(): Boolean {
        clear()
        return try {
            navController.popBackStack()
        } catch (ex: UninitializedPropertyAccessException) {
            false
        }
    }

    fun navigate(direction: NavigationCommand) {
        commands.value = direction
    }

    private fun clear() {
        commands.tryEmit(Default)
    }

}