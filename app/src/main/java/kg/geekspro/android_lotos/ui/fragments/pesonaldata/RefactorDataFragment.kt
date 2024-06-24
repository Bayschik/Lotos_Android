package kg.geekspro.android_lotos.ui.fragments.pesonaldata

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
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
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kg.geekspro.android_lotos.R
import kg.geekspro.android_lotos.databinding.FragmentRefactorDataBinding
import kg.geekspro.android_lotos.ui.fragments.pesonaldata.personalInfoFragment.personalData.PersonalDataViewModel
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
class RefactorDataFragment : Fragment() {
    private lateinit var binding: FragmentRefactorDataBinding
    private val viewModel: PersonalDataViewModel by viewModels()
    private val refactorViewModel: RefactorDataViewModel by viewModels()
    private var selectedFileUri: Uri? = null
    @Inject
    lateinit var pref: Pref
    private lateinit var pickImage: ActivityResultLauncher<String>

    companion object {
        private const val REQUEST_STORAGE_PERMISSION = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                selectedFileUri = it
                loadSelectedImage(selectedFileUri!!)
            }
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
            viewModel.getProfileData(pref.getAccessToken()!!).observe(viewLifecycleOwner) { profile ->
                profile?.let {
                    etName.setText(it.firstName)
                    etSurname.setText(it.lastName)
                    etDateOfBirth.setText(it.dateOfBirth)
                    etAddress.setText(it.address)

                    btnSaveData.setOnClickListener {
                        val firstName = etName.text.toString()
                        val lastName = etSurname.text.toString()
                        val dateOfBirth = etDateOfBirth.text.toString()
                        val address = etAddress.text.toString()
                        val isDataChanged = firstName != profile.firstName ||
                                lastName != profile.lastName ||
                                dateOfBirth != profile.dateOfBirth ||
                                address != profile.address ||
                                selectedFileUri != null

                        if (isDataChanged) {
                            val firstNameBody = firstName.toRequestBody("text/plain".toMediaTypeOrNull())
                            val lastNameBody = lastName.toRequestBody("text/plain".toMediaTypeOrNull())
                            val dateOfBirthBody = dateOfBirth.toRequestBody("text/plain".toMediaTypeOrNull())
                            val addressBody = address.toRequestBody("text/plain".toMediaTypeOrNull())
                            val avatar = selectedFileUri?.let { uri ->
                                uriToFile(requireContext(), uri)?.let { file ->
                                    val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                                    MultipartBody.Part.createFormData("photo", file.name, requestFile) // Обратите внимание на "photo"
                                }
                            }

                            if (avatar != null) {
                                refactorViewModel.putData(
                                    image = avatar,
                                    firstName = firstNameBody,
                                    lastName = lastNameBody,
                                    dateOfBirth = dateOfBirthBody,
                                    address = addressBody
                                ).observe(viewLifecycleOwner) { response ->
                                    Log.d("RefactorDataFragment", "Response received from server: $response")
                                    Toast.makeText(requireContext(), "Данные успешно сохранены", Toast.LENGTH_SHORT).show()
                                    findNavController().navigate(R.id.profileFragment)
                                }
                            } else {
                                refactorViewModel.putData(
                                    image = MultipartBody.Part.createFormData("photo", ""),
                                    firstName = firstNameBody,
                                    lastName = lastNameBody,
                                    dateOfBirth = dateOfBirthBody,
                                    address = addressBody
                                ).observe(viewLifecycleOwner) { response ->
                                    Log.d("RefactorDataFragment", "Response received from server: $response")
                                    Toast.makeText(requireContext(), "Данные успешно сохранены", Toast.LENGTH_SHORT).show()
                                    findNavController().navigate(R.id.profileFragment)
                                }
                            }
                        } else {
                            findNavController().navigate(R.id.profileFragment)
                            Toast.makeText(requireContext(), "Данные не изменились", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

            imageProfile.setOnClickListener {
                checkStoragePermission()
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
    private fun openGallery() {
        Log.d("RefactorDataFragment", "Selected Uri: $selectedFileUri")
        pickImage.launch("image/*")
    }

    private fun loadSelectedImage(uri: Uri) {
        selectedFileUri = uri
        Glide.with(this)
            .load(uri)
            .into(binding.imageProfile)
    }

    private fun checkStoragePermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                REQUEST_STORAGE_PERMISSION)
        } else {
            openGallery()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_STORAGE_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery()
            } else {
                Toast.makeText(requireContext(), "Для выбора изображения необходимо разрешение", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
