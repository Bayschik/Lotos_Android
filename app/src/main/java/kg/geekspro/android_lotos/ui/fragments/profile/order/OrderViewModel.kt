package kg.geekspro.android_lotos.ui.fragments.profile.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kg.geekspro.android_lotos.ui.repositories.reposprofile.Repository
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(private val repository: Repository):ViewModel() {
    fun getOrderId(id:Int):LiveData<Order>{
        return repository.getOrderId(id)
    }
}