package kg.geekspro.android_lotos.presentation.ui.fragments.verificationCode

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import kg.geekspro.android_lotos.presentation.ui.data.repository.Repository

class VerificationViewModel:ViewModel() {
    private val repository = Repository()

    fun confirmCode(code: VerificationCode): LiveData<String> {
        return repository.confirmCode(code)
    }
}