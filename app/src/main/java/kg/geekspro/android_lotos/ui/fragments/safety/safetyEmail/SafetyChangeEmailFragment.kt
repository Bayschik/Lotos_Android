package kg.geekspro.android_lotos.ui.fragments.safety.safetyEmail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kg.geekspro.android_lotos.R
import kg.geekspro.android_lotos.databinding.FragmentSafetyChangeEmailBinding

@AndroidEntryPoint
class SafetyChangeEmailFragment : Fragment() {
    private lateinit var binding: FragmentSafetyChangeEmailBinding
    private val viewModel: SafetyEmailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSafetyChangeEmailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpCodeInput()
        binding.apply {
            btnConfirm.setOnClickListener {
                if (inputCode1.text.toString().isEmpty() ||
                    inputCode2.text.toString().isEmpty() ||
                    inputCode3.text.toString().isEmpty() ||
                    inputCode4.text.toString().isEmpty()
                ) {
                    Toast.makeText(requireContext(), "Введите 4-значный код", Toast.LENGTH_LONG)
                        .show()
                } else {
                    val code =
                        inputCode1.text.toString() + inputCode2.text.toString() + inputCode3.text.toString() + inputCode4.text.toString()

                    val data = Code(
                        code = code
                    )
                    viewModel.changeEmailConfirm(data).observe(viewLifecycleOwner) {
                        findNavController().navigate(R.id.safetyFragment)
                    }
                }
            }
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