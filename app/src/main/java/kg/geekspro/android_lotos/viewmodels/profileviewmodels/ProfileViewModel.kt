package kg.geekspro.android_lotos.viewmodels.profileviewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kg.geekspro.android_lotos.models.profile.Profile
import kg.geekspro.android_lotos.ui.fragments.profile.Token
import kg.geekspro.android_lotos.ui.repositories.reposprofile.Repository
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val repository: Repository) :ViewModel() {
    fun getProfile():LiveData<Profile>{
        return repository.getProfile()
    }

    fun logOut():LiveData<Unit>{
        return repository.logOut()
    }
    fun checkUser(accessToken: Token):LiveData<String>{
        return repository.checkUser(accessToken)
    }

}