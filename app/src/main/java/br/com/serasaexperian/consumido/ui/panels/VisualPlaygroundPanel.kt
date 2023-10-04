package br.com.serasaexperian.consumido.ui.panels

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import br.com.serasaexperian.consumido.R
import br.com.serasaexperian.consumido.domain.C
import br.com.serasaexperian.consumido.domain.GameStatus
import br.com.serasaexperian.consumido.ui.theme.BlackJoker
import br.com.serasaexperian.consumido.ui.theme.PanelsRoutes
import br.com.serasaexperian.consumido.ui.theme.WhiteJoker
import br.com.serasaexperian.consumido.viewmodels.CandyJockerViewModel
import kotlinx.coroutines.delay
import kotlin.random.Random


@Composable
fun VisualPlaygroundPanel(
    navigationConsole: NavHostController,
    candyJockerViewModel: CandyJockerViewModel
) {

    LaunchedEffect(Unit) {
        candyJockerViewModel.initListOfElements()
    }

    val gameList = candyJockerViewModel.liveElements.observeAsState()
    val gameScores = candyJockerViewModel.scores.observeAsState()
    val gameBonuses = candyJockerViewModel.liveBonus.observeAsState()
    val isVisibleRuresScreen = remember { mutableStateOf(true) }

    val gameTime = remember { mutableIntStateOf(20) }

    val gameStatus = candyJockerViewModel.status.observeAsState()

    LaunchedEffect(Unit) {
        repeat(20) {
            delay(1000)
            gameTime.intValue = gameTime.intValue - 1
        }
    }

    LaunchedEffect(Unit) {
        delay(2000)
        isVisibleRuresScreen.value = false
    }

    //Playground
    Image(
        painter = painterResource(id = R.drawable.bg),
        contentDescription = "Visual Background for Play Panel ",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.FillBounds
    )


    Box(modifier = Modifier.fillMaxSize()) {

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

        GameDetails(
            gameScores = gameScores.value!!,
            gameBonuses = gameBonuses.value!!,
            time = gameTime.intValue,
            name = candyJockerViewModel.userName.value!!
        )

        gameList.value?.forEach {
            AnimateElements(it, candyJockerViewModel)
        }

        if (isVisibleRuresScreen.value) {
            RulesScreen()
        }

        if (gameScores.value!! < 0) {
            candyJockerViewModel.postStatus(GameStatus.SCORES0)
        } else if (gameTime.value!! <= 0) {
            candyJockerViewModel.postStatus(GameStatus.TIMEISGONE)
        } else if (gameScores.value == 10) {
            candyJockerViewModel.postStatus(GameStatus.WINNER)
        }

        when (gameStatus.value) {
            GameStatus.SCORES0 -> {
                GameOverScreen(
                    msg = "A lot of bombs and fire",
                    navigationConsole = navigationConsole
                )
            }

            GameStatus.TIMEISGONE -> {
                GameOverScreen(msg = "No time, sorry!", navigationConsole = navigationConsole)
            }

            GameStatus.WINNER -> {
                YouWinScreen(
                    msg = "You have took all elements",
                    navigationConsole = navigationConsole
                )
            }

            else -> {}
        }
    }
}


@Composable
fun RulesScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BlackJoker.copy(alpha = 0.7f))
    ) {

        Column(
            modifier = Modifier
                .align(Alignment.Center)
        ) {

            Text(
                text = "Be careful!",
                fontFamily = PanelsRoutes.candyFont,
                color = WhiteJoker,
                fontSize = 18.sp
            )
            Text(
                text = "Don't touch bombs and fire",
                fontFamily = PanelsRoutes.candyFont,
                color = WhiteJoker,
                fontSize = 18.sp
            )
        }
    }
}


@Composable
fun GameOverScreen(msg: String, navigationConsole: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BlackJoker)
    ) {

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

        Column(
            modifier = Modifier
                .align(Alignment.Center)
        ) {

            Text(
                text = "Game Over!",
                fontFamily = PanelsRoutes.candyFont,
                color = WhiteJoker,
                fontSize = 18.sp
            )
            Text(
                text = msg,
                fontFamily = PanelsRoutes.candyFont,
                color = WhiteJoker,
                fontSize = 18.sp
            )
        }
    }
}


@Composable
fun YouWinScreen(msg: String, navigationConsole: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BlackJoker)
    ) {

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

        Column(
            modifier = Modifier
                .align(Alignment.Center)
        ) {

            Text(
                text = "You Win!",
                fontFamily = PanelsRoutes.candyFont,
                color = WhiteJoker,
                fontSize = 18.sp
            )
            Text(
                text = msg,
                fontFamily = PanelsRoutes.candyFont,
                color = WhiteJoker,
                fontSize = 18.sp
            )
        }
    }
}


@Composable
fun BoxScope.GameDetails(gameScores: Int, gameBonuses: Int, time: Int, name: String) {
    Box(
        modifier = Modifier
            .align(Alignment.TopStart)
            .padding(8.dp)

    ) {
        Image(
            painter = painterResource(id = R.drawable.playscenebackground),
            contentDescription = "details background",
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(0.4f)
        )

        Column(
            modifier = Modifier
                .align(Alignment.Center)
        ) {

            Text(
                text = "Hello $name",
                color = WhiteJoker,
                fontSize = 18.sp,
                fontFamily = PanelsRoutes.candyFont
            )

            Text(
                text = "Bonuses is $gameBonuses",
                color = WhiteJoker,
                fontSize = 18.sp,
                fontFamily = PanelsRoutes.candyFont
            )

            Text(
                text = "Scores is $gameScores",
                color = WhiteJoker,
                fontSize = 18.sp,
                fontFamily = PanelsRoutes.candyFont
            )

            Text(
                text = "Time remains $time",
                color = WhiteJoker,
                fontSize = 18.sp,
                fontFamily = PanelsRoutes.candyFont
            )
        }
    }
}

@Composable
fun BoxScope.AnimateElements(candy: C, candyJockerViewModel: CandyJockerViewModel) {

    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp / 2f - 100f
    val screenHeightDp = -(configuration.screenHeightDp - 140f)

    val offsetX = remember {
        Animatable(0f)
    }

    val offsetY = remember {
        Animatable(0f)
    }

    fun getRandomOffsetY(): Float {
        return Random.nextFloat() * screenHeightDp
    }

    fun getRandomOffsetX(): Float {
        return (Random.nextFloat() * screenWidthDp * 2) - screenWidthDp
    }

    LaunchedEffect(Unit) {
        offsetX.animateTo(
            targetValue = getRandomOffsetX(), tween(
                durationMillis = 700,
                delayMillis = 300,
                easing = FastOutLinearInEasing
            )
        )
    }

    LaunchedEffect(Unit) {
        offsetY.animateTo(
            targetValue = getRandomOffsetY(), tween(
                durationMillis = 700,
                delayMillis = 300,
                easing = FastOutLinearInEasing
            )
        )
    }

    if (candy.isVisible) {
        Image(
            painter = painterResource(id = candy.image),
            contentDescription = "element",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .size(48.dp)
                .offset(x = offsetX.value.dp, y = offsetY.value.dp)
                .clickable {
                    candyJockerViewModel.onClickButton(candy.id)
                }
        )
    }
}