package br.com.serasaexperian.consumido.domain

interface CandyJockerStorage {


    //Launch times
    fun readTimes() : Int
    fun increaseLauncherCounter(times: Int)

    //User Name
    fun readGamerName() : String
    fun saveGamerName(name: String)


}