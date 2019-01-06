package com.apextechies.eretort.model

import com.google.gson.annotations.SerializedName

class FeeTypeModel {
    @SerializedName("id")
    val id: String? = null
    @SerializedName("fee_name")
    val feeType: String? = null
    @SerializedName("fee_amount")
    val feeAmount: String? = null

    @SerializedName("class")
    val className: String? = null
    @SerializedName("school_id")
    val schoolId: String? = null
    @SerializedName("session")
    val sessionName: String? = null
}
