package kg.geekspro.android_lotos.viewmodels.aboutus.contactsviewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kg.geekspro.android_lotos.models.aboutusmodels.contactsmodels.WhatsAppModel
import kg.geekspro.android_lotos.models.firebasetoken.FcmAnswer
import kg.geekspro.android_lotos.ui.repositories.repoaboutus.contactsusrepo.WhatsAppRepositoryImpl
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WhatsAppViewModel @Inject constructor(
    private val repository: WhatsAppRepositoryImpl
) : ViewModel() {


    fun loadWP() : LiveData<List<WhatsAppModel>>{
        return repository.loadWP()
    }
}
