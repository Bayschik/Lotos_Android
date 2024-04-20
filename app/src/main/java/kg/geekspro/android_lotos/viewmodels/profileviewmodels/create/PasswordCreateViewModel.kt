package kg.geekspro.android_lotos.viewmodels.profileviewmodels.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import kg.geekspro.android_lotos.models.profile.Password

class PasswordCreateViewModel:ViewModel() {
    private val repository = kg.geekspro.android_lotos.ui.repositories.reposprofile.Repository()

    fun setPassword(password: Password):LiveData<String>{
        return repository.setPassword(password)
    }
}