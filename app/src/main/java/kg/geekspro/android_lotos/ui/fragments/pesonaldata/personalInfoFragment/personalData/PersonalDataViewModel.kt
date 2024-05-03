package kg.geekspro.android_lotos.ui.fragments.pesonaldata.personalInfoFragment.personalData

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kg.geekspro.android_lotos.models.profile.Profile
import kg.geekspro.android_lotos.ui.repositories.reposprofile.Repository
import javax.inject.Inject

@HiltViewModel
class PersonalDataViewModel @Inject constructor(private val repository: Repository):ViewModel() {
    suspend fun getProfileData():LiveData<Profile>{
        return repository.getProfile()
    }
}