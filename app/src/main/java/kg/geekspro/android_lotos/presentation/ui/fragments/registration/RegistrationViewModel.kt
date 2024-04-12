package kg.geekspro.android_lotos.presentation.ui.fragments.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import kg.geekspro.android_lotos.presentation.ui.data.repository.Repository

class RegistrationViewModel:ViewModel() {
    private val repository = Repository()
    fun verifyEmail(email:Registration):LiveData<String>{
        return repository.verifyEmail(email)
    }
}