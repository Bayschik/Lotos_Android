package kg.geekspro.android_lotos.ui.fragments.pesonaldata.personalInfoFragment.personalData

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kg.geekspro.android_lotos.R
import kg.geekspro.android_lotos.databinding.FragmentPersonalDataBinding
import kg.geekspro.android_lotos.ui.prefs.prefsprofile.Pref
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class PersonalDataFragment : Fragment() {
    private lateinit var binding: FragmentPersonalDataBinding
    private val viewModel:PersonalDataViewModel by viewModels()
    @Inject
    lateinit var pref: Pref

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPersonalDataBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            imgArrowBack.setOnClickListener {findNavController().navigateUp()}

            btnChangePersonalData.setOnClickListener {findNavController().navigate(R.id.refactorDataFragment)}

            viewModel.viewModelScope.launch {
                viewModel.getProfileData(pref.getAccessToken()!!).observe(viewLifecycleOwner){profile->
                    Glide.with(imgPersonalDataProfile).load("https://lotos.pp.ua/${profile.photo}").placeholder(R.drawable.ic_black_profile).into(imgPersonalDataProfile)
                    tvOfficialName.text = profile.firstName
                    tvOfficialSurname.text = profile.lastName
                    tvOfficialDateOfBirth.text = profile.dateOfBirth
                    tvOfficialAddress.text = profile.address
                }
            }
        }
    }
}
