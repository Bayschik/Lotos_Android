package kg.geekspro.android_lotos.viewmodels.profileviewmodels.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kg.geekspro.android_lotos.models.profile.Password
import kg.geekspro.android_lotos.ui.repositories.reposprofile.Repository
import javax.inject.Inject

@HiltViewModel
class PasswordCreateViewModel @Inject constructor(private val repository: Repository):ViewModel() {
    fun setPassword(password: Password):LiveData<String>{
        return repository.setPassword(password)
    }
}