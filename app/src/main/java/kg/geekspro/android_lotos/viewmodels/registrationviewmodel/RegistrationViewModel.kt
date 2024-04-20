package kg.geekspro.android_lotos.viewmodels.registrationviewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import kg.geekspro.android_lotos.models.registrationmodel.Registration
import kg.geekspro.android_lotos.ui.repositories.reposprofile.Repository

class RegistrationViewModel:ViewModel() {
    private val repository = Repository()
    fun verifyEmail(email: Registration):LiveData<String>{
        return repository.verifyEmail(email)
    }
}