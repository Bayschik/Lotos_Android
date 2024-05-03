package kg.geekspro.android_lotos.viewmodels.profileviewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kg.geekspro.android_lotos.models.profile.Profile
import kg.geekspro.android_lotos.ui.fragments.profile.Token
import kg.geekspro.android_lotos.ui.fragments.profile.TokenVerify
import kg.geekspro.android_lotos.ui.repositories.reposprofile.Repository
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val repository: Repository) :ViewModel() {
    suspend fun getProfile():LiveData<Profile>{
        return repository.getProfile()
    }

    suspend fun logOut():LiveData<Unit>{
        return repository.logOut()
    }
    suspend fun checkUser(accessToken: Token):LiveData<TokenVerify>{
        return repository.checkUser(accessToken)
    }

}