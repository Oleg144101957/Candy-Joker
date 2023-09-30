package br.com.serasaexperian.consumido

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import br.com.serasaexperian.consumido.data.CandyJockerStorageImpl
import br.com.serasaexperian.consumido.databinding.NoConnectionActivityBinding

class NoConnectionActivity : AppCompatActivity() {

    private lateinit var binding: NoConnectionActivityBinding

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
            binding.internetProgress.visibility = View.VISIBLE

            //set WebView

        }
    }
}