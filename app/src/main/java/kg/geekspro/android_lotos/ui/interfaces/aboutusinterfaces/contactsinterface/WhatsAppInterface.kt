package kg.geekspro.android_lotos.ui.interfaces.aboutusinterfaces.contactsinterface

import kg.geekspro.android_lotos.models.aboutusmodels.contactsmodels.WhatsAppModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WhatsAppInterface {
    @GET("/api/v1/contact_us/")
    fun getWhatsApp(
    ): Call<List<WhatsAppModel>>
}
