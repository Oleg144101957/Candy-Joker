package br.com.serasaexperian.consumido.domain

interface S {


    //Launch times
    fun readTimes() : Int
    fun increaseLauncherCounter()

    //User Name
    fun readGamerName() : String
    fun saveGamerName(name: String)

    fun savePolicyDestination(policy: String)
    fun readPolicyDestination() : String

    fun saveAppVersion(appVersion: String)
    fun readAppVersion() : String

    fun saveRateUs(rateUs: Boolean)
    fun readRateUs(): Boolean

}