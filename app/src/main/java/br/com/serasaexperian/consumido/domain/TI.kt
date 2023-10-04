package br.com.serasaexperian.consumido.domain

import javax.inject.Inject

class TI @Inject constructor (private val ca: S) {
    //increaser
    fun iT(){
        ca.increaseLauncherCounter()
    }
}