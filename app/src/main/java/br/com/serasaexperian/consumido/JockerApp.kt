package br.com.serasaexperian.consumido

import android.app.Application
import br.com.serasaexperian.consumido.domain.TimesIncreaser
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject


@HiltAndroidApp
class JockerApp : Application() {

    @Inject
    lateinit var timesIncreaser: TimesIncreaser

    override fun onCreate() {
        super.onCreate()
        increaseTimes()
    }

    private fun increaseTimes() {
        timesIncreaser.increaseTimes()
    }
}