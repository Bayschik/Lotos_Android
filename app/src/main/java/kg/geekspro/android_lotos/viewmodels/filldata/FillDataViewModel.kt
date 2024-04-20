package kg.geekspro.android_lotos.viewmodels.filldata

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import kg.geekspro.android_lotos.models.orderhistorymodels.PersonalData

class FillDataViewModel:ViewModel() {
    private val repository = kg.geekspro.android_lotos.ui.repositories.reposprofile.Repository()
    fun clientCreate(data: PersonalData):LiveData<String>{
        return repository.clientCreate(data)
    }
}