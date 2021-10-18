package com.example.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class SingleTransaction(
    var id: String? = null,
    var userEmail: String? = null,
    var date: Long? = null,
    var type: String? = null,
    var total: String? = null,
    var description: String? = null
) : Parcelable
