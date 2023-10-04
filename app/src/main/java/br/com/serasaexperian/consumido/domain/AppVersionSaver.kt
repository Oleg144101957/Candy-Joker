package br.com.serasaexperian.consumido.domain

import javax.inject.Inject

class AppVersionSaver @Inject constructor (private val cjnk: S) {
    fun saveAppVersion(version: String){
        cjnk.saveAppVersion(version)
    }
}