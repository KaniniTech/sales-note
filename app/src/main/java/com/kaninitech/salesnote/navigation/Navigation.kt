package com.kaninitech.salesnote.navigation






import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import androidx.compose.animation.*
import androidx.compose.ui.Modifier
import com.kaninitech.salesnote.screens.AboutAppScreen
import com.kaninitech.salesnote.screens.AddBusinessScreen
import com.kaninitech.salesnote.screens.HomeScreen
import com.kaninitech.salesnote.screens.ReportsScreen
import com.kaninitech.salesnote.screens.SettingScreen
import com.kaninitech.salesnote.screens.SingleSalesReportScreen


sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Settings : Screen("settings")

    object SingleSalesReport : Screen("singleSalesReport/{itemId}") {
        fun createRoute(itemId: String) = "SingleSalesReport/$itemId"
    }

    object Reports : Screen("reports")


    object  AboutApp : Screen("aboutApp")

    object  AddBrand : Screen("addBrand")
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavHost(navController: NavHostController, modifier: Modifier) {


    AnimatedNavHost(
        navController,
        startDestination = Screen.Home.route,
        enterTransition = { slideInHorizontally(initialOffsetX = { 1000 }) + fadeIn() },
        exitTransition = { slideOutHorizontally(targetOffsetX = { -1000 }) + fadeOut() },
        popEnterTransition = { slideInHorizontally(initialOffsetX = { -1000 }) + fadeIn() },
        popExitTransition = { slideOutHorizontally(targetOffsetX = { 1000 }) + fadeOut() }
    ) {
        composable(Screen.Home.route) { HomeScreen(navController) }
        composable(Screen.Settings.route) { SettingScreen(navController) }
        composable(Screen.Reports.route) { ReportsScreen(navController) }
        composable(Screen.AboutApp.route) {  AboutAppScreen(navController)   }
        composable(Screen.AddBrand.route) { AddBusinessScreen(navController) }
        composable(Screen.SingleSalesReport.route) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getString("itemId") ?: "Unknown"
            SingleSalesReportScreen(navController, itemId)
        }







    }
}