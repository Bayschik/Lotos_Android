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
import kg.geekspro.android_lotos.models.profile.Profile
import kg.geekspro.android_lotos.ui.fragments.pesonaldata.personalInfoFragment.personalData.PersonalDataViewModel
import kg.geekspro.android_lotos.ui.prefs.prefsprofile.Pref
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import javax.inject.Inject

@AndroidEntryPoint
class RefactorDataFragment : Fragment() {
    private lateinit var binding: FragmentRefactorDataBinding
    private val viewModel: PersonalDataViewModel by viewModels()
    private val refactorViewModel: RefactorDataViewModel by viewModels()
    private var selectedFileUri:Uri ?= null
    private lateinit var avatarPart:MultipartBody.Part
    @Inject
    lateinit var pref: Pref

    private val getCommentMedia =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                selectedFileUri = result.data?.data!!
                val file = uriToPngFile(requireContext(), selectedFileUri!!)
                file?.let {
                    avatarPart = createMultipartBodyPart(it, "avatar")
                }
                Glide.with(binding.imageProfile).load(selectedFileUri).placeholder(R.drawable.ic_black_profile)
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
            viewModel.getProfileData(pref.getAccessToken()!!).observe(viewLifecycleOwner) {
                if (it != null) {
                    etName.setText(it.firstName)
                    etSurname.setText(it.lastName)
                    etDateOfBirth.setText(it.dateOfBirth)
                    etAddress.setText(it.address)

                    btnSaveData.setOnClickListener {
                        val avatar = selectedFileUri?.let { uri ->
                            uriToFile(requireContext(),uri).let { file ->
                                val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(),file)
                                MultipartBody.Part.createFormData("avatar",file.name,requestFile)
                            }
                        }
                        val firstName = createPartFromString(binding.etName.text.toString())
                        val lastName = createPartFromString(binding.etSurname.text.toString())
                        val dateOfBirth = createPartFromString(binding.etDateOfBirth.text.toString())
                        val address = createPartFromString(binding.etAddress.text.toString())
                        val refactorData = Profile(
                            photo = uriToFile(requireContext(), selectedFileUri!!),
                            firstName = binding.etName.text.toString(),
                            lastName = binding.etSurname.text.toString(),
                            dateOfBirth = binding.etDateOfBirth.text.toString(),
                            address = binding.etAddress.text.toString()
                        )

                        refactorViewModel.putData(avatarPart,firstName,lastName,dateOfBirth,address).observe(viewLifecycleOwner) {
                            findNavController().navigate(R.id.profileFragment)
                        }
                    }

                }
            }

            imageProfile.setOnClickListener {
                val intent = Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                intent.type = "image/*"
                getCommentMedia.launch(intent)
            }
        }

    }

    private fun getFileFromUri(context: Context, uri: Uri): File {
        val inputStream = context.contentResolver.openInputStream(uri)
        val file = File(context.cacheDir, "upload_image")
        file.outputStream().use { inputStream?.copyTo(it) }
        return file
    }

    private fun prepareFilePart(context: Context, partName: String, fileUri: Uri): MultipartBody.Part {
        val file = getFileFromUri(context, fileUri)
        val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
        return MultipartBody.Part.createFormData(partName, file.name, requestFile)
    }

    fun createPartFromString(value: String): RequestBody {
        return RequestBody.create("text/plain".toMediaTypeOrNull(), value)
    }

    fun uriToFile(context: Context, uri: Uri): File {
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        val file = File(context.cacheDir, "selected_image.jpg")
        val outputStream = FileOutputStream(file)
        inputStream?.use { input ->
            outputStream.use { output ->
                input.copyTo(output)
            }
        }
        return file
    }

    private fun imageTo(value: String):RequestBody{
        return RequestBody.create("text/plain".toMediaTypeOrNull(),value)
    }

    fun createMultipartBodyPart(file: File, partName: String): MultipartBody.Part {
        val requestFile = RequestBody.create("image/png".toMediaTypeOrNull(), file)
        return MultipartBody.Part.createFormData(partName, file.name, requestFile)
    }

    fun uriToPngFile(context: Context, uri: Uri): File? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            val file = File(context.cacheDir, "converted_image.png")
            val outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.close()
            file
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
