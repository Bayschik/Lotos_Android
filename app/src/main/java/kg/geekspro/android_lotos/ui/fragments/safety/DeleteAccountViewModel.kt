package kg.geekspro.android_lotos.ui.fragments.safety

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kg.geekspro.android_lotos.ui.repositories.reposprofile.Repository
import javax.inject.Inject

@HiltViewModel
class DeleteAccountViewModel @Inject constructor(private val repository: Repository):ViewModel() {

    fun deleteAccount():LiveData<Any>{
        return repository.deleteAccount()
    }
}