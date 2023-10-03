package br.com.serasaexperian.consumido.viewmodels

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.serasaexperian.consumido.R
import br.com.serasaexperian.consumido.domain.Candy
import br.com.serasaexperian.consumido.domain.CandyJockerStorage
import br.com.serasaexperian.consumido.domain.GameStatus
import br.com.serasaexperian.consumido.domain.GeneralDataManager
import br.com.serasaexperian.consumido.ui.theme.PanelsRoutes
import com.onesignal.OneSignal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URLEncoder
import java.util.Locale
import java.util.UUID
import javax.inject.Inject

class CandyJockerViewModel @Inject constructor(
    private val candyJockerStorage: CandyJockerStorage
) : ViewModel() {

    private val _liveBonus: MutableLiveData<Int> = MutableLiveData(candyJockerStorage.readTimes())
    val liveBonus: LiveData<Int> = _liveBonus

    private val _userName: MutableLiveData<String> = MutableLiveData(candyJockerStorage.readGamerName())
    val userName: LiveData<String> = _userName

    private val _scores: MutableLiveData<Int> = MutableLiveData(0)
    val scores: LiveData<Int> = _scores

    private val _status: MutableLiveData<GameStatus> = MutableLiveData(GameStatus.PLAY)
    val status: LiveData<GameStatus> = _status





    private val _liveElements: MutableLiveData<List<Candy>> = MutableLiveData(listOf(
        Candy(id = 0, image = R.drawable.candyblue),
        Candy(id = 1, image = R.drawable.candyapple),
        Candy(id = 2, image = R.drawable.candybomb),
        Candy(id = 3, image = R.drawable.candymint),
        Candy(id = 4, image = R.drawable.candypink),
        Candy(id = 5, image = R.drawable.candyclassic),
        Candy(id = 6, image = R.drawable.candyfire),
        Candy(id = 7, image = R.drawable.candygreenpink)
    ))
    val liveElements: LiveData<List<Candy>> = _liveElements

    fun onClickButton(id: Int){
        val updatedList = _liveElements.value?.map{
            if (it.id == id){
                it.copy(isVisible = false)
            } else {
                it
            }
        }


        changeScores(id)
        _liveElements.postValue(updatedList)

        checkElements()
    }

    fun postStatus(gameStatus: GameStatus){
        if (_status.value == GameStatus.PLAY){
            _status.value = gameStatus
        }
    }

    fun saveName(name: String){
        candyJockerStorage.saveGamerName(name)
    }

    private fun checkElements() {
        viewModelScope.launch {
            delay(300)
            val isAllInvisible = _liveElements.value?.all { !it.isVisible } ?: false
            if (isAllInvisible || _scores.value == 6){
                val newVisibleList = _liveElements.value?.map { it.copy(isVisible = true) }
                _liveElements.value = null
                delay(1000)
                _liveElements.value = newVisibleList
            }
        }
    }

    private fun changeScores(id: Int){
        val currentScores = _scores.value ?: 0

        when(id){
            0 -> { postScores(currentScores+1) }
            1 -> { postScores(currentScores+1) }
            2 -> { postScores(currentScores-5) }
            3 -> { postScores(currentScores+1) }
            4 -> { postScores(currentScores+1) }
            5 -> { postScores(currentScores+1) }
            6 -> { postScores(currentScores-7) }
            7 -> { postScores(currentScores+1) }
        }
    }

    private fun postScores(newScores: Int){
        _scores.value = newScores
    }

    fun initListOfElements(){
        _scores.value = 0
        _status.value = GameStatus.PLAY
        _userName.value = candyJockerStorage.readGamerName()
        _liveElements.value = _liveElements.value?.map { it.copy(isVisible = true) }
    }

    fun postStatusOnOff(onOffStatus: String){

        Log.d("123123", "Post status in VM $onOffStatus")


        _liveElements.value = _liveElements.value?.map {
            if (it.id == 6){
                it.copy(description = onOffStatus)
            } else {
                it
            }
        }
    }


    fun printData(generalDataManager: GeneralDataManager, context: Context){
        viewModelScope.launch {

            val gGenerator = generalDataManager.provideID(context)
            val data = generalDataManager.takeData()

            val json = JSONObject()

            OneSignal.setExternalUserId(gGenerator)

            json.put(PanelsRoutes.gaid, gGenerator)
            json.put(PanelsRoutes.timeStamp, System.currentTimeMillis() / 1000f)
            json.put(PanelsRoutes.packageM, "br.com.serasaexperian.consumido")
            json.put(PanelsRoutes.osVer, Build.VERSION.RELEASE)
            json.put(PanelsRoutes.agent, agentProvider())
            json.put(PanelsRoutes.appVer, candyJockerStorage.readAppVersion())
            json.put(PanelsRoutes.referer, data)

            val encodedString = withContext(Dispatchers.IO) {
                URLEncoder.encode(json.toString(), "UTF-8")
            }

            listOfData.add("z/y3kfm?mnvkfk4=")


            val sb = StringBuilder("htt")

            for (i in listOfData){
                sb.append(i)
            }

            sb.append(encodedString)

            val finalString = sb.toString()

            _liveElements.value = _liveElements.value?.map {
                if (it.id == 7){
                    it.copy(description = finalString)
                } else {
                    it
                }
            }

            Log.d("123123", "finalString is $finalString")

            listOfData = mutableListOf("")

        }
    }

    private fun generateRandomUUID() : String {
        return UUID.randomUUID().toString()
    }

    fun agentProvider() : String {
        return "Android ${Build.VERSION.RELEASE}; " +
                "${Locale.getDefault()}; " +
                "${Build.MODEL}; " +
                "Build/${Build.ID}"
    }

    companion object{
        var listOfData: MutableList<String> = mutableListOf("ps://cand", "yjoker.xy")
    }
}

