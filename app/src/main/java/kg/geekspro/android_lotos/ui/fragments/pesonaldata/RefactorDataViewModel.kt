package kg.geekspro.android_lotos.ui.fragments.pesonaldata

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kg.geekspro.android_lotos.models.profile.Profile
import kg.geekspro.android_lotos.ui.repositories.reposprofile.Repository
import javax.inject.Inject

@HiltViewModel
class RefactorDataViewModel @Inject constructor(private val repository:Repository):ViewModel() {
    fun putData(refactorData:Profile):LiveData<Profile>{
        return repository.putDataProfile(refactorData)
    }
}