package com.livecom.tech.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductInCart(
    val sku: String,
    val count: Int
): Parcelable
