package kg.geekspro.android_lotos.viewmodels.verifyviewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import kg.geekspro.android_lotos.models.verifycode.VerificationCode
import kg.geekspro.android_lotos.ui.repositories.reposprofile.Repository

class VerificationViewModel:ViewModel() {
    private val repository = Repository()

    fun confirmCode(code: VerificationCode): LiveData<String> {
        return repository.confirmCode(code)
    }
}