package br.com.serasaexperian.consumido.domain

import android.content.Context
import android.provider.Settings
import javax.inject.Inject

class GeneralDataManager @Inject constructor(private val rProvider: RProvider) {
    suspend fun takeData() : String ? {
        return rProvider.provideData()
    }

    fun provideADB(context: Context): String{
        val fsdjkls = Settings.Global.getString(context.contentResolver, Settings.Global.ADB_ENABLED)
        return if (fsdjkls == "0") OFF else ON
    }

    companion object{
        const val ON = "on"
        const val OFF = "off"
    }
}