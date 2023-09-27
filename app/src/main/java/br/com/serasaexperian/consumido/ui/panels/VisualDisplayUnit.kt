package br.com.serasaexperian.consumido.ui.panels

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.serasaexperian.consumido.ui.theme.PanelsRoutes
import br.com.serasaexperian.consumido.viewmodels.CandyJockerViewModel


@Composable
fun VisualDisplayUnit(candyJockerViewModel: CandyJockerViewModel){

    val navigationConsole = rememberNavController()

    NavHost(navController = navigationConsole, startDestination = PanelsRoutes.ProcessingPanelRoute.direction){

        composable(route = PanelsRoutes.ProcessingPanelRoute.direction){
            ProcessingPanel(navigationConsole)
        }

        composable(route = PanelsRoutes.VisualMenuPanelRoute.direction){
            VisualMenuPanel(navigationConsole, candyJockerViewModel)
        }

        composable(route = PanelsRoutes.VisualSettingsPanelRoute.direction){
            VisualSettingsPanel(navigationConsole)
        }

        composable(route = PanelsRoutes.VisualPlaygroundPanelRoute.direction){
            VisualPlaygroundPanel(navigationConsole, candyJockerViewModel)
        }
    }
}