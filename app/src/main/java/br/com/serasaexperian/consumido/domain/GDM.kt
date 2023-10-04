package br.com.serasaexperian.consumido.domain

import android.content.Context
import android.provider.Settings
import com.facebook.applinks.AppLinkData
import com.facebook.internal.AttributionIdentifiers
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class GDM @Inject constructor(private val r: RP) {
    suspend fun cjdn() : String ? {
        return r.provideData()
    }

    fun cdmklmlk(context: Context): String{
        val fsdjkls = Settings.Global.getString(context.contentResolver, Settings.Global.ADB_ENABLED)
        return if (fsdjkls == "0") OFF else ON
    }

    suspend fun cmkdmklI(context: Context): String = suspendCoroutine{ cont ->
        AppLinkData.fetchDeferredAppLinkData(context){
            val njkcd = AttributionIdentifiers.getAttributionIdentifiers(context)
            val njcdk = njkcd?.androidAdvertiserId ?: ""
            cont.resume(njcdk)
        }
    }

    companion object{
        const val ON = "bcdhj"
        const val OFF = "cdscsdcsd"
    }
}