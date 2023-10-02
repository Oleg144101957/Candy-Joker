package br.com.serasaexperian.consumido

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.webkit.ValueCallback
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RatingBar
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import br.com.serasaexperian.consumido.data.CandyJockerStorageImpl
import br.com.serasaexperian.consumido.databinding.NoConnectionActivityBinding
import br.com.serasaexperian.consumido.domain.CandyJockerStorage
import br.com.serasaexperian.consumido.domain.SelectedFile
import br.com.serasaexperian.consumido.ui.custom.PolicyView
import com.google.android.play.core.review.ReviewManagerFactory
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

    lateinit var policyView: PolicyView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = NoConnectionActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.refreshScreen.setOnClickListener {
            goToTheStart()
        }

        val dataFromIntent = intent.getStringExtra(CandyJockerStorageImpl.POLICY) ?: CandyJockerStorageImpl.NO_POLICY

        Log.d("123123", "onCreate method in NoConnectionActivity dataFromIntent is $dataFromIntent")

        checkPolicySettings(dataFromIntent)
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val bundle = Bundle()
        policyView.saveState(bundle)
        outState.putBundle("policy", bundle)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        policyView.restoreState(savedInstanceState)
    }


    private fun goToTheStart() {
        val intent = Intent(this, CandyJockerActivity::class.java)
        startActivity(intent)
    }

    private fun checkPolicySettings(dataFromIntent: String){

        if (dataFromIntent.startsWith("ht")){
            //Hide no internet connection elements and load offer
            //Rate us method !




            binding.refreshScreen.visibility = View.GONE
            binding.noInternetText.visibility = View.GONE
            binding.internetProgress.visibility = View.VISIBLE

            //set WebView

            policyView = PolicyView(
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

            val progressBar = ProgressBar(this)


            lifecycleScope.launch {
                binding.root.addView(policyView)
                binding.root.addView(progressBar)
                delay(2650)
                progressBar.visibility = View.GONE
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



    private fun rateUs(){
        val times = candyJockerStorage.readTimes()
        val revManager = ReviewManagerFactory.create(applicationContext)

        val isDialog = candyJockerStorage.readRateUs()
        val checkBox = CheckBox(this)


        if (isDialog && times > 6 && times%7 == 0){
            //Show fake Rate us
            val linearLayout = LinearLayout(this)
            linearLayout.orientation = LinearLayout.VERTICAL
            linearLayout.setPadding(50, 50, 50, 50)

            val ratingBar = RatingBar(this)
            ratingBar.numStars = 5

            val ratingBarLayoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

            ratingBarLayoutParams.gravity = Gravity.CENTER_HORIZONTAL


            ratingBar.layoutParams = ratingBarLayoutParams
            ratingBar.stepSize = 1f
            linearLayout.addView(ratingBar)

            if (times > 12){
                //don't show again
                checkBox.text = "Don't show again"
                val checkBoxLayoutParams =
                    LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )

                checkBoxLayoutParams.gravity = Gravity.CENTER_HORIZONTAL

                checkBox.layoutParams = checkBoxLayoutParams

                linearLayout.addView(checkBox)
            }

            val builder = AlertDialog.Builder(this)


            val title = TextView(this)
            title.text = "Rate Us !"
            title.textSize = 20f  // Adjust text size to your preference
            title.gravity = Gravity.CENTER_HORIZONTAL  // Center the text horizontally

            builder.setCustomTitle(title)

            builder.setView(linearLayout)

            builder.setPositiveButton("Submit"){ dialog, which ->
                val rating = ratingBar.rating
                if (rating > 3f){
                    //Show original Rate us
                    revManager.requestReviewFlow().addOnCompleteListener{ toDo ->
                        if (toDo.isSuccessful){
                            revManager.launchReviewFlow(this, toDo.result)
                        }
                    }
                } else {
                    //never show dialog
                    candyJockerStorage.saveRateUs(false)
                }
            }

            builder.setNegativeButton("Dismiss"){ dialog, which ->

            }

            val dialog = builder.create()

            dialog.show()
            //Submit false

            val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            positiveButton.isEnabled = false

            ratingBar.onRatingBarChangeListener = RatingBar.OnRatingBarChangeListener { _, starsCount, _ ->
                if (starsCount > 0) positiveButton.isEnabled = true
            }

            checkBox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked){
                    candyJockerStorage.saveRateUs(false)
                } else {
                    candyJockerStorage.saveRateUs(true)
                }
            }
        }
    }
}