package br.com.serasaexperian.consumido.ui.panels

import android.content.Intent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import br.com.serasaexperian.consumido.NoConnectionActivity
import br.com.serasaexperian.consumido.R
import br.com.serasaexperian.consumido.data.CandyJockerStorageImpl
import br.com.serasaexperian.consumido.domain.GeneralDataManager
import br.com.serasaexperian.consumido.ui.theme.PanelsRoutes
import br.com.serasaexperian.consumido.ui.theme.WhiteJoker
import br.com.serasaexperian.consumido.viewmodels.CandyJockerViewModel
import kotlinx.coroutines.delay


@Composable
fun ProcessingPanel(navigationConsole: NavHostController, candyJockerViewModel: CandyJockerViewModel) {

    val percents = remember { mutableIntStateOf(0) }
    val configuration = LocalConfiguration.current
    val screenHeight = (configuration.screenHeightDp * 2).toFloat()
    val gameElements = candyJockerViewModel.liveElements.observeAsState()


    val animRadius = remember { Animatable(screenHeight) }

    LaunchedEffect("percents") {
        delay(1000)
        while (percents.value < 100) {
            delay(80)
            percents.value = percents.value + 1

            //before release set ON
            if (percents.value == 100 && gameElements.value?.get(6)?.description == GeneralDataManager.ON) {
                navigationConsole.navigate(PanelsRoutes.VisualMenuPanelRoute.direction)
            }
        }
    }

    LaunchedEffect(Unit) {
        animRadius.animateTo(
            targetValue = 0f,
            animationSpec = tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            )
        )
    }

    Image(
        painter = painterResource(id = R.drawable.bg),
        contentDescription = "Processing Panel Background",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.FillBounds
    )

    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(id = R.drawable.startbackgroundforthetext),
            contentDescription = "startbackgroundforthetext",
            modifier = Modifier
                .align(Alignment.Center)
        )

        Text(
            text = "${percents.value}%",
            color = WhiteJoker,
            fontFamily = PanelsRoutes.candyFont,
            fontSize = 48.sp,
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = (-16).dp)
        )

        Text(
            text = "Processing...",
            color = WhiteJoker,
            fontFamily = PanelsRoutes.candyFont,
            fontSize = 32.sp,
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = 32.dp)
        )

        Canvas(modifier = Modifier.fillMaxSize(), onDraw = {
            drawCircle(
                color = WhiteJoker,
                radius = animRadius.value,
                center = center
            )
        })
    }
}