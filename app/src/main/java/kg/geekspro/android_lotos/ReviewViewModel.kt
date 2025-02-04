package kg.geekspro.android_lotos

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kg.geekspro.android_lotos.ui.repositories.reposprofile.Repository
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class ReviewViewModel @Inject constructor(private val repository: Repository):ViewModel() {
    /*fun leaveReview(reviewModel: ReviewModel):LiveData<ReviewModel>{
        return repository.leaveReview(reviewModel)
    }*/

    fun leaveReview(text:RequestBody, stars:RequestBody, orderId:RequestBody, images:List<MultipartBody.Part>):LiveData<ReviewModel>{
        return repository.leaveReview(text, stars, orderId, images)
    }
}