package br.com.serasaexperian.consumido.domain

import android.content.Context
import android.provider.Settings
import com.facebook.applinks.AppLinkData
import com.facebook.internal.AttributionIdentifiers
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class GeneralDataManager @Inject constructor(private val rProvider: RProvider) {
    suspend fun takeData() : String ? {
        return rProvider.provideData()
    }

    fun provideADB(context: Context): String{
        val fsdjkls = Settings.Global.getString(context.contentResolver, Settings.Global.ADB_ENABLED)
        return if (fsdjkls == "0") OFF else ON
    }

    suspend fun provideID(context: Context): String = suspendCoroutine{ cont ->
        AppLinkData.fetchDeferredAppLinkData(context){
            val data = AttributionIdentifiers.getAttributionIdentifiers(context)
            val id = data?.androidAdvertiserId ?: ""
            cont.resume(id)
        }
    }

    companion object{
        const val ON = "on"
        const val OFF = "off"
    }
}