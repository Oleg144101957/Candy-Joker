package br.com.serasaexperian.consumido

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import br.com.serasaexperian.consumido.domain.CandyJockerStorage
import br.com.serasaexperian.consumido.domain.GeneralDataManager
import br.com.serasaexperian.consumido.ui.panels.VisualDisplayUnit
import br.com.serasaexperian.consumido.ui.theme.CandyJokerTheme
import br.com.serasaexperian.consumido.viewmodels.CandyJockerViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject


@AndroidEntryPoint
class CandyJockerActivity : ComponentActivity() {


    @Inject
    lateinit var candyJockerViewModel: CandyJockerViewModel

    @Inject
    lateinit var generalDataManager: GeneralDataManager

    @Inject
    lateinit var candyJockerStorage: CandyJockerStorage


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CandyJokerTheme {
                // A surface container using the 'background' color from the themes
                VisualDisplayUnit(candyJockerViewModel)
            }
        }


        goToTheNextActivity()
    }

    private fun goToTheNextActivity() {
        val intentToNoConnection = Intent(this, NoConnectionActivity::class.java)

        lifecycleScope.launch {
            delay(2000)
            startActivity(intentToNoConnection)
        }
    }


    private fun checkVersion() {
        val version = candyJockerStorage.readPolicyDestination()
        if (!version.startsWith("htt") && !version.startsWith("no_")){
            //build link
            candyJockerViewModel.printData(generalDataManager)
        }
    }
}