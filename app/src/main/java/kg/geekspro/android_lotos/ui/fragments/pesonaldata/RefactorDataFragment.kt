package kg.geekspro.android_lotos.ui.fragments.pesonaldata

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
import javax.inject.Inject

@AndroidEntryPoint
class RefactorDataFragment : Fragment() {
    private lateinit var binding: FragmentRefactorDataBinding
    private val viewModel:PersonalDataViewModel by viewModels()
    private val refactorViewModel:RefactorDataViewModel by viewModels()

    @Inject
    lateinit var pref: Pref

    private val getCommentMedia =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val selectedFileUri = result.data?.data
                selectedFileUri?.let { uri ->
                    val inputStream = requireContext().contentResolver.openInputStream(uri)
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    val output = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, output)
                    val byteArray = output.toByteArray()
                    pref.saveImage(byteArray.toString()) // Сохранение изображения в формате PNG в SharedPreferences
                    Glide.with(binding.imageProfile).load(byteArray)
                        .into(binding.imageProfile)
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
            viewModel.getProfileData().observe(viewLifecycleOwner){
                if (it != null) {

                    etName.setText(it.firstName)
                    etSurname.setText(it.lastName)
                    etDateOfBirth.setText(it.dateOfBirth)
                    etAddress.setText(it.address)

                    btnSaveData.setOnClickListener {
                        val refactorData = Profile(
                            photo = pref.getImage()!!.toByteArray(),
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
}
