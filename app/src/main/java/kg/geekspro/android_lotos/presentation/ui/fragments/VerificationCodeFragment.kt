package kg.geekspro.android_lotos.presentation.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import kg.geekspro.android_lotos.R
import kg.geekspro.android_lotos.databinding.FragmentVerificationCodeBinding
import kg.geekspro.android_lotos.presentation.ui.fragments.registration.RegistrationViewModel

class VerificationCodeFragment : Fragment() {

    private lateinit var binding: FragmentVerificationCodeBinding
    private val viewModel:RegistrationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVerificationCodeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val phoneNumber = arguments?.getString("PHONE_NUMBER")
        //setUpCodeInput()
        binding.apply {
            btnCodeContinue.setOnClickListener {
                /*if (inputCode1.text.toString().isEmpty() ||
                    inputCode2.text.toString().isEmpty() ||
                    inputCode3.text.toString().isEmpty() ||
                    inputCode4.text.toString().isEmpty() ){
                    Toast.makeText(requireContext(), "Введите 4-значный код", Toast.LENGTH_SHORT).show()
                }else{*/
                    val data = VerificationCode(
                        email = etOfficialPhoneNumber.text.toString(),
                        code = etOfficialCode.text.toString()
                    )
                    viewModel.confirmCode(data).observe(viewLifecycleOwner){
                        if (it.equals("\nСообщение отправлено\n")){
                            findNavController().navigate(R.id.fillDataFragment)
                        }else{
                            Toast.makeText(requireContext(), "ошибка", Toast.LENGTH_SHORT).show()
                        }
                    }

            }
            tvVerifyCode.text = "Вставьте 4-значный код, отправленный в SMS \nпо номеру +996${phoneNumber}"
        }
    }

    private fun setUpCodeInput() = with(binding) {
        inputCode1.addTextChangedListener { charSequence ->
            if (charSequence.toString().trim().isNotEmpty()) {
                inputCode2.requestFocus()
            }
        }

        inputCode2.addTextChangedListener { charSequence ->
            if (charSequence.toString().trim().isNotEmpty()) {
                inputCode3.requestFocus()
            }
        }

        inputCode3.addTextChangedListener { charSequence ->
            if (charSequence.toString().trim().isNotEmpty()) {
                inputCode4.requestFocus()
            }
        }
    }
}