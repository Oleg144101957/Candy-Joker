package br.com.serasaexperian.consumido.ui.panels

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import br.com.serasaexperian.consumido.R


@Composable
fun VisualSettingsPanel(navigationConsole: NavHostController){

    Image(
        painter = painterResource(id = R.drawable.bg),
        contentDescription = "Visual Settings Panel Background",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.FillBounds
    )




}