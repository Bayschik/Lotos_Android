package kg.geekspro.android_lotos.ui.fragments.pesonaldata

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kg.geekspro.android_lotos.models.profile.Profile
import kg.geekspro.android_lotos.ui.repositories.reposprofile.Repository
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class RefactorDataViewModel @Inject constructor(private val repository:Repository):ViewModel() {
    fun putData(image:MultipartBody.Part,firstName:RequestBody, lastName:RequestBody, dateOfBirth:RequestBody, address:RequestBody):LiveData<Profile>{
        return repository.putDataProfile(image,firstName, lastName,dateOfBirth,address)
    }
}