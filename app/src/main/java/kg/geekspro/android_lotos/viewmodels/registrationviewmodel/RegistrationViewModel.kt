package kg.geekspro.android_lotos.viewmodels.registrationviewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kg.geekspro.android_lotos.models.registrationmodel.Registration
import kg.geekspro.android_lotos.ui.repositories.reposprofile.Repository
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(private val repository: Repository):ViewModel() {
    fun verifyEmail(email: Registration):LiveData<String>{
        return repository.verifyEmail(email)
    }

    fun googleAuth():LiveData<Unit>{
        return repository.googleAuth()
    }
}