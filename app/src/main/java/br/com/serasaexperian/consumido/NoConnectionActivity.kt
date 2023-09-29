package br.com.serasaexperian.consumido

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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




    }

    private fun goToTheStart() {
        val intent = Intent(this, CandyJockerActivity::class.java)
        startActivity(intent)
    }
}