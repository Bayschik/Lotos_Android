package kg.geekspro.android_lotos.viewmodels.profileviewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kg.geekspro.android_lotos.models.profile.Profile
import kg.geekspro.android_lotos.ui.fragments.profile.RefreshToken
import kg.geekspro.android_lotos.ui.fragments.profile.Token
import kg.geekspro.android_lotos.ui.fragments.profile.TokenVerify
import kg.geekspro.android_lotos.ui.fragments.profile.password.create.PasswordCreate
import kg.geekspro.android_lotos.ui.repositories.reposprofile.Repository
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val repository: Repository) :ViewModel() {
    fun getProfile(accessToken:String):LiveData<Profile>{
        return repository.getProfile(accessToken)
    }

    fun logOut():LiveData<Unit>{
        return repository.logOut()
    }
    fun checkUser(accessToken: Token):LiveData<TokenVerify>{
        return repository.checkUser(accessToken)
    }

    fun refreshToken(refreshToken: RefreshToken):LiveData<PasswordCreate>{
        return repository.refreshToken(refreshToken)
    }

}