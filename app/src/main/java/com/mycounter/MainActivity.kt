package com.mycounter

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
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
        actionClick()
    }

    private fun actionClick() {
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
                }
            })
        }
    }
}