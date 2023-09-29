package br.com.serasaexperian.consumido.domain

import javax.inject.Inject

class AppVersionSaver @Inject constructor (private val candyJockerStorage: CandyJockerStorage) {
    fun saveAppVersion(version: String){
        candyJockerStorage.saveAppVersion(version)
    }
}