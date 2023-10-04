package br.com.serasaexperian.consumido.data

import android.content.Context
import android.os.DeadObjectException
import android.util.Log
import br.com.serasaexperian.consumido.domain.RP
import com.android.installreferrer.api.InstallReferrerClient
import com.android.installreferrer.api.InstallReferrerStateListener
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class RImpl(private val context: Context) : RP {
    override suspend fun provideData(): String? = suspendCoroutine{ cont ->
        val d = InstallReferrerClient.newBuilder(context).build()

        d.startConnection(object : InstallReferrerStateListener{
            override fun onInstallReferrerSetupFinished(response: Int) {
                if (response == InstallReferrerClient.InstallReferrerResponse.OK){
                    cont.resume(
                        try {
                            val data = d?.installReferrer?.installReferrer
                            d.endConnection()
                            data
                        } catch (e: DeadObjectException){
                            null
                        }
                    )
                } else {
                    cont.resume(null)
                }
            }

            override fun onInstallReferrerServiceDisconnected() {
                Log.d("debug", "onInstallReferrerServiceDisconnected in provideData method")
            }
        })
    }
}