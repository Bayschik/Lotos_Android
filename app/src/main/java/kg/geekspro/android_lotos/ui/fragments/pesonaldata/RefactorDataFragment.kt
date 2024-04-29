package kg.geekspro.android_lotos.ui.fragments.pesonaldata

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kg.geekspro.android_lotos.R
import kg.geekspro.android_lotos.databinding.FragmentRefactorDataBinding
import kg.geekspro.android_lotos.models.profile.Profile
import kg.geekspro.android_lotos.ui.fragments.pesonaldata.personalInfoFragment.personalData.PersonalDataViewModel
import kg.geekspro.android_lotos.ui.prefs.prefsprofile.Pref
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
                pref.saveImage(selectedFileUri.toString())
                Glide.with(binding.imageProfile).load(selectedFileUri.toString())
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
            viewModel.getProfileData().observe(viewLifecycleOwner){
                if (it != null) {

                    etName.setText(it.firstName)
                    etSurname.setText(it.lastName)
                    etDateOfBirth.setText(it.dateOfBirth)
                    etAddress.setText(it.address)

                    btnSaveData.setOnClickListener {
                        val refactorData = Profile(
                            photo = "http://209.38.228.54:88/media/category/2024-04-21T23_37_51_7w2qzvX.png",
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
                .placeholder(R.drawable.ic_profile_placeholder).into(imageProfile)

            imageProfile.setOnClickListener {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                getCommentMedia.launch(intent)
            }
        }

    }
}
