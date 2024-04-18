package kg.geekspro.android_lotos.activites.profileViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import kg.geekspro.android_lotos.models.profile.Profile
import kg.geekspro.android_lotos.ui.repositories.repoProfile.Repository

class ProfileViewModel:ViewModel() {
    private val repository = kg.geekspro.android_lotos.ui.repositories.repoProfile.Repository()

    fun getProfile():LiveData<Profile>{
        return repository.getProfile()
    }
}