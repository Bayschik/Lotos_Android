package kg.geekspro.android_lotos.activites.registrationViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import kg.geekspro.android_lotos.models.registrationModel.Registration
import kg.geekspro.android_lotos.ui.repositories.repoProfile.Repository

class RegistrationViewModel:ViewModel() {
    private val repository = Repository()
    fun verifyEmail(email: Registration):LiveData<String>{
        return repository.verifyEmail(email)
    }
}