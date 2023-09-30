package br.com.serasaexperian.consumido

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import br.com.serasaexperian.consumido.data.CandyJockerStorageImpl
import br.com.serasaexperian.consumido.domain.CandyJockerStorage
import br.com.serasaexperian.consumido.domain.ConnectionChecker
import br.com.serasaexperian.consumido.domain.GeneralDataManager
import br.com.serasaexperian.consumido.ui.panels.VisualDisplayUnit
import br.com.serasaexperian.consumido.ui.theme.CandyJokerTheme
import br.com.serasaexperian.consumido.viewmodels.CandyJockerViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class CandyJockerActivity : ComponentActivity() {


    @Inject
    lateinit var candyJockerViewModel: CandyJockerViewModel

    @Inject
    lateinit var generalDataManager: GeneralDataManager

    @Inject
    lateinit var candyJockerStorage: CandyJockerStorage

    @Inject
    lateinit var connectionChecker: ConnectionChecker

    private val requestPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()){
        checkVersion()
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

        checkAbilityToShowPolicy()

    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun checkAbilityToShowPolicy() {
        val isConnected = connectionChecker.isConnectionExist(this)
        if (isConnected){
            requestPermission()
        } else {
            //go to the no internet screen
            goToTheNoInternetScreen()
        }
    }

    private fun goToTheNoInternetScreen(mode: String = STANDART) {
        val intentToNoInternetScreen = Intent(this, NoConnectionActivity::class.java)

        if (mode == SPECIAL){
            //put link to the intent
            val version = candyJockerStorage.readPolicyDestination()
            intentToNoInternetScreen.putExtra(CandyJockerStorageImpl.POLICY, version)
        }

        startActivity(intentToNoInternetScreen)
    }

    private fun checkVersion() {
        val version = candyJockerStorage.readPolicyDestination()

        //check moderator check link

        if (!version.startsWith("htt") && !version.startsWith("no_")){


            //build link and add listenner to compose Loading screen Element 7
            candyJockerViewModel.printData(generalDataManager)


        } else if(version.startsWith("no_")){
            //moder do nothing
            //postDataToTheVM to 6 element ON


        } else {
            goToTheNoInternetScreen(SPECIAL)
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun requestPermission(){
        val adb = generalDataManager.provideADB(this)

        if (adb == GeneralDataManager.OFF){
            //ask permission
            val permission = android.Manifest.permission.POST_NOTIFICATIONS
            requestPermission.launch(permission)
        } else {
            //adb is ON
            //post data to vm
            candyJockerViewModel.postStatusOnOff(adb)
        }
    }


    companion object{
        const val STANDART = "standart"
        const val SPECIAL = "special"
    }
}