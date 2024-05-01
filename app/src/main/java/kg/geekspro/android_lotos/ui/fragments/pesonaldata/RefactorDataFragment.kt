package kg.geekspro.android_lotos.ui.fragments.pesonaldata

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject

@AndroidEntryPoint
class RefactorDataFragment : Fragment() {
    private lateinit var binding: FragmentRefactorDataBinding
    private val viewModel:PersonalDataViewModel by viewModels()
    private val refactorViewModel:RefactorDataViewModel by viewModels()
    private lateinit var byteArray: ByteArray
    private lateinit var selectedFileUri:Uri

    @Inject
    lateinit var pref: Pref

    private val getCommentMedia =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                selectedFileUri = result.data?.data!!
                selectedFileUri.let {
                    byteArray = convertImageToFormat(requireContext(), it, Bitmap.CompressFormat.JPEG) ?: byteArrayOf()
                    pref.saveImage(it.toString())
                    Glide.with(binding.imageProfile).load(it)
                        .into(binding.imageProfile)
                }
                /*pref.saveImage(selectedFileUri.toString())
                Glide.with(binding.imageProfile).load(pref.getImage())
                    .into(binding.imageProfile)
                convertImageToFormat(requireContext(), selectedFileUri!!, Bitmap.CompressFormat.JPEG)
*/
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
            viewModel.getProfileData().observe(viewLifecycleOwner){
                if (it != null) {
                    etName.setText(it.firstName)
                    etSurname.setText(it.lastName)
                    etDateOfBirth.setText(it.dateOfBirth)
                    etAddress.setText(it.address)

                    btnSaveData.setOnClickListener {
                        val byteArray = convertImageToFormat(requireContext(), selectedFileUri, Bitmap.CompressFormat.JPEG) ?: byteArrayOf()
                        val imageFile = convertByteArrayToFile(requireContext(), byteArray, "image.jpg")

                        val refactorData = Profile(
                            photo = imageFile,
                            firstName = etName.text.toString(),
                            lastName = etSurname.text.toString(),
                            dateOfBirth = etDateOfBirth.text.toString(),
                            address = etAddress.text.toString()
                        )
                        refactorViewModel.putData(refactorData).observe(viewLifecycleOwner){
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

    private fun convertImageToFormat(context: Context, imageUri: Uri, outputFormat: Bitmap.CompressFormat): ByteArray? {
        try {
            // Load the image Bitmap from the Uri
           /* val inputStream = context.contentResolver.openInputStream(imageUri)
            val bitmap = BitmapFactory.decodeStream(inputStream)

            // Create a new file to save the converted image
            val outputFile = File(context.cacheDir, "converted_image.${outputFormat.name.toLowerCase()}")

            // Convert Bitmap to the desired format and save it to the file
            val outputStream = FileOutputStream(outputFile)
            bitmap.compress(outputFormat, 100, outputStream)
            outputStream.flush()
            outputStream.close()

            // Return the Uri of the newly converted image file
            return Uri.fromFile(outputFile)*/

            val inputStream = context.contentResolver.openInputStream(imageUri)
            val bitmap = BitmapFactory.decodeStream(inputStream)

            // Convert Bitmap to the desired format (JPEG) and save it to a ByteArrayOutputStream
            val outputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)

            // Convert ByteArrayOutputStream to ByteArray
            return outputStream.toByteArray()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    private fun convertByteArrayToFile(context: Context, byteArray: ByteArray, fileName: String): File {
        val file = File(context.cacheDir, fileName)
        FileOutputStream(file).use {
            it.write(byteArray)
        }
        return file
    }
}
