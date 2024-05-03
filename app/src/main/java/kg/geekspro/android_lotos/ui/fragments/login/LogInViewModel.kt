package kg.geekspro.android_lotos.ui.fragments.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kg.geekspro.android_lotos.ui.fragments.profile.password.create.PasswordCreate
import kg.geekspro.android_lotos.ui.repositories.reposprofile.Repository
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(private val repository: Repository):ViewModel() {
    suspend fun logIn(logIn: LogIn):LiveData<PasswordCreate>{
        return repository.logIn(logIn)
    }
}