package kg.geekspro.android_lotos.ui.fragments.pesonaldata

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kg.geekspro.android_lotos.R
import kg.geekspro.android_lotos.databinding.FragmentRefactorDataBinding
import kg.geekspro.android_lotos.ui.fragments.pesonaldata.personalInfoFragment.personalData.PersonalDataViewModel
import kg.geekspro.android_lotos.ui.prefs.prefsprofile.Pref
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject

@AndroidEntryPoint
class RefactorDataFragment : Fragment() {
    private lateinit var binding: FragmentRefactorDataBinding
    private val viewModel: PersonalDataViewModel by viewModels()
    private val refactorViewModel: RefactorDataViewModel by viewModels()
    private var selectedFileUri: Uri? = null
    private var avatarPart: MultipartBody.Part? = null
    @Inject
    lateinit var pref: Pref

    private val getCommentMedia =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                selectedFileUri = result.data?.data
                /*selectedFileUri?.let { uri ->
                    // Конвертируем URI в файл JPEG
                    val jpegFile = uriToJpegFile(requireContext(), uri)
                    jpegFile?.let { file ->
                        val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
                        avatarPart = MultipartBody.Part.createFormData("image", file.name, requestFile)
                        // Показать выбранное изображение
                        Glide.with(binding.imageProfile).load(uri)
                            .placeholder(R.drawable.ic_black_profile)
                            .into(binding.imageProfile)
                        Log.d("image", "$selectedFileUri")
                    }
                }*/
                Glide.with(binding.imageProfile).load(selectedFileUri)
                    .placeholder(R.drawable.ic_black_profile)
                    .into(binding.imageProfile)
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRefactorDataBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            viewModel.getProfileData(pref.getAccessToken()!!)
                .observe(viewLifecycleOwner) { profileData ->
                    profileData?.let {
                        etName.setText(it.firstName)
                        etSurname.setText(it.lastName)
                        etDateOfBirth.setText(it.dateOfBirth)
                        etAddress.setText(it.address)
                    }
                }

            btnSaveData.setOnClickListener {
                val firstName = createPartFromString(etName.text.toString())
                val lastName = createPartFromString(etSurname.text.toString())
                val dateOfBirth = createPartFromString(etDateOfBirth.text.toString())
                val address = createPartFromString(etAddress.text.toString())

                refactorViewModel.putData(createMultipartBody(selectedFileUri!!), firstName, lastName, dateOfBirth, address)
                    .observe(viewLifecycleOwner) {
                        findNavController().navigate(R.id.profileFragment)
                    }

            }

            imageProfile.setOnClickListener {
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = "image/jpeg"
                getCommentMedia.launch(intent)
            }
        }
    }

    private fun createPartFromString(value: String): RequestBody {
        return value.toRequestBody("text/plain".toMediaTypeOrNull())
    }


    private fun uriToFile(uri: Uri): File? {
        val contentResolver = requireContext().contentResolver
        val filePath = "${requireContext().cacheDir}/temp_image.jpg"
        val file = File(filePath)
        return try {
            contentResolver.openInputStream(uri)?.use { inputStream ->
                FileOutputStream(file).use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
            file
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    private fun uriToJpegFile(context: Context, uri: Uri): File? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            val file = File(context.cacheDir, "converted_image.jpg")
            val outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream.close()
            file
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun createEmptyImagePart(): MultipartBody.Part {
        val emptyFile = File(requireContext().cacheDir, "empty_image.jpeg")
        emptyFile.createNewFile()
        val requestFile = emptyFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("image", emptyFile.name, requestFile)
    }

    private fun getFileFromUri(uri: Uri): File {
        val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = requireContext().contentResolver.query(uri, filePathColumn, null, null, null)
        cursor?.moveToFirst()
        val columnIndex = cursor?.getColumnIndex(filePathColumn[0])
        val filePath = cursor?.getString(columnIndex!!)
        cursor?.close()
        return File(filePath!!)
    }

    private fun createMultipartBody(uri: Uri): MultipartBody.Part {
        val file = uriToJpegFile(requireContext(), uri)!!
        val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("image", file.name, requestFile)
    }
}
