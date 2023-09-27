package br.com.serasaexperian.consumido.ui.panels

import android.util.Log
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import br.com.serasaexperian.consumido.R
import br.com.serasaexperian.consumido.domain.Candy
import br.com.serasaexperian.consumido.ui.theme.PanelsRoutes
import br.com.serasaexperian.consumido.ui.theme.WhiteJoker
import br.com.serasaexperian.consumido.viewmodels.CandyJockerViewModel
import kotlin.random.Random


@Composable
fun VisualPlaygroundPanel(navigationConsole: NavHostController, candyJockerViewModel: CandyJockerViewModel){

    val gameBonuses = remember { mutableStateOf(candyJockerViewModel.liveBonus.value) }


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

        candyJockerViewModel.liveElements.value?.forEach {
            if (it.isVisible){
                AnimateElements(it, candyJockerViewModel)
            }
        }
    }
}

@Composable
fun BoxScope.AnimateElements(candy: Candy, candyJockerViewModel: CandyJockerViewModel){

    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp / 2f - 90f
    val screenHeightDp = -(configuration.screenHeightDp - 120f)

    val offsetX = remember {
        Animatable(0f)
    }

    val offsetY = remember {
        Animatable(0f)
    }

    fun getRandomOffsetY() : Float {
        return Random.nextFloat() * screenHeightDp
    }

    fun getRandomOffsetX() : Float {
        return (Random.nextFloat() * screenWidthDp * 2)-screenWidthDp
    }

    LaunchedEffect(Unit){
        offsetX.animateTo(targetValue = getRandomOffsetX(), tween(
            durationMillis = 700,
            delayMillis = 300,
            easing = FastOutLinearInEasing
        ))
    }

    LaunchedEffect(Unit){
        offsetY.animateTo(targetValue = getRandomOffsetY(), tween(
            durationMillis = 700,
            delayMillis = 300,
            easing = FastOutLinearInEasing
        ))
    }

    Image(
        painter = painterResource(id = candy.image),
        contentDescription = "element",
        modifier = Modifier
            .align(Alignment.BottomCenter)
            .offset(x = offsetX.value.dp, y = offsetY.value.dp)
            .clickable {
                candyJockerViewModel.onClickButton(candy.id)
            }
    )
}