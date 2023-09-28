package br.com.serasaexperian.consumido.ui.panels

import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.BackHandler
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import br.com.serasaexperian.consumido.R
import br.com.serasaexperian.consumido.ui.theme.PanelsRoutes
import br.com.serasaexperian.consumido.ui.theme.WhiteJoker
import br.com.serasaexperian.consumido.viewmodels.CandyJockerViewModel
import kotlin.system.exitProcess


@Composable
fun VisualMenuPanel(
    navigationConsole: NavHostController,
    candyJockerViewModel: CandyJockerViewModel
) {

    val context = LocalContext.current
    val bonuses = candyJockerViewModel.liveBonus.value ?: 0
    val buttonsOffsetY = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        buttonsOffsetY.animateTo(
            targetValue = 128f, tween(
                durationMillis = 300,
                delayMillis = 500,
                easing = FastOutLinearInEasing
            )
        )
    }

    Image(
        painter = painterResource(id = R.drawable.bg),
        contentDescription = "Visual Menu Panel Background",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.FillBounds
    )

    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(id = R.drawable.decorgirl),
            contentDescription = "Girl For Decoration",
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Bonuses(bonus = bonuses)

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = buttonsOffsetY.value.dp)
        ) {
            MenuButton(Buttons.ButtonStart, navigationConsole)
            MenuButton(Buttons.ButtonSettings, navigationConsole)
            MenuButton(Buttons.ButtonExit, navigationConsole)
        }
    }

    BackHandler {
        //do nothing
        Toast.makeText(context, "Press exit button", Toast.LENGTH_SHORT).show()
    }

}

@Composable
fun MenuButton(button: Buttons, navigationConsole: NavHostController) {

    val context = LocalContext.current

    Box(modifier = Modifier.padding(8.dp)) {
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

                        is Buttons.ButtonSave -> {
                            navigationConsole.navigate(PanelsRoutes.VisualMenuPanelRoute.direction)
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


@Composable
fun BoxScope.Bonuses(bonus: Int) {
    Text(
        text = "Your bonus: $bonus",
        fontFamily = PanelsRoutes.candyFont,
        color = WhiteJoker,
        modifier = Modifier
            .padding(8.dp)
            .align(Alignment.TopStart)
    )

}

sealed class Buttons(val buttonText: String) {
    object ButtonStart : Buttons("Start")
    object ButtonSettings : Buttons("Settings")
    object ButtonExit : Buttons("Exit")
    object ButtonSave : Buttons("Save")

}
