package kg.geekspro.android_lotos.ui.fragments.safety.safetyEmail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kg.geekspro.android_lotos.ui.repositories.reposprofile.Repository
import javax.inject.Inject

@HiltViewModel
class SafetyEmailViewModel @Inject constructor(private val repository: Repository):ViewModel() {
    suspend fun changeEmail(changeEmail: ChangeEmail):LiveData<Any>{
        return repository.changeEmail(changeEmail)
    }

    suspend fun changeEmailConfirm(code: Code):LiveData<String>{
        return repository.changeEmailConfirm(code)
    }
}