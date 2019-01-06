package com.apextechies.eretort.model

import com.google.gson.annotations.SerializedName
import java.util.*

class FeeDetailsModel {
    @SerializedName("status")
    var status: String? = null
        internal set
    @SerializedName("message")
    var msg: String? = null
        internal set
    @SerializedName("data")
    var data: ArrayList<FeeTypeModel>? = null
        internal set
}
