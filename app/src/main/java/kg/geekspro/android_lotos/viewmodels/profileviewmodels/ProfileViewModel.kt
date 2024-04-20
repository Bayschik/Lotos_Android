package kg.geekspro.android_lotos.viewmodels.profileviewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import kg.geekspro.android_lotos.models.profile.Profile

class ProfileViewModel:ViewModel() {
    private val repository = kg.geekspro.android_lotos.ui.repositories.reposprofile.Repository()

    fun getProfile():LiveData<Profile>{
        return repository.getProfile()
    }
}