package kg.geekspro.android_lotos.viewmodels.verifyviewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kg.geekspro.android_lotos.models.verifycode.VerificationCode
import kg.geekspro.android_lotos.ui.repositories.reposprofile.Repository
import javax.inject.Inject

@HiltViewModel
class VerificationViewModel @Inject constructor(private val repository: Repository):ViewModel() {

    /*fun confirmCode(code: VerificationCode): LiveData<String> {
        return repository.confirmCode(code)
    }
     */

    fun confirmCode(code: VerificationCode): LiveData<String> {
        return repository.confirmCode(code)
    }
}