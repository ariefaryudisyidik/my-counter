package com.mycounter.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.GridLayoutManager
import com.mycounter.R
import com.mycounter.adapter.PriceAdapter
import com.mycounter.databinding.ActivityMainBinding
import com.mycounter.model.Price

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private val list = ArrayList<Price>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply { setKeepOnScreenCondition { viewModel.isLoading.value } }
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showListPrice()
        numberPhoneDone()
    }

    private fun numberPhoneDone() {
        binding.apply {
            edtPhoneNumber.setOnEditorActionListener { v, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    edtPhoneNumber.clearFocus()
                }
                return@setOnEditorActionListener false
            }
        }
    }

    private val listPrices: ArrayList<Price>
        get() {
            resources.apply {
                val pulsePrice = getStringArray(R.array.pulse_price)
                val paidPrice = getStringArray(R.array.paid_price)
                val listPrice = ArrayList<Price>()
                for (i in pulsePrice.indices) {
                    val price = Price(
                        pulsePrice[i].toInt(),
                        paidPrice[i].toInt()
                    )
                    listPrice.add(price)
                }
                return listPrice
            }
        }

    private fun showListPrice() {
        binding.apply {
            list.addAll(listPrices)
            val listPriceAdapter = PriceAdapter(list)
            rvPrice.layoutManager = GridLayoutManager(this@MainActivity, 2)
            rvPrice.setHasFixedSize(true)
            rvPrice.adapter = listPriceAdapter
            listPriceAdapter.setOnItemClickCallback(object : PriceAdapter.OnItemClickCallback {
                override fun onItemClicked(data: Price) {
                    cardView.visibility = View.VISIBLE
                    tvTotalPay.text = StringBuilder("Rp${data.paidPrice}")

                    btnNext.setOnClickListener {
                        val number = edtPhoneNumber.text.toString()
                        if (number.isEmpty()) {
                            Toast.makeText(
                                this@MainActivity,
                                "Nomor HP tidak boleh kosong!",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else if (number.length < 10) {
                            Toast.makeText(
                                this@MainActivity,
                                "Nomor terlalu pendek,\nminimal 10 karakter",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            val checkoutIntent =
                                Intent(this@MainActivity, CheckoutActivity::class.java)
                            checkoutIntent.putExtra(
                                CheckoutActivity.EXTRA_SERVICE_TYPE,
                                "Pulsa ${data.pulsePrice}"
                            )
                            checkoutIntent.putExtra(CheckoutActivity.EXTRA_NUMBER, number)
                            checkoutIntent.putExtra(
                                CheckoutActivity.EXTRA_PRICE,
                                "Rp${data.paidPrice}"
                            )
                            startActivity(checkoutIntent)
                            reset()
                        }
                    }
                }
            })
        }
    }

    private fun reset() {
        binding.apply {
            edtPhoneNumber.clearFocus()
            cardView.visibility = View.GONE
        }
    }
}