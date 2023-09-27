package br.com.serasaexperian.consumido.ui.panels

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalViewConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import br.com.serasaexperian.consumido.R
import br.com.serasaexperian.consumido.ui.theme.PanelsRoutes
import br.com.serasaexperian.consumido.ui.theme.WhiteJoker


@Composable
fun VisualPlaygroundPanel(navigationConsole: NavHostController){

    //Playground
    Image(
        painter = painterResource(id = R.drawable.bg),
        contentDescription = "Visual Background for Play Panel ",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.FillBounds
    )


    Box(modifier = Modifier.fillMaxSize()){

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
        
        AnimateElements(image = R.drawable.candyapple)

    }

}


@Composable
fun BoxScope.AnimateElements(image: Int){

    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    val screenHeightDp = configuration.screenHeightDp


    val offsetX = remember {
        Animatable(0f)
    }

    val offsetY = remember {
        Animatable(0f)
    }

    LaunchedEffect(Unit){
        offsetX.animateTo(targetValue = -100f, tween(
            durationMillis = 1000,
            delayMillis = 300,
            easing = FastOutLinearInEasing
        ))
    }

    LaunchedEffect(Unit){
        offsetY.animateTo(targetValue = -screenHeightDp.toFloat(), tween(
            durationMillis = 1000,
            delayMillis = 300,
            easing = FastOutLinearInEasing
        ))
    }

    Image(
        painter = painterResource(id = image),
        contentDescription = "element",
        modifier = Modifier
            .align(Alignment.BottomCenter)
            .offset(x = offsetX.value.dp, y = offsetY.value.dp)
    )
    
}