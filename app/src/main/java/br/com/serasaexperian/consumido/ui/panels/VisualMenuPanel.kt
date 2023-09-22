package br.com.serasaexperian.consumido.ui.panels

import android.content.Intent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import br.com.serasaexperian.consumido.R
import br.com.serasaexperian.consumido.ui.theme.PanelsRoutes
import br.com.serasaexperian.consumido.ui.theme.WhiteJoker
import kotlin.system.exitProcess


@Composable
fun VisualMenuPanel(navigationConsole: NavHostController){


    val buttonsOffsetY = remember { Animatable(0f) }

    LaunchedEffect(Unit){
        buttonsOffsetY.animateTo(targetValue = 128f, tween(
            durationMillis = 300,
            delayMillis = 500,
            easing = FastOutLinearInEasing
        ))
    }

    Image(
        painter = painterResource(id = R.drawable.bg),
        contentDescription = "Visual Menu Panel Background",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.FillBounds
    )

    Box(modifier = Modifier.fillMaxSize()){

        Image(
            painter = painterResource(id = R.drawable.decorgirl),
            contentDescription = "Girl For Decoration",
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Column(modifier = Modifier
            .align(Alignment.Center)
            .offset(y = buttonsOffsetY.value.dp)
        ){

            MenuButton(Buttons.ButtonStart, navigationConsole)
            MenuButton(Buttons.ButtonSettings, navigationConsole)
            MenuButton(Buttons.ButtonExit, navigationConsole)

        }
    }
}

@Composable
fun MenuButton(button: Buttons, navigationConsole: NavHostController){

    val context = LocalContext.current

    Box(modifier = Modifier.padding(8.dp)){
        Image(
            painter = painterResource(id = R.drawable.scoresandbuttonbackground),
            contentDescription = "button ${button.buttonText}",
            modifier = Modifier
                .align(Alignment.Center)
                .clickable {
                    when (button) {
                        is Buttons.ButtonStart -> {
                            navigationConsole.navigate(PanelsRoutes.VisualPlaygroundPanelRoute.direction)
                        }

                        is Buttons.ButtonSettings -> {
                            navigationConsole.navigate(PanelsRoutes.VisualSettingsPanelRoute.direction)
                        }

                        is Buttons.ButtonExit -> {
                            val intent = Intent(Intent.ACTION_MAIN)
                            intent.addCategory(Intent.CATEGORY_HOME)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                            context.startActivity(intent)
                            exitProcess(0)
                        }
                    }
                }
        )

        Text(
            text = button.buttonText,
            color = WhiteJoker,
            fontFamily = PanelsRoutes.candyFont,
            fontSize = 24.sp,
            modifier = Modifier.align(Alignment.Center)
        )
    }

}

sealed class Buttons(val buttonText: String){
    object ButtonStart : Buttons("Start")
    object ButtonSettings : Buttons("Settings")
    object ButtonExit : Buttons("Exit")

}

@Preview
@Composable
fun MenuPrev(){
    val nav = rememberNavController()
    VisualMenuPanel(navigationConsole = nav)
}