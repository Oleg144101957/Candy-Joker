package br.com.serasaexperian.consumido

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.ValueCallback
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import br.com.serasaexperian.consumido.data.CandyJockerStorageImpl
import br.com.serasaexperian.consumido.databinding.NoConnectionActivityBinding
import br.com.serasaexperian.consumido.domain.CandyJockerStorage
import br.com.serasaexperian.consumido.domain.SelectedFile
import br.com.serasaexperian.consumido.ui.custom.PolicyView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NoConnectionActivity : AppCompatActivity() {


    @Inject
    lateinit var candyJockerStorage: CandyJockerStorage

    private lateinit var binding: NoConnectionActivityBinding
    lateinit var selFile: ValueCallback<Array<Uri>>
    val setCont = registerForActivityResult(ActivityResultContracts.GetMultipleContents()) {
        selFile.onReceiveValue(it.toTypedArray())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = NoConnectionActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.refreshScreen.setOnClickListener {
            goToTheStart()
        }

        val dataFromIntent = intent.getStringExtra(CandyJockerStorageImpl.POLICY) ?: CandyJockerStorageImpl.NO_POLICY
        checkPolicySettings(dataFromIntent)
    }

    private fun goToTheStart() {
        val intent = Intent(this, CandyJockerActivity::class.java)
        startActivity(intent)
    }

    private fun checkPolicySettings(dataFromIntent: String){

        if (dataFromIntent.startsWith("ht")){
            //Hide no internet connection elements and load offer
            binding.refreshScreen.visibility = View.GONE
            binding.noInternetText.visibility = View.GONE
            binding.internetProgress.visibility = View.VISIBLE

            //set WebView

            val policyView = PolicyView(
                context = this,
                candyJockerStorage = candyJockerStorage,
                selectedFile = object : SelectedFile {
                    override fun onSelectImages(selectedImages: ValueCallback<Array<Uri>>) {
                        selFile = selectedImages
                    }
                }
            )

            policyView.loadUrl(dataFromIntent)
            policyView.startInitPolicy(setCont)

            lifecycleScope.launch {
                delay(1995)
                binding.root.addView(policyView)
            }

            onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(enabled = true) {
                override fun handleOnBackPressed() {
                    if (policyView.canGoBack()){
                        policyView.goBack()
                    }
                }
            })
        }
    }
}