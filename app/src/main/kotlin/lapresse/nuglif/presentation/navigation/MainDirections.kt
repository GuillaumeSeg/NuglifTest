package lapresse.nuglif.presentation.navigation

object MainDirections {

    val root = object : NavigationCommand {
        override val destination = "app-root"
    }

    val Default = object : NavigationCommand {
        override val destination = ""
    }

    val feed = object : NavigationCommand {
        override val destination = "app-feed"
    }

    val details = object : NavigationCommand {
        override val destination = "app-details"
    }

}