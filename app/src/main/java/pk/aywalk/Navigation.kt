package pk.aywalk

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import pk.aywalk.ui.screen.SearchResults
import pk.aywalk.ui.screen.SearchInput
import java.time.LocalDateTime

@Composable
fun Navigation()
{
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.InputScreen.route) {
        composable(Screens.InputScreen.route) {
            SearchInput(navController = navController)
        }
        composable(
            route = Screens.ResultsScreen.route + "/from={from}&to={to}",
            arguments = listOf(
                navArgument(name = "from") {
                    type = NavType.StringType
                },
                navArgument(name = "to") {
                    type = NavType.StringType
                },
                navArgument(name = "time") {
                    type = NavType.StringType
                }
            )) { entry ->
            SearchResults(
                navController = navController,
                from = entry.arguments!!.getString("from")!!,
                to = entry.arguments!!.getString("to")!!,
                time = LocalDateTime.parse(entry.arguments!!.getString("time")!!))
        }

    }
}

sealed class Screens(val route: String) {
    data object InputScreen: Screens("input")
    data object ResultsScreen: Screens("results")
}