package kg.geekspro.android_lotos.presentation.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import kg.geekspro.android_lotos.presentation.ui.data.repository.Repository

class ProfileViewModel:ViewModel() {
    private val repository = Repository()

    fun getProfile():LiveData<Profile>{
        return repository.getProfile()
    }
}