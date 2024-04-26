package kg.geekspro.android_lotos.ui.fragments.profile.password

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kg.geekspro.android_lotos.R
import kg.geekspro.android_lotos.databinding.FragmentForgotPasswordBinding

@AndroidEntryPoint
class ForgotPasswordFragment : Fragment() {
    private lateinit var binding:FragmentForgotPasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpCodeInput()
        binding.apply {
            btnConfirmPassword.setOnClickListener {
                if (containerEt.visibility == View.GONE){
                    containerEt.visibility = View.VISIBLE
                }else{
                    if (inputCode1.text.toString().isEmpty() ||
                        inputCode2.text.toString().isEmpty() ||
                        inputCode3.text.toString().isEmpty() ||
                        inputCode4.text.toString().isEmpty() ){
                        Toast.makeText(requireContext(), "Введите 4-значный код", Toast.LENGTH_SHORT).show()
                    }else{
                        findNavController().navigate(R.id.confirmPasswordFragment)
                    }
                }
            }
            imgArrowBack.setOnClickListener{
                findNavController().navigateUp()
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