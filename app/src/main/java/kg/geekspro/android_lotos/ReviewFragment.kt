package kg.geekspro.android_lotos

import android.app.Activity
import android.content.Intent
import android.graphics.PorterDuff
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kg.geekspro.android_lotos.databinding.FragmentReviewBinding

@AndroidEntryPoint
class ReviewFragment : Fragment() {
    private lateinit var binding:FragmentReviewBinding
    private var stars:Float = 0.0f
    private val imageList = mutableListOf<Uri>()
    private val viewModel:ReviewViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentReviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            ratingBar.stepSize = .5f
            ratingBar.setOnRatingBarChangeListener { _, fl, _ ->
                stars=fl
            }
            ibAddImage.setOnClickListener{
                openGallery()
            }
            btnLeaveReview.setOnClickListener {
                val review = ReviewModel(
                    images = imageList,
                    orderId = 8,
                    stars = stars.toInt(),
                    text = etComment.text.toString()
                )
                Toast.makeText(requireContext(), "hello", Toast.LENGTH_SHORT).show()
                /*viewModel.leaveReview(review).observe(viewLifecycleOwner){

                }*/
            }
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        startActivityForResult(Intent.createChooser(intent, "Select Pictures"), PICK_IMAGES_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGES_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data?.clipData != null) {
                imageList.clear()
                val clipData = data.clipData
                for (i in 0 until clipData!!.itemCount) {
                    val imageUri: Uri = clipData.getItemAt(i).uri
                    imageList.add(imageUri)
                }
            } else if (data?.data != null) {
                imageList.clear()
                val imageUri: Uri = data.data!!
                imageList.add(imageUri)
            }
            if(imageList.size > 0){
                binding.ibAddImage.setColorFilter(ContextCompat.getColor(requireContext(), R.color.blue), PorterDuff.Mode.SRC_IN)
            }else{
                binding.ibAddImage.setColorFilter(ContextCompat.getColor(requireContext(), R.color.gray_), PorterDuff.Mode.SRC_IN)
            }


        }
    }

    companion object{
        const val PICK_IMAGES_REQUEST_CODE = 123
    }

}