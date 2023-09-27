package br.com.serasaexperian.consumido.data

import android.content.Context
import br.com.serasaexperian.consumido.domain.CandyJockerStorage

class CandyJockerStorageImpl(context: Context) : CandyJockerStorage{

    private val sharedPreferences = context.getSharedPreferences(JOCKER_STORAGE, Context.MODE_PRIVATE)
    override fun readTimes(): Int {
        return sharedPreferences.getInt(LAUNCH_TIMES, 0)
    }

    override fun increaseLauncherCounter() {
        val lastCounterValue = readTimes()
        sharedPreferences.edit().putInt(LAUNCH_TIMES, lastCounterValue+1).apply()
    }

    override fun readGamerName(): String {
        return sharedPreferences.getString(GAMER_NAME, DEFAULT_NAME) ?: DEFAULT_NAME
    }

    override fun saveGamerName(name: String) {
        sharedPreferences.edit().putString(GAMER_NAME, name).apply()
    }

    companion object{
        const val JOCKER_STORAGE = "JOCKER_STORAGE"
        const val LAUNCH_TIMES = "LAUNCH_TIMES"
        const val GAMER_NAME = "GAMER_NAME"
        const val DEFAULT_NAME = " gamer"
    }
}