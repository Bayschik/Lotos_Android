package kg.geekspro.android_lotos.viewmodels.fcmviewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kg.geekspro.android_lotos.models.firebasetoken.FcmAnswer
import kg.geekspro.android_lotos.ui.repositories.fcmtoken.FcmRepository
import javax.inject.Inject

@HiltViewModel
class FcmViewModel @Inject constructor(private val repository: FcmRepository):ViewModel() {

    fun loadFcm(fcmToken : String) : LiveData<FcmAnswer>{
        return repository.sendFcmToken(fcmToken)
    }
}