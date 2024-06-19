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
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kg.geekspro.android_lotos.databinding.FragmentLeaveReviewBinding

@AndroidEntryPoint
class LeaveReviewFragment : Fragment() {
    private lateinit var binding:FragmentLeaveReviewBinding
    private var stars:Float = 0.0f
    private val imageList = mutableListOf<Uri>()
    private val viewModel:ReviewViewModel by viewModels()

    private val getContent = registerForActivityResult(ActivityResultContracts.GetMultipleContents()) { uris ->
        imageList.addAll(uris)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLeaveReviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.getInt("id of order")
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
                    orderId = id!!,
                    stars = stars.toInt(),
                    text = etComment.text.toString()
                )
                viewModel.leaveReview(review).observe(viewLifecycleOwner){
                    findNavController().popBackStack()
                    findNavController().navigate(R.id.profileFragment)
                }
            }
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        //intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        /*startActivityForResult(
            Intent.createChooser(intent, "Select Picture"),
            ReviewFragment.PICK_IMAGES_REQUEST_CODE
        )*/
        getContent.launch("image/*")
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