package kg.geekspro.android_lotos

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.PorterDuff
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kg.geekspro.android_lotos.databinding.FragmentLeaveReviewBinding
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

@AndroidEntryPoint
class LeaveReviewFragment : Fragment() {
    private lateinit var binding:FragmentLeaveReviewBinding
    private var stars:Float = 0.0f
    private val imageList = mutableListOf<Uri>()
    private val viewModel:ReviewViewModel by viewModels()
    private val orderId = 8

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
            btnSendReview.setOnClickListener {
                val review = ReviewModel(
                    images = imageList,
                    orderId = 8,
                    stars = stars.toInt(),
                    text = etComment.text.toString()
                )

                sendingData()
            }
        }
    }

    private fun sendingData() = with(binding){
        val text = etComment.text.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val avatar = imageList.mapNotNull { uri ->
            uriToFile(requireContext(), uri)?.let { file ->
                val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                MultipartBody.Part.createFormData("photo", file.name, requestFile) // Обратите внимание на "photo"
            }
        }

        viewModel.leaveReview(
            text = text,
            stars = stars.toString().toRequestBody("text/plain".toMediaTypeOrNull()),
            orderId = orderId.toString().toRequestBody("text/plain".toMediaTypeOrNull()),
            images = avatar
        ).observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "Отзыв был успешен", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
            findNavController().navigate(R.id.profileFragment)
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        startActivityForResult(
            Intent.createChooser(intent, "Select Picture"),
            ReviewFragment.PICK_IMAGES_REQUEST_CODE
        )
        //getContent.launch("image/*")
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

    private fun uriToFile(context: Context, uri: Uri): File? {
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        val bitmap = BitmapFactory.decodeStream(inputStream)
        val tempFile = File(context.cacheDir, "temp_image_file.jpg")
        try {
            FileOutputStream(tempFile).use { out ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 75, out)
            }
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
        return tempFile
    }

    companion object{
        const val PICK_IMAGES_REQUEST_CODE = 123
    }

}