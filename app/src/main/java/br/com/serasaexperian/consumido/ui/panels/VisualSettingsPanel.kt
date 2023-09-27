package br.com.serasaexperian.consumido.ui.panels

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import br.com.serasaexperian.consumido.R
import br.com.serasaexperian.consumido.ui.theme.PanelsRoutes
import br.com.serasaexperian.consumido.ui.theme.WhiteJoker
import br.com.serasaexperian.consumido.viewmodels.CandyJockerViewModel


@Composable
fun VisualSettingsPanel(navigationConsole: NavHostController, candyJockerViewModel: CandyJockerViewModel){

    Image(
        painter = painterResource(id = R.drawable.bg),
        contentDescription = "Visual Settings Panel Background",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.FillBounds
    )


    Box(modifier = Modifier
        .fillMaxSize()
    ){
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Candy Joker Button Close",
            tint = WhiteJoker,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp)
                .clickable {
                    navigationConsole.navigate(PanelsRoutes.VisualMenuPanelRoute.direction)
                }
        )

        Box(modifier = Modifier
            .align(Alignment.BottomCenter)
            .padding(bottom = 16.dp)
        ){
            MenuButton(Buttons.ButtonSave, navigationConsole)
        }

    }
}