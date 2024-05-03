package kg.geekspro.android_lotos.ui.fragments.safety.safetyPassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kg.geekspro.android_lotos.R
import kg.geekspro.android_lotos.databinding.FragmentChangePasswordBinding
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChangePasswordFragment : Fragment() {
    private lateinit var binding: FragmentChangePasswordBinding
    private val viewModel:ChangePasswordViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChangePasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnChangePassword.setOnClickListener {
                if (etOfficialCurrentPassword.text.toString().isEmpty() ||
                    etOfficialNewPassword.text.toString().isEmpty() ||
                    etOfficialSecondNewPassword.text.toString().isEmpty()
                ) {
                    Toast.makeText(
                        requireContext(),
                        "Поля должны быть заполнены",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (etOfficialNewPassword.text.toString() !=
                    etOfficialSecondNewPassword.text.toString()
                ) {
                    Toast.makeText(
                        requireContext(),
                        "Новые пароли должны совпадать",
                        Toast.LENGTH_SHORT
                    ).show()
                }else{
                    val changePassword = ChangePassword(
                        oldPassword = etOfficialCurrentPassword.text.toString(),
                        newPassword = etOfficialNewPassword.text.toString(),
                        reNewPassword = etOfficialSecondNewPassword.text.toString()
                    )
                    viewModel.viewModelScope.launch {
                        viewModel.changePassword(changePassword).observe(viewLifecycleOwner){
                            findNavController().navigateUp()
                        }
                    }
                }
            }
            tvForgotPassword.setOnClickListener {
                findNavController().navigate(R.id.forgotPasswordFragment)
            }
            imgArrowBack.setOnClickListener{
                findNavController().navigateUp()
            }

        }
    }

}