package kg.geekspro.android_lotos.activites.verifyViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import kg.geekspro.android_lotos.models.verifyCode.VerificationCode
import kg.geekspro.android_lotos.ui.repositories.repoProfile.Repository

class VerificationViewModel:ViewModel() {
    private val repository = kg.geekspro.android_lotos.ui.repositories.repoProfile.Repository()

    fun confirmCode(code: VerificationCode): LiveData<String> {
        return repository.confirmCode(code)
    }
}