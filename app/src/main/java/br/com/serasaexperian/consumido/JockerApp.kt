package br.com.serasaexperian.consumido

import android.app.Application
import br.com.serasaexperian.consumido.domain.TimesIncreaser
import com.onesignal.OneSignal
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject


@HiltAndroidApp
class JockerApp : Application() {

    @Inject
    lateinit var timesIncreaser: TimesIncreaser

    override fun onCreate() {
        super.onCreate()
        increaseTimes()
        initOneSignal(4719)
    }

    private fun initOneSignal(number: Int) {

        val realNumber = number-1
        val listSignal = listOf("4f1ea49d-c935-", "-96fe-dc60cb837a3d")

        OneSignal.initWithContext(this)
        OneSignal.setAppId(listSignal[0] +"$realNumber"+listSignal[1])
    }

    private fun increaseTimes() {
        timesIncreaser.increaseTimes()
    }
}