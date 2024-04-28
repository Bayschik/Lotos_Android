package kg.geekspro.android_lotos.ui.fragments.safetyEmail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.navigation.findNavController
import kg.geekspro.android_lotos.R
import kg.geekspro.android_lotos.databinding.FragmentSafetyEmailUpdateBinding

class SafetyEmailUpdateFragment : Fragment() {
    private lateinit var binding:FragmentSafetyEmailUpdateBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentSafetyEmailUpdateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpCodeInput()
        binding.apply {
            btnOnfirm.setOnClickListener {
                if (inputCode1.text.toString().isEmpty() ||
                    inputCode2.text.toString().isEmpty() ||
                    inputCode3.text.toString().isEmpty() ||
                    inputCode4.text.toString().isEmpty()
                ) {
                    Toast.makeText(requireContext(), "Введите 4-значный код", Toast.LENGTH_LONG)
                        .show()
                } else {
                    Toast.makeText(requireContext(), "Ваш номер телефона изменен", Toast.LENGTH_LONG)
                        .show()
                }
            }
            imgArrowBack.findNavController().navigate(R.id.safetyFragment)
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