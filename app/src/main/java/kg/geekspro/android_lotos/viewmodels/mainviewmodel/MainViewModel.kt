package kg.geekspro.android_lotos.viewmodels.mainviewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kg.geekspro.android_lotos.models.firebasetoken.FcmAnswer
import kg.geekspro.android_lotos.models.mainmodels.MainEntities
import kg.geekspro.android_lotos.ui.repositories.fcmtoken.FcmRepository
import kg.geekspro.android_lotos.ui.repositories.repomain.RepositoryMain
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: RepositoryMain): ViewModel() {

    fun loadFcm() : LiveData<MainEntities> {
        return repository.loadMain()
    }
}