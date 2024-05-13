package kg.geekspro.android_lotos.viewmodels.filldata

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kg.geekspro.android_lotos.models.orderhistorymodels.PersonalData
import kg.geekspro.android_lotos.ui.repositories.reposprofile.Repository
import javax.inject.Inject

@HiltViewModel
class FillDataViewModel @Inject constructor(private val repository: Repository):ViewModel() {
    fun clientCreate(data: PersonalData):LiveData<String>{
        return repository.clientCreate(data)
    }
}