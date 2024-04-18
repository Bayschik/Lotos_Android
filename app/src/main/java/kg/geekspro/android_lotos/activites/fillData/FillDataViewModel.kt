package kg.geekspro.android_lotos.activites.fillData

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import kg.geekspro.android_lotos.models.orderHistoryModel.PersonalData
import kg.geekspro.android_lotos.ui.repositories.repoProfile.Repository

class FillDataViewModel:ViewModel() {
    private val repository = kg.geekspro.android_lotos.ui.repositories.repoProfile.Repository()
    fun clientCreate(data: PersonalData):LiveData<String>{
        return repository.clientCreate(data)
    }
}