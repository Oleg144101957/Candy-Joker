package br.com.serasaexperian.consumido

import android.app.Application
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import br.com.serasaexperian.consumido.domain.AppVersionSaver
import br.com.serasaexperian.consumido.domain.TimesIncreaser
import com.onesignal.OneSignal
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject


@HiltAndroidApp
class JockerApp : Application() {

    @Inject
    lateinit var timesIncreaser: TimesIncreaser

    @Inject
    lateinit var appVerSaver: AppVersionSaver



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

        saveAppVersion()
    }

    private fun saveAppVersion() {
        val ver = getAppVerData().toString()
        Log.d("123123", "App ver is $ver")
        appVerSaver.saveAppVersion(ver)
    }

    private fun increaseTimes() {
        timesIncreaser.increaseTimes()
    }

    private fun getAppVerData() = try{
        val info = packageInfo(this)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
            info.longVersionCode
        } else {
            info.versionCode.toLong()
        }
    } catch (e: Exception){
        -1
    }

    private fun packageInfo(context: Context): PackageInfo {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.packageManager.getPackageInfo(context.packageName, PackageManager.PackageInfoFlags.of(0))
        } else {
            context.packageManager.getPackageInfo(context.packageName, 0)
        }
    }
}