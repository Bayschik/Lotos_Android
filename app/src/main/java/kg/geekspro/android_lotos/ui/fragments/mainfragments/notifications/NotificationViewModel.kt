package kg.geekspro.android_lotos.ui.fragments.mainfragments.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kg.geekspro.android_lotos.models.mainmodels.MainEntities
import kg.geekspro.android_lotos.ui.repositories.repomain.RepositoryMain
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(private val repository: RepositoryMain):ViewModel() {
    fun loadNotifications() : LiveData<NotificationsModel> {
        return repository.loadNotifications()
    }
}