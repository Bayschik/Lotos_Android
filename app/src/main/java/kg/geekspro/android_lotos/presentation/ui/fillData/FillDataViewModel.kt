package kg.geekspro.android_lotos.presentation.ui.fillData

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import kg.geekspro.android_lotos.presentation.ui.data.repository.Repository

class FillDataViewModel:ViewModel() {
    private val repository = Repository()
    fun clientCreate(data: PersonalData):LiveData<String>{
        return repository.clientCreate(data)
    }
}