package kg.geekspro.android_lotos.ui.fragments.pesonaldata

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import java.io.FileInputStream
import java.io.FileOutputStream
import javax.inject.Inject

@AndroidEntryPoint
class RefactorDataFragment : Fragment(), UploadCallback{
    private lateinit var binding: FragmentRefactorDataBinding
    private val viewModel: PersonalDataViewModel by viewModels()
    private val refactorViewModel: RefactorDataViewModel by viewModels()
    private var selectedFileUri: Uri? = null
    @Inject
    lateinit var pref: Pref

    private val getCommentMedia =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                selectedFileUri = result.data?.data
                Glide.with(binding.imageProfile).load(selectedFileUri)
                    .placeholder(R.drawable.ic_black_profile)
                    .into(binding.imageProfile)
                Log.d("image","$selectedFileUri")
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
                /*val firstName = createPartFromString(etName.text.toString())
                val lastName = createPartFromString(etSurname.text.toString())
                val dateOfBirth = createPartFromString(etDateOfBirth.text.toString())
                val address = createPartFromString(etAddress.text.toString())

                val imageFile = uriToFile(selectedFileUri!!, requireContext())

                val requestFile = RequestBody.create("image/jpeg".toMediaTypeOrNull(), imageFile)
                val body = MultipartBody.Part.createFormData("image", imageFile.name, requestFile)


                refactorViewModel.putData(body, firstName, lastName, dateOfBirth, address)
                    .observe(viewLifecycleOwner) {
                        findNavController().navigate(R.id.profileFragment)
                    }*/
                uploadImage()
            }

            imageProfile.setOnClickListener {
                /*val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = "image/jpeg"
                getCommentMedia.launch(intent)*/
                imageChooser()
            }
        }
    }

    private fun createPartFromString(value: String): RequestBody {
        return value.toRequestBody("text/plain".toMediaTypeOrNull())
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

    private fun createMultipartBody(uri: Uri): MultipartBody.Part {
        val file = uriToJpegFile(requireContext(), uri)!!
        val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("image", file.name, requestFile)
    }

    //
    private fun imageChooser(){
        Intent(Intent.ACTION_GET_CONTENT).also {
            it.type = "image/*"
            val mimeTypes = arrayOf("image/jpeg", "image/png")
            it.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            getCommentMedia.launch(it)
        }
    }

    override fun onProgressUpdate(percentage: Int) {
        Toast.makeText(requireContext(), "ALL IS $percentage", Toast.LENGTH_SHORT).show()
    }


    private fun uploadImage()= with(binding){
        val parcelFileDescriptor = context?.contentResolver?.openFileDescriptor(selectedFileUri!!, "r", null)
        val inputStream = FileInputStream(parcelFileDescriptor?.fileDescriptor)
        val file = File(context?.cacheDir, context?.contentResolver?.getFileName(selectedFileUri!!))
        val outputStream = FileOutputStream(file)
        inputStream.copyTo(outputStream)
        val body = UploadRequestBody(file,"image")

        val firstName = createPartFromString(etName.text.toString())
        val lastName = createPartFromString(etSurname.text.toString())
        val dateOfBirth = createPartFromString(etDateOfBirth.text.toString())
        val address = createPartFromString(etAddress.text.toString())

        val imageBody = MultipartBody.Part.createFormData("photo",file.name, body)

        refactorViewModel.putData(imageBody, firstName, lastName, dateOfBirth, address)
            .observe(viewLifecycleOwner) {
                findNavController().navigate(R.id.profileFragment)
            }
    }

    @SuppressLint("Range")
    fun ContentResolver.getFileName(uri: Uri):String{
        var name = ""
        val cursor = query(uri, null, null,null, null)
        cursor?.use {
            it.moveToFirst()
            name = cursor.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
        }
        return name
    }
    //
}
