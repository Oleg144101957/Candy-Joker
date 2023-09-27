package br.com.serasaexperian.consumido

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import br.com.serasaexperian.consumido.ui.panels.VisualDisplayUnit
import br.com.serasaexperian.consumido.ui.theme.CandyJokerTheme
import br.com.serasaexperian.consumido.viewmodels.CandyJockerViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class CandyJockerActivity : ComponentActivity() {


    @Inject
    lateinit var candyJockerViewModel: CandyJockerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CandyJokerTheme {
                // A surface container using the 'background' color from the themes
                VisualDisplayUnit(candyJockerViewModel)
            }
        }
    }
}