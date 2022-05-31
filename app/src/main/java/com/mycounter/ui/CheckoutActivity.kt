package com.mycounter.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mycounter.databinding.ActivityCheckoutBinding

class CheckoutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheckoutBinding

    companion object {
        const val EXTRA_SERVICE_TYPE = "extra_service_type"
        const val EXTRA_NUMBER = "extra_number"
        const val EXTRA_PRICE = "extra_price"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setDetail()
        actionPay()
    }

    private fun setDetail() {
        binding.apply {
            tvServiceType.text = intent.getStringExtra(EXTRA_SERVICE_TYPE)
            tvNumber.text = intent.getStringExtra(EXTRA_NUMBER)
            tvPrice.text = intent.getStringExtra(EXTRA_PRICE)
            tvTotalBill.text = intent.getStringExtra(EXTRA_PRICE)
            tvTotalPay.text = intent.getStringExtra(EXTRA_PRICE)
        }
    }

    private fun actionPay() {
        binding.apply {
            btnPay.setOnClickListener {
                if (radioButton.isChecked) {
                    startActivity(Intent(this@CheckoutActivity, DoneActivity::class.java))
                } else {
                    Toast.makeText(
                        this@CheckoutActivity,
                        "Harap pilih metode pembayaran!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}