package kg.geekspro.android_lotos.presentation.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import kg.geekspro.android_lotos.R
import kg.geekspro.android_lotos.databinding.FragmentPersonalInfoBinding
import kg.geekspro.android_lotos.presentation.ui.model.PersonalData

class PersonalInfoFragment : Fragment() {
    private lateinit var binding:FragmentPersonalInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPersonalInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            val data = PersonalData(
                name = etName.text.toString(),
                surName = etSurname.text.toString(),
                dateOfBirth = etBirthday.text.toString(),
                phoneNumber = etPhoneNumber.text.toString(),
                email = etEmail.text.toString(),
                address = etAddress.text.toString(),
                password = etPassword.text.toString()
            )
            findNavController().navigate(R.id.mainFragment, bundleOf("PERSONAL_DATA" to data))
        }
    }

}