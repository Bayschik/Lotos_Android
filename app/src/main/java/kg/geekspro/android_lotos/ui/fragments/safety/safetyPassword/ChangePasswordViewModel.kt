package kg.geekspro.android_lotos.ui.fragments.safety.safetyPassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kg.geekspro.android_lotos.ui.repositories.reposprofile.Repository
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(private val repository: Repository):ViewModel() {
    fun changePassword(changePassword: ChangePassword):LiveData<Unit>{
        return repository.changePassword(changePassword)
    }

}