package kg.geekspro.android_lotos.presentation.ui.profile.password.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import kg.geekspro.android_lotos.presentation.ui.data.repository.Repository

class PasswordCreateViewModel:ViewModel() {
    private val repository = Repository()

    fun setPassword(password: Password):LiveData<String>{
        return repository.setPassword(password)
    }
}