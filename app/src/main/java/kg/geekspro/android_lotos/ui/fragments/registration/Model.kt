package kg.geekspro.android_lotos.ui.fragments.registration


import com.google.gson.annotations.SerializedName


data class Model(
    @SerializedName("email")
    val email: List<String>
)