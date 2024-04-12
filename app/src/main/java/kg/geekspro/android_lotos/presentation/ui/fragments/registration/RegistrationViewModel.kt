package kg.geekspro.android_lotos.presentation.ui.fragments.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import kg.geekspro.android_lotos.presentation.ui.data.repository.Repository
import kg.geekspro.android_lotos.presentation.ui.fragments.Code
import kg.geekspro.android_lotos.presentation.ui.fragments.VerificationCode
import kg.geekspro.android_lotos.presentation.ui.model.PersonalData

class RegistrationViewModel:ViewModel() {
    private val repository = Repository()
    fun verifyEmail(email:String):LiveData<String>{
        return repository.verifyEmail(email)
    }

    fun confirmCode(code:VerificationCode):LiveData<String>{
        return repository.confirmCode(code)
    }

    fun clientCreate(data: PersonalData):LiveData<String>{
        return repository.clientCreate(data)
    }
}