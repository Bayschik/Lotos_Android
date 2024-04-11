package kg.geekspro.android_lotos.presentation.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import kg.geekspro.android_lotos.R
import kg.geekspro.android_lotos.databinding.FragmentRegistrationBinding

class RegistrationFragment : Fragment() {
    private lateinit var binding: FragmentRegistrationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnContinue.setOnClickListener {
                if (etOfficialPhoneNumber.text.toString().isEmpty()){
                    Toast.makeText(requireContext(), "Введите ваш номер телефона", Toast.LENGTH_SHORT).show()
                }else if(etOfficialPhoneNumber.text?.length != 9){
                    Toast.makeText(requireContext(), "Введите подный номер телефона", Toast.LENGTH_SHORT).show()
                }else{
                    findNavController().navigate(R.id.verificationCodeFragment, bundleOf("PHONE_NUMBER" to etOfficialPhoneNumber.text.toString()))
                }
            }
        }
    }
}