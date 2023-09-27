package br.com.serasaexperian.consumido.domain

import javax.inject.Inject

class TimesIncreaser @Inject constructor (private val candyJockerStorage: CandyJockerStorage) {
    //increaser
    fun increaseTimes(){
        candyJockerStorage.increaseLauncherCounter()
    }
}