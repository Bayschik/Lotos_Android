package kg.geekspro.android_lotos.activites.profileViewModel.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import kg.geekspro.android_lotos.ui.repositories.repoProfile.Repository
import kg.geekspro.android_lotos.models.profile.Password

class PasswordCreateViewModel:ViewModel() {
    private val repository = kg.geekspro.android_lotos.ui.repositories.repoProfile.Repository()

    fun setPassword(password: Password):LiveData<String>{
        return repository.setPassword(password)
    }
}