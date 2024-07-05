package kg.geekspro.android_lotos

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.PorterDuff
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kg.geekspro.android_lotos.databinding.FragmentLeaveReviewBinding
import kg.geekspro.android_lotos.ui.prefs.prefsprofile.Pref
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import javax.inject.Inject

@AndroidEntryPoint
class LeaveReviewFragment : Fragment() {
    private lateinit var binding: FragmentLeaveReviewBinding
    private var stars: Float = 0.0f
    private val imageList = mutableListOf<Uri>()
    private val viewModel: ReviewViewModel by viewModels()
    private lateinit var pickImage: ActivityResultLauncher<String>
    @Inject
    lateinit var pref: Pref

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLeaveReviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pickImage =
            registerForActivityResult(ActivityResultContracts.GetMultipleContents()) { uri: List<Uri> ->
                uri.let {
                    imageList.clear()
                    imageList.addAll(uri)
                    if (uri.isNotEmpty()) {
                        binding.ibAddImage.setColorFilter(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.blue
                            ), PorterDuff.Mode.SRC_IN
                        )
                    } else {
                        binding.ibAddImage.setColorFilter(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.gray_
                            ), PorterDuff.Mode.SRC_IN
                        )
                    }
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.getInt("id of order")
        binding.apply {
            ratingBar.stepSize = 1.0f
            ratingBar.setOnRatingBarChangeListener { _, fl, _ ->
                stars = fl
            }
            ibAddImage.setOnClickListener {
                openGallery()
            }
            btnSendReview.setOnClickListener {
                sendingData(id)
            }
            if (imageList.size > 0) {
                binding.ibAddImage.setColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.blue
                    ), PorterDuff.Mode.SRC_IN
                )
            } else {
                binding.ibAddImage.setColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.gray_
                    ), PorterDuff.Mode.SRC_IN
                )
            }
        }
    }

    private fun sendingData(id: Int?) = with(binding) {
        val text = etComment.text.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val avatar = imageList.mapNotNull { uri ->
            uriToFile(requireContext(), uri)?.let { file ->
                val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                MultipartBody.Part.createFormData(
                    "images",
                    file.name,
                    requestFile
                ) // Обратите внимание на "photo"
            }
        }

        viewModel.leaveReview(
            text = text,
            stars = stars.toString().toRequestBody("text/plain".toMediaTypeOrNull()),
            orderId = id.toString().toRequestBody("text/plain".toMediaTypeOrNull()),
            images = avatar
        ).observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "Отзыв был успешен", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
            findNavController().navigate(R.id.profileFragment)
        }
    }

    private fun openGallery() {
        pickImage.launch("image/*")
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
}