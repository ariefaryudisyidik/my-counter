package com.mycounter.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Price(
    var pulsePrice: Int,
    var paidPrice: Int
) : Parcelable