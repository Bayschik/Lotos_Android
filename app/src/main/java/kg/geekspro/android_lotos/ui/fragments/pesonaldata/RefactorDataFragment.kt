package kg.geekspro.android_lotos.ui.fragments.pesonaldata

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.net.Uri
import android.os.Bundle
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
import javax.inject.Inject

@AndroidEntryPoint
class RefactorDataFragment : Fragment() {
    private lateinit var binding: FragmentRefactorDataBinding
    private val viewModel: PersonalDataViewModel by viewModels()
    private val refactorViewModel: RefactorDataViewModel by viewModels()
    @Inject
    lateinit var pref: Pref

    private val getCommentMedia =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val selectedFileUri = result.data?.data!!
                pref.saveImage(selectedFileUri.toString())
                Glide.with(binding.imageProfile).load(pref.getImage())
                    .into(binding.imageProfile)
            }
        }

    /*private val getCommentMedia =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val selectedFileUri = result.data?.data!!
                val pngFile = convertUriToPng(selectedFileUri)
                val requestBody = RequestBody.create("image/png".toMediaTypeOrNull(), pngFile)
                val photoPart = MultipartBody.Part.createFormData("photo", pngFile.name, requestBody)

                // Update refactorData with photoPart
                val refactorData = Profile(
                    photo = photoPart,
                    firstName = binding.etName.text.toString(),
                    lastName = binding.etSurname.text.toString(),
                    dateOfBirth = binding.etDateOfBirth.text.toString(),
                    address = binding.etAddress.text.toString()
                )
                refactorViewModel.putData(refactorData).observe(viewLifecycleOwner) {
                    findNavController().navigate(R.id.profileFragment)
                }
            }
        }*/

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
                        /*getCommentMedia*/

                        val refactorData = Profile(
                            photo = pref.getImage()!!,
                            firstName = binding.etName.text.toString(),
                            lastName = binding.etSurname.text.toString(),
                            dateOfBirth = binding.etDateOfBirth.text.toString(),
                            address = binding.etAddress.text.toString()
                        )

                        refactorViewModel.putData(refactorData).observe(viewLifecycleOwner) {
                            findNavController().navigate(R.id.profileFragment)
                        }
                    }

                }
            }

            Glide.with(imageProfile).load(pref.getImage())
                .placeholder(R.drawable.ic_black_profile).into(imageProfile)

            imageProfile.setOnClickListener {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                getCommentMedia.launch(intent)
            }
        }

    }

    private fun convertUriToPng(uri: Uri): File {
        val contextWrapper = ContextWrapper(requireContext())
        val file = contextWrapper.getDir("images", Context.MODE_PRIVATE)
        val fileName = "${System.currentTimeMillis()}.png"
        val destinationFile = File(file, fileName)

        val inputStream = requireContext().contentResolver.openInputStream(uri)
        val outputStream = FileOutputStream(destinationFile)

        val buffer = ByteArray(1024)
        var bytesRead: Int
        while (inputStream!!.read(buffer).also { bytesRead = it } > 0) {
            outputStream.write(buffer, 0, bytesRead)
        }

        inputStream.close()
        outputStream.close()

        return destinationFile
    }
}
