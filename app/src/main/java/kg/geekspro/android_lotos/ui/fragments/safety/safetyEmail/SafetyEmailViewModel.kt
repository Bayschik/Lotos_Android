package kg.geekspro.android_lotos.ui.fragments.safety.safetyEmail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kg.geekspro.android_lotos.ui.repositories.reposprofile.Repository
import javax.inject.Inject

@HiltViewModel
class SafetyEmailViewModel @Inject constructor(private val repository: Repository):ViewModel() {
    fun changeEmail(changeEmail: ChangeEmail):LiveData<Any>{
        return repository.changeEmail(changeEmail)
    }

    fun changeEmailConfirm(code: Code):LiveData<Any>{
        return repository.changeEmailConfirm(code)
    }
}