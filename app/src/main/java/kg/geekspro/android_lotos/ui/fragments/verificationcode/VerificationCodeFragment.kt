package kg.geekspro.android_lotos.ui.fragments.verificationcode

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kg.geekspro.android_lotos.R
import kg.geekspro.android_lotos.databinding.FragmentVerificationCodeBinding
import kg.geekspro.android_lotos.models.verifycode.VerificationCode
import kg.geekspro.android_lotos.viewmodels.verifyviewmodel.VerificationViewModel

@AndroidEntryPoint
class VerificationCodeFragment : Fragment() {

    private lateinit var binding: FragmentVerificationCodeBinding
    private val viewModel: VerificationViewModel by viewModels()

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
        setUpCodeInput()
        binding.apply {
            btnCodeContinue.setOnClickListener {
                if (inputCode1.text.toString().isEmpty() ||
                    inputCode2.text.toString().isEmpty() ||
                    inputCode3.text.toString().isEmpty() ||
                    inputCode4.text.toString().isEmpty()
                ) {
                    Toast.makeText(requireContext(), "Введите 4-значный код", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    val code =
                        inputCode1.text.toString() + inputCode2.text.toString() + inputCode3.text.toString() + inputCode4.text.toString()

                    val data = VerificationCode(code = code)

                    viewModel.confirmCode(data).observe(viewLifecycleOwner) {
                        Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
                        if (it.toString() == "Неверный код"){
                            val errorBackground: Drawable? = ContextCompat.getDrawable(requireContext(), R.drawable.code_red_border)
                            inputCode1.background = errorBackground
                            inputCode2.background = errorBackground
                            inputCode3.background = errorBackground
                            inputCode4.background = errorBackground
                        }else{
                            Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
                            findNavController().navigate(R.id.fillDataFragment)
                        }
                    }
                }
            }
            tvVerifyCode.text =
                "Вставьте 4-значный код,отправленный в Gmail \nпо адресу $phoneNumber"
        }
    }

    private fun setUpCodeInput() = with(binding) {
        setupEditText(inputCode1,null,inputCode2)
        setupEditText(inputCode2,inputCode1,inputCode3)
        setupEditText(inputCode3,inputCode2,inputCode4)
        setupEditText(inputCode4,inputCode3,null)
    }

    private fun setupEditText(
        currentEditText: EditText,
        previousEditText: EditText?,
        nextEditText: EditText?
    ) {
        currentEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.length == 1 && nextEditText != null) {
                    nextEditText.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        currentEditText.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_DEL && event.action == KeyEvent.ACTION_DOWN) {
                if (currentEditText.text.isEmpty() && previousEditText != null) {
                    previousEditText.requestFocus()
                }
                return@setOnKeyListener true
            }
            false
        }
    }
}