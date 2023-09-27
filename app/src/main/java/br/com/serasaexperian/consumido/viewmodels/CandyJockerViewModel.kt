package br.com.serasaexperian.consumido.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.serasaexperian.consumido.R
import br.com.serasaexperian.consumido.domain.Candy
import br.com.serasaexperian.consumido.domain.CandyJockerStorage
import javax.inject.Inject

class CandyJockerViewModel @Inject constructor(
    val candyJockerStorage: CandyJockerStorage
) : ViewModel() {

    private val _liveBonus: MutableLiveData<Int> = MutableLiveData(candyJockerStorage.readTimes())
    val liveBonus: LiveData<Int> = _liveBonus

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

        Log.d("123123", "Update list is $updatedList")

        _liveElements.postValue(updatedList)
    }

}