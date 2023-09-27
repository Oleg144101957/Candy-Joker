package br.com.serasaexperian.consumido.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.serasaexperian.consumido.domain.CandyJockerStorage
import javax.inject.Inject

class CandyJockerViewModel @Inject constructor(
    private val candyJockerStorage: CandyJockerStorage
) : ViewModel() {

    private val _liveBonus: MutableLiveData<Int> = MutableLiveData(candyJockerStorage.readTimes())
    val liveBonus: LiveData<Int> = _liveBonus

}